package ru.altmanea.elem.core.server

import io.ktor.server.routing.*

interface RestVerbs {
    val verbs: List<Route.()->Route>
}