package ru.altmanea.elem.generator.shared

import com.squareup.kotlinpoet.ClassName
import ru.altmanea.elem.generator.config.ElemAppConfig
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.server.elemMongo

class ElemGenerator(
    val config: ElemAppConfig,
    val elem: ElemDescription
) {
    val packageName = config.packageName

    val clientClass = ClassName(
        packageName,
        "${elem.name.upperFirstLetter}Client"
    )
    val mongoClass = ClassName(
        packageName,
        "${elem.name.upperFirstLetter}Mongo"
    )
    val clientInners = elem.tables.associate {
        it.name to ClassName(
            packageName,
            "${elem.name.upperFirstLetter}Client${it.name.upperFirstLetter}"
        )
    }
    val mongoInners = elem.tables.associate {
        it.name to ClassName(
            packageName,
            "${elem.name.upperFirstLetter}Mongo${it.name.upperFirstLetter}"
        )
    }

    fun server() =
        listOf(
            elemClient(),
            elemMongo()
        )

    fun client() =
        listOf(
            elemClient()
        )
}