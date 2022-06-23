package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.generators.build
import ru.altmanea.elem.generator.generators.elemBase
import ru.altmanea.elem.generator.shared.Def

private val newIdFun = MemberName(SDef.packageKMongo, "newId")

fun Generator.elemMongo(elem: ElemDescription): FileSpec {
    val className = elem.mongoClassName
    val (baseClass, innerClasses) = elemBase(elem, className)

    baseClass.second
        .addParameter(
            ParameterSpec
                .builder("id", SDef.idClassName.parameterizedBy(ClassName(packageName, className)))
                .defaultValue("%M()", newIdFun)
                .build()
        )
    baseClass.first
        .addProperty(
            PropertySpec
                .builder("id", SDef.idClassName.parameterizedBy(ClassName(packageName, className)))
                .addAnnotation(Def.contextual)
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
        .build()
}