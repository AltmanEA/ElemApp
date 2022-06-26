package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.*
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription

fun Generator.elemRest(): FileSpec {
    val codeBlock =
        CodeBlock
            .builder()
            .indent()
            .addStatement(".verbs")
            .beginControlFlow(".forEach")
            .addStatement("it()")
            .endControlFlow()
            .build()

    val restFun =
        FunSpec
            .builder("addElemRoutes")
            .receiver(SDef.routeClassname)
    config.elems.forEach {
        restFun
            .addCode(
                "%T(\n" +
                        "\t${it.mongoClassName}::class,\n" +
                        "\t${it.mongoCollectionName}\n" +
                        ")\n",
                SDef.restVerbsMongoClass
            )
            .addCode(codeBlock)
    }

    return FileSpec
        .builder(packageName, "AddElemRoutes")
        .addFunction(restFun.build())
        .build()
}


//
//private val callObject = MemberName(SDef.packageApplication, "call")
//private val respondFun = MemberName(SDef.packageResponse, "respond")
//private val routeFun = MemberName(SDef.packageRouting, "route")
//private val getFun = MemberName(SDef.packageRouting, "get")
//
//fun Generator.elemRest(elem: ElemDescription): FileSpec {
//
//    val elemRouteObj =
//        PropertySpec
//            .builder(elem.verbObjName, SDef.restVerbsInterface)
//            .initializer(
//                "%T(\n" +
//                        "${elem.mongoClassName}::class,\n" +
//                        "${elem.mongoCollectionName}\n" +
//                        ")",
//                SDef.restVerbsMongoClass
//            )
//            .build()
//
//    val elemRoute =
//        FunSpec
//            .builder(elem.routingFunName)
//            .receiver(SDef.routeClassname)
//            .beginControlFlow("%M(%S)", routeFun, elem.name)
//            .addCode("%N.addVerb()", elemRouteObj)
//            .endControlFlow()
//            .build()
//
//
//    return FileSpec
//        .builder(packageName, elem.restClassName)
//        .addProperty(elemRouteObj)
//        .addFunction(elemRoute)
//        .build()
//}
