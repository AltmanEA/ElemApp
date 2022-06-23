package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.ClassName

class SDef {
    companion object{
        const val packageRouting = "io.ktor.server.routing"
        const val packageResponse = "io.ktor.server.response"
        const val packageApplication = "io.ktor.server.application"
        const val packageKMongo = "org.litote.kmongo"
        const val packageMongoClient = "com.mongodb.client"

        fun mongoClassname(elemName: String) = "${elemName}Mongo"


        val classRoute = ClassName(packageRouting, "Route")


    }
}