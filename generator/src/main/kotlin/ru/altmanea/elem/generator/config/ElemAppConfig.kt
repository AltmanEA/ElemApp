package ru.altmanea.elem.generator.config

import kotlinx.serialization.Serializable

@Serializable
class ElemAppConfig(
    val name: String,
    val packageName: String,
    val serverConfig: ElemAppServerConfig,
    val elems: List<ElemDescription>
)

@Serializable
class ElemAppServerConfig(
    val mongoConnect: String = "",
    val serverHost: String = "127.0.0.1",
    val serverPort: String = "8000",
    val apiPath: String = "api.0.1"
)