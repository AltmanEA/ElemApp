import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.config.PropsType
import ru.altmanea.elem.generator.config.RowDescription

class GeneratorsTest : StringSpec({

    "elems" {
        val generator = Generator(config)
        val serverFileSpecs = generator.serverFiles()
        serverFileSpecs.size shouldBe 3
        val clientFileSpecs = generator.clientFiles()
        clientFileSpecs.size shouldBe 2
    }
})
