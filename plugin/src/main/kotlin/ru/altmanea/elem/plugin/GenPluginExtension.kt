package ru.altmanea.elem.plugin

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Property

open class GenPluginExtension(project: Project) {
    private val objects = project.objects

    private val configProperty =
        objects.property(String::class.java)
            .convention("")
    private val packageNameProperty: Property<String> =
        objects.property(String::class.java)
            .convention("ru.altmanea.elem.model")
    private val outputDirProperty =
        objects.directoryProperty()
            .convention(project.layout.buildDirectory.dir("/../src-gen"))

    var config: String
        get() = configProperty.get()
        set(value) = configProperty.set(value)
    var outputDir: Directory
        get() = outputDirProperty.get()
        set(value) = outputDirProperty.set(value)
    var packageName: String
        get() = packageNameProperty.get()
        set(value) = packageNameProperty.set(value)
}