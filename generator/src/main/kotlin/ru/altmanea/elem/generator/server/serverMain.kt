package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.poet.Bracket
import ru.altmanea.elem.generator.poet.block
import ru.altmanea.elem.generator.shared.*

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

    val mongoClient = PropertySpec
        .builder("mongoClient", Mongo.MongoClient)
        .initializer(CodeBlock.of("%M.createClient(%S)", Mongo.KMongo, config.serverConfig.mongoConnect))
        .build()

    val mongoDatabase = PropertySpec
        .builder("mongoDatabase", Mongo.MongoDatabase)
        .initializer(CodeBlock.of("%N.getDatabase(%S)", mongoClient, config.name))
        .build()

    fileSpec.addProperty(mongoClient)
        .addProperty(mongoDatabase)

    config.elems.map {
        fileSpec.addProperty(
            PropertySpec
                .builder(
                    it.mongoCollectionName,
                    Mongo.MongoCollection.parameterizedBy(
                        ClassName(config.packageName, it.mongo)
                    )
                )
                .initializer(CodeBlock.of("%N.%M()", mongoDatabase, Mongo.getCollection))
                .build()
        )

    }
}

fun Generator.ktorMain(fileSpec: FileSpec.Builder, mainFun: FunSpec.Builder) {
    val application = ClassName("io.ktor.server.application", "Application")
    val installFun = MemberName("io.ktor.server.application", "install")
    val contentNegotiation = MemberName("io.ktor.server.plugins.contentnegotiation", "ContentNegotiation")
    val jsonFun = MemberName("io.ktor.serialization.kotlinx.json", "json")
    val jsonClass = ClassName("kotlinx.serialization.json", "Json")
    val idSerial = MemberName("org.litote.kmongo.id.serialization", "IdKotlinXSerializationModule")
    val embeddedServer = MemberName("io.ktor.server.engine", "embeddedServer")
    val netty = MemberName("io.ktor.server.netty", "Netty")
    val routingFun = MemberName("io.ktor.server.routing", "routing")

    val rests = CodeBlock.builder().run {
        beginControlFlow("%M(%S)", Ktor.route, config.serverConfig.apiPath)
        config.elems.forEach {
            addStatement("%N()", it.rest.lowerFirstLetter)
        }
        endControlFlow()
        addStatement("index()")
    }

    val mainModule = FunSpec.builder("main").run {
        receiver(application)
        block("%M(%M)", installFun, contentNegotiation) {
            addStatement("%M(%T { serializersModule = %M })", jsonFun, jsonClass, idSerial)
        }
        block("%M", routingFun) {
            addCode(rests.build())
        }
        build()
    }

    fileSpec
        .addFunction(mainModule)

    mainFun.addCode(CodeBlock.builder().run {
        block("%M", embeddedServer, bracket = Bracket.Round, end = ""){
            addStatement("factory = %M,", netty)
            addStatement("port = ${config.serverConfig.serverPort},")
            addStatement("host = %S", config.serverConfig.serverHost)
        }
        block(end = ""){
            addStatement("%N()", mainModule)
        }
        addStatement(".start(wait = true)")
        build()
    })
}
