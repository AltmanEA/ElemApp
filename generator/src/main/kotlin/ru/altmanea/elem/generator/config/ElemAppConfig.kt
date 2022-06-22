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
    val mongoConnect: String = ""
)