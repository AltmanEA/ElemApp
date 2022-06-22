package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.shared.ImportDef

fun Generator.elemMongo(elem: ElemDescription): FileSpec {
    val className = elem.name + "Mongo"
    val (baseClass, innerClasses) = elemBase(elem, className)

    val idClassName = ClassName("org.litote.kmongo", "Id")
    baseClass.second
        .addParameter(
            ParameterSpec
                .builder("id", idClassName.parameterizedBy(ClassName(packageName, className)))
                .defaultValue("newId()")
                .build()
        )
    baseClass.first
        .addProperty(
            PropertySpec
                .builder("id", idClassName.parameterizedBy(ClassName(packageName, className)))
                .addAnnotation(ImportDef.contextual)
                .initializer("id")
                .build()
        )

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
        .addImport("org.litote.kmongo", "newId")
        .build()
}