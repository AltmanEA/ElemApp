package ru.altmanea.elem.generator.shared

import com.squareup.kotlinpoet.*

fun ElemGenerator.elemClient() =
    FileSpec
        .builder(packageName, clientClass.simpleName)
        .addElemBase(elemBase(clientClass, clientInners))
        .build()
