package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.shared.*

private val kMongoClass = MemberName(Def.packageKMongo, "KMongo")
private val getCollectionFun = MemberName(Def.packageKMongo, "getCollection")
private val embeddedServer = MemberName("io.ktor.server.engine", "embeddedServer")
private val netty = MemberName("io.ktor.server.netty", "Netty")
private val routingFun = MemberName("${Def.packageKtorServer}.routing", "routing")

fun Generator.serverMain(): FileSpec {
    val fileSpec = FileSpec
        .builder(config.packageName, "${config.name}ServerMain")

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
    val mongoClientClass = ClassName(Def.packageMongoClient, "MongoClient")
    val mongoDatabaseClass = ClassName(Def.packageMongoClient, "MongoDatabase")
    val mongoCollectionClass = ClassName(Def.packageMongoClient, "MongoCollection")

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
                    it.mongoCollectionName,
                    mongoCollectionClass.parameterizedBy(
                        ClassName(config.packageName, it.mongo)
                    )
                )
                .initializer(CodeBlock.of("%N.%M()", mongoDatabase, getCollectionFun))
                .build()
        )

    }
}

fun Generator.ktorMain(fileSpec: FileSpec.Builder, mainFun: FunSpec.Builder) {
    val application = ClassName(Def.packageKtorServer+".application", "Application")
    val installFun = MemberName(Def.packageKtorServer+".application", "install")
    val contentNegotiation =MemberName("${Def.packageKtorServer}.plugins.contentnegotiation", "ContentNegotiation")
    val jsonFun = MemberName(Def.packageKtorSerial, "json")
    val jsonClass = ClassName("kotlinx.serialization.json", "Json")
    val idSerail = MemberName("org.litote.kmongo.id.serialization", "IdKotlinXSerializationModule")

    val rests = CodeBlock.builder()
    config.elems.forEach {
        rests.addStatement("%N()", it.rest.lowerFirstLetter)
    }

    val mainModule = FunSpec
        .builder("main")
        .receiver(application)
        .beginControlFlow("%M(%M)", installFun, contentNegotiation)
        .addStatement("%M(%T { serializersModule = %M })", jsonFun, jsonClass, idSerail)
        .endControlFlow()
        .beginControlFlow("%M", routingFun)
        .addCode(rests.build())
        .endControlFlow()
        .build()

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