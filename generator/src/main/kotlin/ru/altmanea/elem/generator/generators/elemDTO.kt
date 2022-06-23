package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.FileSpec
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.server.dtoClassName

fun Generator.elemDto(elem: ElemDescription): FileSpec {
    val (baseClass, innerClasses) = elemBase(elem, elem.dtoClassName)
    return FileSpec
        .builder(packageName, elem.dtoClassName)
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