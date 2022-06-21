package ru.altmanea.elem.generator.shared

import com.squareup.kotlinpoet.ClassName

class ImportDef {
    companion object{
        val serializable = ClassName("kotlinx.serialization", "Serializable")
        val contextual = ClassName("kotlinx.serialization", "Contextual")
    }
}