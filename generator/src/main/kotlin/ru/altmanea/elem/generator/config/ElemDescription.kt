package ru.altmanea.elem.generator.config

import kotlinx.serialization.Serializable

@Serializable
class ElemDescription(
    val name: String,
    val props: Map<String,PropsType> = emptyMap(),
    val tables: List<RowDescription> = emptyList()
)