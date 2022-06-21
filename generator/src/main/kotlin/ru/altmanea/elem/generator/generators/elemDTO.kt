package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.FileSpec
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription

fun Generator.elemDto(description: ElemDescription): FileSpec {
    val className = description.name + "DTO"
    val (baseClass, innerClasses) = elemBase(description, className)
    return FileSpec
        .builder(packageName, className)
        .addType(
            baseClass.build()
        )
        .apply {
            innerClasses.map {
                addType(
                    it.build()
                )
            }
        }
        .build()
}