package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

class Ktor {
    companion object {
        val route = MemberName("io.ktor.server.routing", "route")
        val post = MemberName("io.ktor.server.routing", "post")
        val get = MemberName("io.ktor.server.routing", "get")
        val respond = MemberName("io.ktor.server.response", "respond")
        val respondText = MemberName("io.ktor.server.response", "respondText")
        val receive = MemberName("io.ktor.server.request", "receive")
        val callObject = MemberName("io.ktor.server.application", "call")
        val statusCodeClass = MemberName("io.ktor.http", "HttpStatusCode")

        val RouteClass = ClassName("io.ktor.server.routing", "Route")
    }
}