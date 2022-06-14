package ru.altmanea.elem.core

import kotlinx.serialization.Serializable

@Serializable
class RowDescription(
    val name: String,
    val props: List<PropsType> = emptyList()
)