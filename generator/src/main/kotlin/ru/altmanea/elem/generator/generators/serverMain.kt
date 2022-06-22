package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator

fun Generator.serverMain(): FileSpec {
    val fileSpec = FileSpec
        .builder(config.packageName, "${config.name}Main")
    val mainFun = FunSpec
        .builder("main")

    mongoMain(fileSpec,mainFun)

    return fileSpec
        .addFunction(
            mainFun.build()
        )
        .build()
}

fun Generator.mongoMain(fileSpec: FileSpec.Builder, mainFun: FunSpec.Builder) {
    val mongoClient = ClassName("com.mongodb.client", "MongoClient")
    val mongoDatabase = ClassName("com.mongodb.client", "MongoDatabase")
    val mongoCollection = ClassName("com.mongodb.client", "MongoCollection")

    val client = PropertySpec
        .builder("mongoClient", mongoClient)
        .initializer(CodeBlock.of("KMongo.createClient(%S)", config.serverConfig.mongoConnect))
        .build()

    val database = PropertySpec
        .builder("mongoDatabase", mongoDatabase)
        .initializer(CodeBlock.of("%N.getDatabase(%S)", client, config.name))
        .build()

    fileSpec.addProperty(client)
        .addProperty(database)
        .addImport("org.litote.kmongo", "KMongo")
        .addImport("org.litote.kmongo", "getCollection")

    config.elems.map {
        fileSpec.addProperty(
            PropertySpec
                .builder(
                    "collection${it.name}",
                    mongoCollection.parameterizedBy(
                        ClassName(config.packageName, it.name + "Mongo")
                    )
                )
                .initializer(CodeBlock.of("%N.getCollection()", database))
                .build()
        )

    }
}