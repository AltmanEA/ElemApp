package ru.altmanea.elem.jslib.kfetch

import kotlinx.serialization.json.Json
import kotlin.js.Promise

@JsModule("cross-fetch")
@JsNonModule
external fun fetch(
    url: String,
    options: FetchOptions = definedExternally
): Promise<HTTPResult>

external interface HTTPResult {
    fun json(): Promise<dynamic>
    fun text(): Promise<String>
}

external interface FetchOptions {
    var method: String
    var headers: dynamic
    var body: dynamic
}

fun fetchText(
    url: String,
    options: FetchOptions = js("{}")
) =
    fetch(url, options)
        .then { it.text() }
        .then { it }

