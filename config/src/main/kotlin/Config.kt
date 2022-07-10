import ru.altmanea.elem.generator.config.*

val config = ElemAppConfig(
    "ConfigName",
    "ru.altmanea.elem.test",
    ElemAppServerConfig(
        "mongodb://docker:mongopw@localhost:49154"
    ),
    listOf(
        ElemDescription(
            "ElemOne",
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
        ),
        ElemDescription(
            "ElemTwo",
            mapOf(
                "name" to PropsType.STRING,
                "age" to PropsType.INT
            ),
            listOf(
                RowDescription(
                    "ThirdTable",
                    mapOf(
                        "number" to PropsType.INT
                    )
                )
            )
        )
    )
)