import ru.altmanea.elem.generator.config.*

val config = ElemAppConfig(
    "ConfigName",
    "ru.altmanea.elem.test",
    ElemAppServerConfig(
        "mongodb://docker:mongopw@localhost:49153"
    ),
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