package ru.altmanea.elem.generator

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import java.io.OutputStream

operator fun OutputStream.plusAssign(str: String) {
    this.write(str.toByteArray())
}

class ElemProcessor(
    private val options: Map<String, String>,
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.info("--- KSP process start ---")
        val symbols: Sequence<KSAnnotated> = resolver
            .getSymbolsWithAnnotation("ru.altmanea.elem.annotations.Elem")
        if (!symbols.iterator().hasNext())
            return emptyList()

        val className = symbols.first().toString()
        val fileSpec =
            FileSpec.builder("", "HelloWorld")
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