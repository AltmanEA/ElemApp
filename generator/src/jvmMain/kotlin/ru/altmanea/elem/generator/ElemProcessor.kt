package ru.altmanea.elem.generator

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec

class ElemProcessor(
    private val options: Map<String, String>,
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.info("--- KSP process start ---")
        val symbols: Sequence<KSAnnotated> = resolver
            .getSymbolsWithAnnotation("ru.altmanea.elem.generator.Elem")
        if (!symbols.iterator().hasNext())
            return emptyList()

        val className = symbols.first().toString()
        val fileSpec =
            FileSpec.builder("", "")
                .addType(
                    TypeSpec.classBuilder(className)
                        .primaryConstructor(
                            FunSpec.constructorBuilder()
                                .build()
                        )
                        .build()
                )
                .build()


        codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "ru.altmanea.elem",
            fileName = "TestGen"
        ).use { outputStream ->
            outputStream.writer()
                .use { writer ->
                    fileSpec.writeTo(writer)
                }
        }

        return emptyList()
    }
}