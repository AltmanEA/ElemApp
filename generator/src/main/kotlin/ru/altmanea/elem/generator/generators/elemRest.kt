package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.shared.lfl

fun Generator.elemRest(elem: ElemDescription): FileSpec {
    val route = ClassName("io.ktor.server.routing", "Route")

    val fileName = elem.name + "REST"
    val elemRoute =
        FunSpec
            .builder(lfl(elem.name) + "Routes")
            .receiver(route)

    elemRoute
        .beginControlFlow("route(%S)", elem.name)
    listOf(
        CodeBlock.builder()
            .beginControlFlow("get")
            .endControlFlow()
            .build()
    ).forEach {
        elemRoute.addCode(it)
    }
    elemRoute.endControlFlow()


    return FileSpec
        .builder(packageName, fileName)
        .addFunction(elemRoute.build())
        .addImport("io.ktor.server.routing", "route")
        .addImport("io.ktor.server.routing", "get")
        .build()
}