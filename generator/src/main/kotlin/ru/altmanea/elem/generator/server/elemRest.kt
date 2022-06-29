package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.*
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.shared.*

private val routeFun = MemberName("${Def.packageKtorServer}.routing", "route")
private val callObject = MemberName("${Def.packageKtorServer}.application", "call")
private val getFun = MemberName("${Def.packageKtorServer}.routing", "get")
private val postFun = MemberName("${Def.packageKtorServer}.routing", "post")
private val statusCode = MemberName(Def.packageKtorHTTP, "HttpStatusCode")
private val respond = MemberName("${Def.packageKtorServer}.response", "respond")
private val respondText = MemberName("${Def.packageKtorServer}.response", "respondText")
private val receive = MemberName("${Def.packageKtorServer}.request", "receive")
private val insertOne = MemberName(Def.packageMongoClient, "insertOne")

fun Generator.elemRest(elem: ElemDescription): FileSpec {

    val verbs =
        CodeBlock
            .builder()
            .add(verbGet(elem))
            .add(postGet(elem))
            .build()

    val elemRoute =
        FunSpec
            .builder(elem.rest.lowerFirstLetter)
            .receiver(Def.routeClassname)
            .beginControlFlow("%M(%S)", routeFun, elem.path)
            .addCode(verbs)
            .endControlFlow()
            .build()

    return FileSpec
        .builder(packageName, elem.rest.lowerFirstLetter)
        .addFunction(elemRoute)
        .build()
}

fun verbGet(elem: ElemDescription) =
    CodeBlock
        .builder()
        .beginControlFlow("%M", getFun)
        .addStatement("val elems = %N.find().toList()", elem.mongoCollectionName)
        .beginControlFlow("if(elems.isEmpty())")
        .addStatement(
            "%M.%M(%S, status = %M.NotFound)",
            callObject, respondText, "No elems found", statusCode
        )
        .endControlFlow()
        .beginControlFlow("else")
        .addStatement("%M.%M(elems)", callObject, respond)
        .endControlFlow()
        .endControlFlow()
        .build()

fun postGet(elem: ElemDescription) =
    CodeBlock
        .builder()
        .beginControlFlow("%M", postFun)
        .addStatement(
            "val newElems = %M.%M<${elem.client}>()",
            callObject, receive
        )
        .addStatement(
            "${elem.mongoCollectionName}.%M(newElems.mongo)",
            insertOne
        )
        .addStatement(
            "%M.%M(%S, status = %M.Created)",
            callObject, respondText, "Elems stored", statusCode
        )
        .endControlFlow()
        .build()
