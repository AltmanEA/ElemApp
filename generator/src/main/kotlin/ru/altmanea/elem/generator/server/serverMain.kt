package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator

private val kMongoClass = MemberName(SDef.packageKMongo, "KMongo")
private val getCollectionFun = MemberName(SDef.packageKMongo, "getCollection")
private val embeddedServer = MemberName("io.ktor.server.engine", "embeddedServer")
private val netty = MemberName("io.ktor.server.netty", "Netty")
private val routingFun = MemberName(SDef.packageRouting, "routing")

fun Generator.serverMain(): FileSpec {
    val fileSpec = FileSpec
        .builder(config.packageName, "${config.name}Main")

    val mainFun = FunSpec
        .builder("main")

    mongoMain(fileSpec, mainFun)
    ktorMain(fileSpec, mainFun)

    return fileSpec
        .addFunction(
            mainFun.build()
        )
        .build()
}

fun Generator.mongoMain(fileSpec: FileSpec.Builder, mainFun: FunSpec.Builder) {
    val mongoClientClass = ClassName(SDef.packageMongoClient, "MongoClient")
    val mongoDatabaseClass = ClassName(SDef.packageMongoClient, "MongoDatabase")
    val mongoCollectionClass = ClassName(SDef.packageMongoClient, "MongoCollection")

    val mongoClient = PropertySpec
        .builder("mongoClient", mongoClientClass)
        .initializer(CodeBlock.of("%M.createClient(%S)", kMongoClass, config.serverConfig.mongoConnect))
        .build()

    val mongoDatabase = PropertySpec
        .builder("mongoDatabase", mongoDatabaseClass)
        .initializer(CodeBlock.of("%N.getDatabase(%S)", mongoClient, config.name))
        .build()

    fileSpec.addProperty(mongoClient)
        .addProperty(mongoDatabase)

    config.elems.map {
        fileSpec.addProperty(
            PropertySpec
                .builder(
                    "collection${it.name}",
                    mongoCollectionClass.parameterizedBy(
                        ClassName(config.packageName, it.name + "Mongo")
                    )
                )
                .initializer(CodeBlock.of("%N.%M()", mongoDatabase, getCollectionFun))
                .build()
        )

    }
}

fun Generator.ktorMain(fileSpec: FileSpec.Builder, mainFun: FunSpec.Builder) {
    val application = ClassName(SDef.packageApplication, "Application")

    val mainModuleBuiler = FunSpec
        .builder("main")
        .receiver(application)
    mainModuleBuiler.beginControlFlow("%M", routingFun)
    config.elems.forEach {
        mainModuleBuiler.addStatement("${it.routingFunName}()")
    }
    mainModuleBuiler.endControlFlow()
    val mainModule = mainModuleBuiler.build()

    fileSpec
        .addFunction(mainModule)

    mainFun
        .addStatement("%M(\n" +
                "        %M,\n" +
                "        port = ${config.serverConfig.serverPort},\n" +
                "        host = %S,\n" +
                "    ) {\n" +
                "        %N()\n" +
                "    }.start(wait = true)",
            embeddedServer,
            netty,
            config.serverConfig.serverHost,
            mainModule
        )
}