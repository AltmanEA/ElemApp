package ru.altmanea.elem.core.config

import kotlinx.serialization.Serializable

@Serializable
enum class PropsType {
    Int,
    Float,
    String,
    Link
}