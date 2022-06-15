package ru.altmanea.elem.core.config

import kotlinx.serialization.Serializable

@Serializable
class RowDescription(
    val name: String,
    val props: List<PropsType> = emptyList()
)