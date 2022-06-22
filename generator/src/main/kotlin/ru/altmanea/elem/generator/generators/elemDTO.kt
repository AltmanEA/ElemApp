package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.FileSpec
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription

fun Generator.elemDto(elem: ElemDescription): FileSpec {
    val className = elem.name + "DTO"
    val (baseClass, innerClasses) = elemBase(elem, className)
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