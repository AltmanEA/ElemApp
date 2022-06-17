package ru.altmanea.elem.generator

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import ru.altmanea.elem.generator.config.ElemDescription

class Generator(
    private val packageName: String,
) {
    fun elems(description: ElemDescription): FileSpec {
        val className = description.name
        val elemClass =
            TypeSpec
                .classBuilder(className)
                .build()
        return FileSpec
            .builder(packageName, className)
            .addType(elemClass)
            .build()
    }
}
