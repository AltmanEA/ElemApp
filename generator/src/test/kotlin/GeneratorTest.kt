import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.config.PropsType
import ru.altmanea.elem.generator.config.RowDescription

class GeneratorsTest : StringSpec({
    val elemDescription = ElemDescription(
        "TestElem",
        mapOf(
            "name" to PropsType.STRING,
            "age" to PropsType.INT
        ),
        listOf(
            RowDescription(
                "first_table",
                mapOf(
                    "number" to PropsType.INT
                )
            )
        )
    )

    "elems" {
        val generator = Generator("ru.test")
        val serverFileSpecs = generator.serverFiles(
            elemDescription
        )
        serverFileSpecs.size shouldBe 2
        val clientFileSpecs = generator.serverFiles(
            elemDescription
        )
        clientFileSpecs.size shouldBe 2
    }
})
