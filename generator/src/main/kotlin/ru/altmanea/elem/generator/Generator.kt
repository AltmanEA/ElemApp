package ru.altmanea.elem.generator

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.generators.elemDto
import ru.altmanea.elem.generator.generators.elemRepo

class Generator(
    val packageName: String,
) {
    fun serverFiles(description: ElemDescription) =
        listOf(
            elemDto(description),
            elemRepo(description)
        )

    fun clientFiles(description: ElemDescription) =
        listOf(
            elemDto(description),
            elemComp(description)
        )

    private fun elemComp(description: ElemDescription): FileSpec {
        val className = "fc" + description.name
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
