package ru.altmanea.elem.generator.config

import kotlinx.serialization.Serializable

@Serializable
class RowDescription(
    val name: String,
    val props: Map<String,PropsType> = emptyMap()
)