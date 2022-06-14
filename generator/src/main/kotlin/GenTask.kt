package ru.altmanea.elem.generator

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import ru.altmanea.elem.core.ElemDescription
import java.io.File

open class GenTask : DefaultTask() {

    @get:Input
    lateinit var elemDescription: ElemDescription

    @get:Input
    lateinit var packageName: String

    @get:OutputDirectory
    lateinit var outputDir: File

    @TaskAction
    fun invoke() {
        val className = elemDescription.name
        val elemClass =
            TypeSpec
                .classBuilder(className)
                .build()
        val elemFile =
            FileSpec
                .builder(packageName, className)
                .addType(elemClass)
                .build()
        elemFile.writeTo(outputDir)
    }
}