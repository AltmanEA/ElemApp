package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.ClassName
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.shared.lowerFirstLetter

class SDef {
    companion object {
        const val packageRouting = "io.ktor.server.routing"
        const val packageResponse = "io.ktor.server.response"
        const val packageApplication = "io.ktor.server.application"
        const val packageKMongo = "org.litote.kmongo"
        const val packageMongoClient = "com.mongodb.client"

        val routeClassname = ClassName(packageRouting, "Route")
        val idClassName = ClassName(packageKMongo, "Id")
    }
}

val ElemDescription.dtoClassName
    get() = "${this.name}DTO"

val ElemDescription.restClassName
    get() = "${this.name}Rest"

val ElemDescription.mongoClassName
    get() = "${this.name}Mongo"

val ElemDescription.routingFunName
    get() = this.name.lowerFirstLetter + "Routes"