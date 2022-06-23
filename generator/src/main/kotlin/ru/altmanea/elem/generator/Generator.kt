package ru.altmanea.elem.generator

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import ru.altmanea.elem.generator.config.ElemAppConfig
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.generators.elemDto
import ru.altmanea.elem.generator.server.elemMongo
import ru.altmanea.elem.generator.server.elemRest
import ru.altmanea.elem.generator.server.serverMain

class Generator(
    val config: ElemAppConfig
) {

    val packageName
        get() = config.packageName

    fun serverFiles(): List<FileSpec> {
        val result = ArrayList<FileSpec>()
        config.elems.forEach {
            result.addAll(elemServerFiles(it))
        }
        result.add(serverMain())
        return result
    }

    private fun elemServerFiles(description: ElemDescription) =
        listOf(
            elemDto(description),
            elemMongo(description),
            elemRest(description)
        )

    fun clientFiles(): List<FileSpec> {
        val result = ArrayList<FileSpec>()
        config.elems.forEach {
            result.addAll(elemClientFiles(it))
        }
        return result
    }

    private fun elemClientFiles(description: ElemDescription) =
        listOf(
            elemDto(description),
            elemComp(description)
        )

    private fun elemComp(description: ElemDescription): FileSpec {
        // TO DO make and extract
        val className = "fc" + description.name
        val elemClass =
            TypeSpec
                .classBuilder(className)
                .build()
        return FileSpec
            .builder(config.packageName, className)
            .addType(elemClass)
            .build()
    }

}
