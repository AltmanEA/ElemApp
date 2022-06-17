package ru.altmanea.elem.generator.config

import kotlinx.serialization.Serializable

@Serializable
enum class PropsType {
    Int,
    Float,
    String,
    Link
}