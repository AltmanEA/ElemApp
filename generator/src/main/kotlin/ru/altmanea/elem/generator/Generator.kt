package ru.altmanea.elem.generator

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import ru.altmanea.elem.generator.client.clientMain
import ru.altmanea.elem.generator.config.ElemAppConfig
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.shared.elemClient
import ru.altmanea.elem.generator.server.elemMongo
import ru.altmanea.elem.generator.server.elemRest
import ru.altmanea.elem.generator.server.index
import ru.altmanea.elem.generator.server.serverMain
import ru.altmanea.elem.generator.shared.ElemGenerator

class Generator(
    val config: ElemAppConfig
) {

    val packageName
        get() = config.packageName

    fun serverFiles(): List<FileSpec> {
        val result = ArrayList<FileSpec>()
        config.elems.forEach {
            result.addAll(
                ElemGenerator(config, it)
                    .server()
            )
        }
        result.add(serverMain())
        result.add(index())
        return result
    }

    fun clientFiles(): List<FileSpec> {
        val result = ArrayList<FileSpec>()
        config.elems.forEach {
            result.addAll(
                ElemGenerator(config, it)
                    .client()
            )
        }
        result.add(clientMain())
        return result
    }

}
