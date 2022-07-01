package ru.altmanea.elem.plugin

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import ru.altmanea.elem.generator.Generator
import java.io.File

open class ClientGenTask : DefaultTask() {

    @get:Input
    lateinit var config: String

    @get:OutputDirectory
    lateinit var outputDir: File

    @TaskAction
    fun invoke() {
        Generator(
            Json.decodeFromString(config)
        )
            .clientFiles()
            .forEach {
                it.writeTo(outputDir)
            }
    }
}