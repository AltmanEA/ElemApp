package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import ru.altmanea.elem.generator.shared.*

fun ElemGenerator.elemRest(): FileSpec {

    val verbs =
        CodeBlock
            .builder()
            .add(verbGet())
            .add(verbPost())
            .build()

    val elemRoute =
        FunSpec
            .builder(elem.rest.lowerFirstLetter)
            .receiver(Ktor.RouteClass)
            .beginControlFlow("%M(%S)", Ktor.route, elem.path)
            .addCode(verbs)
            .endControlFlow()
            .build()

    return FileSpec
        .builder(packageName, elem.rest.lowerFirstLetter)
        .addFunction(elemRoute)
        .build()
}

fun ElemGenerator.verbGet() =
    CodeBlock
        .builder()
        .beginControlFlow("%M", Ktor.get)
        .add(queryIds())
        .add(
            "val elemsMongo =\n" +
                    "\tif (ids == null)\n" +
                    "\t\t${elem.mongoCollectionName}.find()\n" +
                    "\telse\n" +
                    "\t\t${elem.mongoCollectionName}.find(%T::_id %M ids)\n" +
                    "val elems = elemsMongo.map { it.toServer() }.toList()\n",
            mongoClass, Mongo.`in`
        )
        .beginControlFlow("if(elems.isEmpty())")
        .addStatement(
            "%M.%M(%S, status = %M.NotFound)",
            Ktor.callObject, Ktor.respondText, "No elems found", Def.statusCodeClass
        )
        .endControlFlow()
        .beginControlFlow("else")
        .addStatement("%M.%M(elems)", Ktor.callObject, Ktor.respond)
        .endControlFlow()
        .endControlFlow()
        .build()

//`get` {
//    val params = call.request.queryParameters["id"]
//    val ids = params
//        ?.split(",")
//        ?.map { ObjectId(it).toId<TestElemMongo>() }
//    val elemsMongo =
//        if (ids == null)
//            collectionTestElem.find()
//        else
//            collectionTestElem.find(TestElemMongo::_id `in` ids)
//    val elems = elemsMongo.map { it.toServer() }.toList()
//    if (elems.isEmpty()) {
//        call.respondText("No elems found", status = HttpStatusCode.NotFound)
//    } else {
//        call.respond(elems)
//    }
//}

fun ElemGenerator.verbPost() =
    CodeBlock
        .builder()
        .beginControlFlow("%M", Ktor.post)
        .addStatement(
            "val newElems = %M.%M<%T>()",
            Ktor.callObject, Ktor.receive, clientClass
        )
        .addStatement(
            "${elem.mongoCollectionName}.insertOne(newElems.toMongo())"
        )
        .addStatement(
            "%M.%M(%S, status = %M.Created)",
            Ktor.callObject, Ktor.respondText, "Elems stored", Def.statusCodeClass
        )
        .endControlFlow()
        .build()


fun idParam() =
    CodeBlock
        .builder()
        .add(
            "val id = call.parameters[\"id\"] ?: return@get call.respondText(\n" +
                    "\t\"Missing or malformed id\",\n" +
                    "\tstatus = HttpStatusCode.BadRequest\n" +
                    ")\n"
        )
        .build()


//    val params = call.request.queryParameters["id"]
//    val ids = params
//        ?.split(",")
//        ?.map { ObjectId(it).toId<TestElemMongo>() }
fun ElemGenerator.queryIds() =
    CodeBlock
        .builder()
        .add(
            "val params = call.request.queryParameters[\"id\"]\n" +
                    "val ids = params\n" +
                    "\t?.split(\",\")\n" +
                    "\t?.map { %M(it).%M<%T>() }\n",
            Mongo.objectId, Mongo.toId, mongoClass
        )
        .build()
