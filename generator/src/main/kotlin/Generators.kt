package ru.altmanea.elem.generator

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import ru.altmanea.elem.core.config.ElemDescription
import java.io.File

class Generators(
    private val packageName: String,
    private val outputDir: File
) {
    fun elems(description: ElemDescription) {
        val className = description.name
        val elemClass =
            TypeSpec
                .classBuilder(className)
                .build()
        val elemFile =
            FileSpec
                .builder(packageName, className)
                .addType(elemClass)
                .build()
        elemFile.writeTo(outputDir)
    }
}
