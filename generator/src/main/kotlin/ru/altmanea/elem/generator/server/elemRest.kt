package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.*
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription

private val callObject = MemberName(SDef.packageApplication, "call")
private val respondFun = MemberName(SDef.packageResponse, "respond")
private val routeFun = MemberName(SDef.packageRouting, "route")
private val getFun = MemberName(SDef.packageRouting, "get")
fun Generator.elemRest(elem: ElemDescription): FileSpec {

    val elemRoute =
        FunSpec
            .builder(elem.routingFunName)
            .receiver(SDef.routeClassname)
            .beginControlFlow("%M(%S)", routeFun, elem.name)
            .addCode(elemRestGet())
            .endControlFlow()

    return FileSpec
        .builder(packageName, elem.restClassName)
        .addFunction(elemRoute.build())
        .build()
}

private fun elemRestGet(): CodeBlock {
    val result =
        CodeBlock
            .builder()
            .beginControlFlow("%M", getFun)
            .addStatement("%M.%M(%S)", callObject, respondFun, "")
            .endControlFlow()
    return result.build()
}