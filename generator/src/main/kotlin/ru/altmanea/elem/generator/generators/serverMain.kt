package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.FileSpec
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemAppConfig

fun Generator.serverMain(config: ElemAppConfig): FileSpec {
    val fileSpec = FileSpec.builder(config.packageName, "${config.name}Main")
    return fileSpec.build()
}