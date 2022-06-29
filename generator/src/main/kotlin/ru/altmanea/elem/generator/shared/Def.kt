package ru.altmanea.elem.generator.shared

import com.squareup.kotlinpoet.ClassName
import ru.altmanea.elem.generator.config.ElemDescription

class Def {
    companion object {
        const val packageSerialization = "kotlinx.serialization"
        const val packageCore = "ru.altmanea.elem.core"
        const val packageKtorServer = "io.ktor.server"
        const val packageKtorHTTP = "io.ktor.http"
        const val packageKMongo = "org.litote.kmongo"
        const val packageMongoClient = "com.mongodb.client"

        val serializable = ClassName(packageSerialization, "Serializable")
        val contextual = ClassName(packageSerialization, "Contextual")
        val routeClassname = ClassName("$packageKtorServer.routing", "Route")
        val idClassName = ClassName(packageKMongo, "Id")
        val restVerbsInterface = ClassName("$packageCore.server", "RestVerbs")
        val restVerbsMongoClass = ClassName("$packageCore.server", "RestVerbsMongo")

    }
}

val ElemDescription.client
    get() = "${this.name}Client"

val ElemDescription.server
    get() = "${this.name}Server"

val ElemDescription.mongo
    get() = "${this.name}Mongo"

val ElemDescription.rest
    get() = "${this.name}Rest"

val ElemDescription.mongoCollectionName
    get() = "collection${this.name}"

val ElemDescription.path
    get() = name