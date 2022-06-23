package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.server.SDef
import ru.altmanea.elem.generator.shared.lfl

fun Generator.elemRest(elem: ElemDescription): FileSpec {
    fileImport.clear()

    val fileName = elem.name + "REST"
    val elemRoute =
        FunSpec
            .builder(lfl(elem.name) + "Routes")
            .receiver(SDef.classRoute)
            .beginControlFlow("route(%S)", elem.name)
            .addCode(elemRestGet())
            .endControlFlow()

    fileImport.add(SDef.packageRouting to "route")
    fileImport.add(SDef.packageRouting to "get")

    return FileSpec
        .builder(packageName, fileName)
        .addFunction(elemRoute.build())
        .importAll()
        .build()
}

fun Generator.elemRestGet(): CodeBlock {
    val result =
        CodeBlock
            .builder()
            .beginControlFlow("get")
            .addStatement("call.respond(%S)", "")
            .endControlFlow()
    fileImport.add(SDef.packageApplication to "call")
    return result.build()
}