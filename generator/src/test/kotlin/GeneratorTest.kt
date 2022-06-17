import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription

class GeneratorsTest : StringSpec({
    "elems" {
        val generator = Generator("ru.test")
        val fileSpec = generator.elems(
            ElemDescription("TestElem")
        )
        fileSpec.toString() shouldBe "package ru.test\n" +
                "\n" +
                "public class TestElem\n"
    }
})
