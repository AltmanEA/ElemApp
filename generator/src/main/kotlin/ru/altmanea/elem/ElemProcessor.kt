package ru.altmanea.elem

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
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
        println("--- KSP process start ---")
        val symbols = resolver
            .getSymbolsWithAnnotation("ru.altmanea.elem.annotations.ELem")
        if (!symbols.iterator().hasNext()) return emptyList()

        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "ru.altmanea.elem",
            fileName = "TestGen"
        )
        // Generating package statement.
        file += "val x = \"${symbols.first()}\""

        file.close()
        return emptyList()
    }
}