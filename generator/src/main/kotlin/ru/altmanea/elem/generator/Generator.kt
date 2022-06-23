package ru.altmanea.elem.generator

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import ru.altmanea.elem.generator.config.ElemAppConfig
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.generators.elemDto
import ru.altmanea.elem.generator.generators.elemMongo
import ru.altmanea.elem.generator.generators.elemRest
import ru.altmanea.elem.generator.generators.serverMain

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

    fun elemServerFiles(description: ElemDescription) =
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

    fun elemClientFiles(description: ElemDescription) =
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

    /**
     * Accumulator for FileSpec imports.
     *
     * - call fileImport.clear() before FileSpec processing;
     * - call importAll() before FileSpec build.
     */
    val fileImport = ArrayList<Pair<String, String>>()
    fun FileSpec.Builder.importAll(): FileSpec.Builder{
        fileImport.forEach{
            this.addImport(it.first, it.second)
        }
        return this
    }
}
