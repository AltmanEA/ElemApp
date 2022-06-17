import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.altmanea.elem.core.config.ElemDescription
import ru.altmanea.elem.generator.Generators

class GeneratorsTest : StringSpec({
    "elems" {
        val generator = Generators("ru.test")
        val fileSpec = generator.elems(
            ElemDescription("TestElem")
        )
        fileSpec.toString() shouldBe "package ru.test\n" +
                "\n" +
                "public class TestElem\n"
    }
})
