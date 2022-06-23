package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.server.SDef
import ru.altmanea.elem.generator.shared.lfl

fun Generator.serverMain(): FileSpec {
    val fileSpec = FileSpec
        .builder(config.packageName, "${config.name}Main")
    fileImport.clear()

    val mainFun = FunSpec
        .builder("main")

    mongoMain(fileSpec, mainFun)
    ktorMain(fileSpec, mainFun)

    return fileSpec
        .addFunction(
            mainFun.build()
        )
        .importAll()
        .build()
}

fun Generator.mongoMain(fileSpec: FileSpec.Builder, mainFun: FunSpec.Builder) {
    val mongoClient = ClassName(SDef.packageMongoClient, "MongoClient")
    val mongoDatabase = ClassName(SDef.packageMongoClient, "MongoDatabase")
    val mongoCollection = ClassName(SDef.packageMongoClient, "MongoCollection")

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

    fileImport.addAll(listOf(
        SDef.packageKMongo to "KMongo",
        SDef.packageKMongo to "getCollection"
    ))

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

fun Generator.ktorMain(fileSpec: FileSpec.Builder, mainFun: FunSpec.Builder) {
    val application = ClassName(SDef.packageApplication, "Application")

    val mainModuleBuiler = FunSpec
        .builder("main")
        .receiver(application)
    mainModuleBuiler.beginControlFlow("routing")
    config.elems.forEach {
        val funName = lfl(it.name) + "Routes"
        mainModuleBuiler.addStatement("$funName()")
        fileSpec.addImport(config.packageName, funName)
    }
    mainModuleBuiler.endControlFlow()
    val mainModule = mainModuleBuiler.build()

    fileSpec
        .addFunction(mainModule)

    fileImport.addAll(listOf(
        "io.ktor.server.engine" to "embeddedServer",
        "io.ktor.server.netty" to "Netty",
        SDef.packageRouting to "routing"
    ))

    mainFun
        .addStatement("embeddedServer(\n" +
                "        Netty,\n" +
                "        port = ${config.serverConfig.serverPort},\n" +
                "        host = %S,\n" +
                "    ) {\n" +
                "        %N()\n" +
                "    }.start(wait = true)",
            config.serverConfig.serverHost,
            mainModule
        )
}