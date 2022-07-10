package ru.altmanea.elem.generator.shared

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import ru.altmanea.elem.generator.config.ElemDescription

class Serial {
    companion object {
        val Serializable = ClassName("kotlinx.serialization", "Serializable")
        val Contextual = ClassName("kotlinx.serialization", "Contextual")
        val Json = ClassName("kotlinx.serialization.json", "Json")
        val ListSerializer = ClassName("kotlinx.serialization.builtins", "ListSerializer")
    }
}

class Mongo {
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

// DTO from server to client
val ElemDescription.server
    get() = "${this.name}Server"

// DTO from client to server
val ElemDescription.client
    get() = "${this.name}Client"


// objects of mongo collection
val ElemDescription.mongo
    get() = "${this.name}Mongo"

// mongo collection name
val ElemDescription.mongoCollectionName
    get() = "collection${this.name}"


val ElemDescription.rest
    get() = "${this.name}Rest"


val ElemDescription.path
    get() = name