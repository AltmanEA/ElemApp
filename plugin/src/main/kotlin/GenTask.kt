package ru.altmanea.elem.generator

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import ru.altmanea.elem.generator.config.ElemAppConfig
import java.io.File

open class GenTask : DefaultTask() {

    @get:Input
    lateinit var config: String

    @get:Input
    lateinit var packageName: String

    @get:OutputDirectory
    lateinit var outputDir: File

    @TaskAction
    fun invoke() {
        val config = Json.decodeFromString<ElemAppConfig>(config)
        val generators = Generator(packageName)
        config.elems.forEach {
            val fileSpec = generators.elems(it)
            fileSpec.writeTo(outputDir)
        }
    }
}