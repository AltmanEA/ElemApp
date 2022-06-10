package ru.altmanea.elem.generator

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toTypeName

class ElemProcessor(
    private val options: Map<String, String>,
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.info("--- KSP process start ---")
        resolver
            .getSymbolsWithAnnotation("ru.altmanea.elem.core.Elem")
            .forEach {
                logger.info("make $it")
                elemProcess(it as KSClassDeclaration, codeGenerator)
            }

        return emptyList()
    }

    @OptIn(KotlinPoetKspPreview::class)
    private fun elemProcess(elem: KSClassDeclaration, codeGenerator: CodeGenerator){
        val elemName = elem.toString() + "DTO"
        val elemPackage = elem.qualifiedName?.getQualifier()?:""
        val elemProps = elem.getAllProperties().first()
        val fileSpec =
            FileSpec.builder(elemPackage, "")
                .addType(
                    TypeSpec.classBuilder(elemName)
                        .primaryConstructor(
                            FunSpec.constructorBuilder()
                                .addParameter("name", String::class)
                                .addParameter(elemProps.toString(), elemProps.type.toTypeName() )
                                .build()
                        )
                        .build()
                )
                .build()

        codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = elemPackage,
            fileName = elemName
        ).use { outputStream ->
            outputStream.writer()
                .use { writer ->
                    fileSpec.writeTo(writer)
                }
        }

    }
}