import io.kotest.core.spec.style.StringSpec
import io.kotest.engine.spec.tempdir
import ru.altmanea.elem.generator.Generator

class GeneratorsTest : StringSpec({

    "elems" {
        val dir = tempdir()
        val generator = Generator(config)
        generator.serverFiles().forEach {
            it.writeTo(dir)
        }
        generator.clientFiles().forEach(){
            it.writeTo(dir)
        }
    }
})
