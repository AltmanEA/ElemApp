package ru.altmanea.elem.generator.client

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.poet.Bracket
import ru.altmanea.elem.generator.poet.block
import ru.altmanea.elem.generator.shared.*

fun ElemGenerator.elemComp(): FileSpec {
    val dataType = List::class.asClassName().parameterizedBy(serverClass)

    val queryCompCode = CodeBlock.builder().run {
        val url = config.serverConfig.run {
            "http://${serverHost}:${serverPort}/${apiPath}/${elem.path}"
        }
        block("%T<%T>", React.FC, React.Props) {
            block(
                "val query = %M<%T, Any, %T, %T>",
                ReactQuery.useQuery, dataType, dataType, ReactQuery.QueryKey,
                bracket = Bracket.Round
            ) {
                addStatement("queryKey = %S.unsafeCast<QueryKey>(),", "${elem.name}Query")
                block("queryFn = ") {
                    add("%M(\n\t%S\n", JSLib.fetch, url)
                    block(").then ") {
                        addStatement(
                            "%T.decodeFromString(%T(${elem.server}.serializer()), it)",
                            Serial.Json, Serial.ListSerializer
                        )
                    }
                }
            }
            addStatement("if (query.isLoading) %M { +\"Loading ..\" }", React.div)
            addStatement("else if (query.isError) %M { +\"Error!\" }", React.div)
            block("else") {
                block("child", bracket = Bracket.Round) {
                    block("${elem.tableComp}.%M", React.create) {
                        addStatement("elems = query.data ?: emptyList()")
                    }
                }
            }
        }
        build()
    }
    val queryComp = PropertySpec
        .builder(elem.queryComp, React.FC.parameterizedBy(React.Props))
        .initializer(queryCompCode)
        .build()

    val tableCompProps = TypeSpec
        .interfaceBuilder(elem.tableProps)
        .addSuperinterface(React.Props)
        .addModifiers(KModifier.EXTERNAL)
        .addProperty(
            PropertySpec
                .builder("elems", dataType)
                .mutable()
                .build()
        )
        .build()

    val tableCompType = React.FC.parameterizedBy(ClassName(packageName, elem.tableProps))
    val tableCompCode = CodeBlock.builder().run {
        block("%T", tableCompType) {
            block("%M", React.ul) {
                block("it.elems.map") {
                    block("%M", React.li) {
                        addStatement("+it.name")
                    }
                }
            }
        }
        build()
    }
    val tableComp = PropertySpec
        .builder(elem.tableComp, tableCompType)
        .initializer(tableCompCode)
        .build()



    return FileSpec.builder(packageName, elem.comp).run {
        addType(tableCompProps)
        addProperty(queryComp)
        addProperty(tableComp)
        build()
    }
}