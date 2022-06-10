package ru.altmanea.elem.generator

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.File
import kotlin.io.path.Path


// https://github.com/tschuchortdev/kotlin-compile-testing/issues/129
internal val KotlinCompilation.Result.workingDir: File get() =
    outputDirectory.parentFile!!

internal fun File.listFilesRecursively(): List<File> {
    return listFiles()?.flatMap { file ->
        if (file.isDirectory) {
            file.listFilesRecursively()
        } else {
            listOf(file)
        }
    } ?: emptyList()
}

val KotlinCompilation.Result.kspGeneratedSources: List<File> get() {
    val kspWorkingDir = workingDir.resolve("ksp")
    val kspGeneratedDir = kspWorkingDir.resolve("sources")
    val kotlinGeneratedDir = kspGeneratedDir.resolve("kotlin")
    val javaGeneratedDir = kspGeneratedDir.resolve("java")
    return kotlinGeneratedDir.listFilesRecursively() +
            javaGeneratedDir.listFilesRecursively()
}

// end https://github.com/tschuchortdev/kotlin-compile-testing/issues/129
internal class ElemProcessorProviderTest: StringSpec({
    val projectPath = Path(File("").absolutePath).parent.toString()
    val modelPath = "$projectPath\\model\\src\\commonMain\\kotlin\\ru\\altmanea\\elem\\model\\"
    val annotationPath = "$projectPath\\core\\src\\commonMain\\kotlin\\ru\\altmanea\\elem\\core\\"

    "Experimental test" {
        val compilation = KotlinCompilation().apply {
            sources = listOf(
                annotationPath + "Elem.kt",
                modelPath + "SimpleElem.kt"
            ).map {
                SourceFile.fromPath(File(it))
            }
            symbolProcessorProviders = listOf(ElemProcessorProvider())
        }
        val result = compilation.compile()
        result.kspGeneratedSources.forEach {
            it.inputStream().bufferedReader().lineSequence().toList()
                .map { println(it) }
        }
    }
})

