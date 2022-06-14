package ru.altmanea.elem.generator

import ElemDescriptionJVM
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Property
import ru.altmanea.elem.core.ElemDescription

open class GenPluginExtension(project: Project) {
    private val objects = project.objects

    private val elemDescriptionProperty =
        objects.property(ElemDescriptionJVM::class.java)
            .convention(ElemDescriptionJVM("Test"))
    private val packageNameProperty: Property<String> =
        objects.property(String::class.java)
            .convention("ru.altmanea.elem.model")
    private val outputDirProperty =
        objects.directoryProperty()
            .convention(project.layout.buildDirectory.dir("src-gen"))

    var elemDescription: ElemDescriptionJVM
        get() = elemDescriptionProperty.get()
        set(value) = elemDescriptionProperty.set(value)
    var outputDir: Directory
        get() = outputDirProperty.get()
        set(value) = outputDirProperty.set(value)
    var packageName: String
        get() = packageNameProperty.get()
        set(value) = packageNameProperty.set(value)
}