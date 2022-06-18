package ru.altmanea.elem.plugin

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import ru.altmanea.elem.generator.Generator
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
        val jvmDir = File(outputDir.path+"/jvmMain/")
        val jsDir = File(outputDir.path+"/jsMain/")
        config.elems.forEach {
            generators
                .serverFiles(it)
                .forEach {
                    it.writeTo(jvmDir)
                }
            generators
                .clientFiles(it)
                .forEach {
                    it.writeTo(jsDir)
                }
        }
    }
}