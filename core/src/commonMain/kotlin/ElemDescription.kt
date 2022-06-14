package ru.altmanea.elem.core

import kotlinx.serialization.Serializable

@Serializable
open class ElemDescription(
    val name: String,
    val props: List<PropsType> = emptyList(),
    val tables: List<RowDescription> = emptyList()
)