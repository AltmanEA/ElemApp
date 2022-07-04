package ru.altmanea.elem.generator.shared

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import ru.altmanea.elem.generator.config.ElemDescription

class Serial{
    companion object{
        val Serializable = ClassName("kotlinx.serialization", "Serializable")
        val Contextual = ClassName("kotlinx.serialization", "Contextual")
    }
}

class Mongo{
    companion object {
        const val packageName = "org.litote.kmongo"
        val Id = ClassName(packageName, "Id")
        val newId = MemberName(packageName, "newId")
        val `in` = MemberName(packageName, "`in`")
        val toId = MemberName("${packageName}.id", "toId")
        val objectId = MemberName("org.bson.types", "ObjectId")
        val getCollection = MemberName(packageName, "getCollection")
        val KMongo = MemberName(packageName, "KMongo")

        const val packageClient = "com.mongodb.client"
        val MongoClient = ClassName(packageClient, "MongoClient")
        val MongoDatabase = ClassName(packageClient, "MongoDatabase")
        val MongoCollection = ClassName(packageClient, "MongoCollection")
    }
}

class Def {
    companion object {
        const val packageKtorServer = "io.ktor.server"
        const val packageKtorSerial = "io.ktor.serialization.kotlinx.json"
        const val packageKtorHTTP = "io.ktor.http"

        val routeClassname = ClassName("$packageKtorServer.routing", "Route")
        val getFun = MemberName("$packageKtorServer.routing", "get")
        val callObject = MemberName("${packageKtorServer}.application", "call")
        val statusCodeClass = MemberName(packageKtorHTTP, "HttpStatusCode")
    }
}

val ElemDescription.mongo
    get() = "${this.name}Mongo"

val ElemDescription.rest
    get() = "${this.name}Rest"

val ElemDescription.mongoCollectionName
    get() = "collection${this.name}"

val ElemDescription.path
    get() = name