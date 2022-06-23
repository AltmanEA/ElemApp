import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.altmanea.elem.generator.Generator

class GeneratorsTest : StringSpec({

    "elems" {
        val generator = Generator(config)
        val serverFileSpecs = generator.serverFiles()
        serverFileSpecs.size shouldBe 3
        val clientFileSpecs = generator.clientFiles()
        clientFileSpecs.size shouldBe 2
    }
})
