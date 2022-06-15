package ru.altmanea.elem.core.config

import kotlinx.serialization.Serializable

@Serializable
class Config(
    val name: String,
    val elems: List<ElemDescription>
)