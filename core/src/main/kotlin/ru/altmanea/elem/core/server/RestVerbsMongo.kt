package ru.altmanea.elem.core.server

import com.mongodb.client.MongoCollection
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.reflect.KClass

class RestVerbsMongo<T: Any>(
    private val tClass: KClass<T>,
    private val mongoCollection: MongoCollection<T>
) : RestVerbs {

    override val verbs: List<Route.() -> Route>
        get() = listOf(
            get, post
        )

    private val get: Route.() -> Route = {
        get {
            val elems: List<T> = mongoCollection.find().toList()
            if (elems.isEmpty())
                call.respondText("No elems found", status = HttpStatusCode.NotFound)
            else
                call.respond(elems)
        }
    }

    private val post: Route.() -> Route = {
        post {
            val newElem = call.receive(tClass)
            mongoCollection.insertOne(newElem)
            call.respondText("Elem stored correctly", status = HttpStatusCode.Created)
        }
    }
}