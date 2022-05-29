package ru.altmanea.elem.generator

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.File
import kotlin.io.path.Path

internal class ElemProcessorProviderTest: StringSpec({
    val projectPath = Path(File("").absolutePath).parent.toString()
    val modelPath = "$projectPath\\model\\src\\main\\kotlin\\ru\\altmanea\\elem\\model\\"
    val annotationPath = "$projectPath\\annotations\\src\\main\\kotlin\\ru\\altmanea\\elem\\annotations\\"

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
        result shouldNotBe null
    }
})

