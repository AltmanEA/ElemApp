import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription

class GeneratorsTest : StringSpec({
    "elems" {
        val generator = Generator("ru.test")
        val serverFileSpecs = generator.serverFiles(
            ElemDescription("TestElem")
        )
        serverFileSpecs.size shouldBe 2
        val clientFileSpecs = generator.serverFiles(
            ElemDescription("TestElem")
        )
        clientFileSpecs.size shouldBe 2
    }
})
