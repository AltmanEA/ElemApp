package ru.altmanea.elem.generator.config

import kotlinx.serialization.Serializable

@Serializable
class ElemDescription(
    val name: String,
    val props: List<PropsType> = emptyList(),
    val tables: List<RowDescription> = emptyList()
)