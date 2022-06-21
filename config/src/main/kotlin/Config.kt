import ru.altmanea.elem.generator.config.ElemAppConfig
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.config.PropsType
import ru.altmanea.elem.generator.config.RowDescription

val config = ElemAppConfig(
    "ConfigName",
    "ru.altmanea.elem.test",
    listOf(
        ElemDescription(
            "TestElem",
            mapOf(
                "name" to PropsType.STRING,
                "age" to PropsType.INT
            ),
            listOf(
                RowDescription(
                    "firstTable",
                    mapOf(
                        "number" to PropsType.INT
                    )
                ),
                RowDescription(
                        "secondTable",
                mapOf(
                    "link" to PropsType.LINK
                )
            )
            )
        )
    )
)