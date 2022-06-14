package ru.altmanea.elem.core

import kotlinx.serialization.Serializable

@Serializable
enum class PropsType {
    Int,
    Float,
    String,
    Link
}