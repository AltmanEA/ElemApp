package ru.altmanea.elem.plugin

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
            "generateServerFiles",
            ServerGenTask::class.java
        ) {
            it.group = "Code generation"
            it.description = "Generate server elem classes and others"

            it.config = extension.config
            it.outputDir = extension.outputDir.asFile
        }

        project.tasks.register(
            "generateClientFiles",
            ClientGenTask::class.java
        ) {
            it.group = "Code generation"
            it.description = "Generate client elem classes and others"

            it.config = extension.config
            it.outputDir = extension.outputDir.asFile
        }
    }
}
