package ru.altmanea.elem.generator

import org.gradle.api.Plugin
import org.gradle.api.Project


class GenPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        val extension = project.extensions.create(
            "simpleCodegen",
            GenPluginExtension::class.java,
            project
        )

        project.tasks.register(
            "generation",
            GenTask::class.java
        ) {
            it.group = "Code generation"
            it.description = "Generate elem classes and others"

            it.packageName = extension.packageName
            it.elemDescription = extension.elemDescription
            it.outputDir = extension.outputDir.asFile
        }
    }
}
