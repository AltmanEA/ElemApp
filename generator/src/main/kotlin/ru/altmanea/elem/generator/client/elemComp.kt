package ru.altmanea.elem.generator.client

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.poet.Bracket
import ru.altmanea.elem.generator.poet.block
import ru.altmanea.elem.generator.shared.ElemGenerator
import ru.altmanea.elem.generator.shared.path

fun ElemGenerator.elemComp(): FileSpec {

    val queryCompCode = CodeBlock.builder().run {
        val url = config.serverConfig.run{
            "https://${serverHost}:${serverPort}/${apiPath}/${elem.path}"
        }
        block("%T<%T>", React.FC, React.Props) {
            block(
                "val query = %M<Any, Any, Any, %T>",
                ReactQuery.useQuery, ReactQuery.QueryKey,
                bracket = Bracket.Round
            ) {
                add("queryKey = %S.unsafeCast<QueryKey>(),\n", "${elem.name}Query")
                block("queryFn = ") {
                    add("%M(\n%S\n", JSLib.fetch, url)
                    block(").then ") {
                        add("\n")
                    }
                }
            }
        }
        build()
    }
    val queryComp = PropertySpec
        .builder("${elem.name}QueryComp", React.FC.parameterizedBy(React.Props))
        .initializer(queryCompCode)
        .build()

    val tableCompProps = TypeSpec
        .interfaceBuilder("${elem.name}TableProps")
        .addSuperinterface(React.Props)
        .addProperty(
            PropertySpec
                .builder("elems", List::class.asClassName().parameterizedBy(clientClass))
                .mutable()
                .build()
        )
        .build()

    val tableCompType = React.FC.parameterizedBy(ClassName(packageName, "${elem.name}TableProps"))
    val tableCompCode = CodeBlock.builder().run {
        block("%T", tableCompType) {

        }
        build()
    }
    val tableComp = PropertySpec
        .builder("${elem.name}TableComp", tableCompType)
        .initializer(tableCompCode)
        .build()



    return FileSpec.builder(packageName, "${elem.name}Comp").run {
        addType(tableCompProps)
        addProperty(queryComp)
        addProperty(tableComp)
        build()
    }
}