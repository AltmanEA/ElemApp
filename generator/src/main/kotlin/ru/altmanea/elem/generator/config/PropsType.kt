package ru.altmanea.elem.generator.config

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
enum class PropsType(
    val type: KClass<*>
) {
    INT(Long::class),
    FLOAT(Double::class),
    STRING(String::class),
    LINK(String::class)
}


