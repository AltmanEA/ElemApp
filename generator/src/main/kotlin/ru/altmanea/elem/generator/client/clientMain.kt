package ru.altmanea.elem.generator.client

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.poet.block
import ru.altmanea.elem.generator.shared.path


fun Generator.clientMain(): FileSpec {

    val navCode = CodeBlock.builder().run {
        block("%T<%T>", React.FC, React.Props) {
            block("%M", React.ul) {
                config.elems.forEach {
                    block("%M", React.li) {
                        block("%M", React.NavLink) {
                            addStatement("to = %S", it.name)
                            addStatement("+%S", it.name)
                        }
                    }
                }
            }
        }
    }.build()

    val navProp = PropertySpec
        .builder("Nav", React.FC.parameterizedBy(React.Props))
        .initializer(navCode)
        .build()

    val routerCode = CodeBlock.builder().run {
        block("%T<%T>", React.FC, React.Props) {
            block("%T", ReactRouter.Routes){
                this@clientMain.config.elems.map {
                    block("%T", ReactRouter.Route){
                        addStatement("path = %S", it.path)
                        addStatement("element = ${it.queryComp}.create()")
                    }
                }
            }
        }
    }.build()

    val routerProp = PropertySpec
        .builder("Router", React.FC.parameterizedBy(React.Props))
        .initializer(routerCode)
        .build()

    val queryClientProp = PropertySpec
        .builder("queryClient", ReactQuery.QueryClient)
        .initializer("%T()", ReactQuery.QueryClient)
        .build()

    val appCode = CodeBlock.builder().run {
        block("%T<%T>", React.FC, React.Props) {
            block("%T", React.HashRouter) {
                block("%T", ReactQuery.QueryClientProvider) {
                    addStatement("client = %N", queryClientProp)
                    addStatement("%N {}", navProp)
                    addStatement("%N {}", routerProp)
                }
            }
        }
    }.build()

    val appProp = PropertySpec
        .builder("App", React.FC.parameterizedBy(React.Props))
        .initializer(appCode)
        .build()

    val mainFun = FunSpec.builder("main").run {
        addStatement("val container = %M.createElement(%S)", Browser.document, "div")
        addStatement("%M.body!!.appendChild(container)", Browser.document)
        addStatement("%M(container).render(%N.%M())", React.createRoot, appProp, React.create)
    }


    return FileSpec.builder(config.packageName, "${config.name}ClientMain")
        .run {
            addProperty(queryClientProp)
            addFunction(mainFun.build())
            addProperty(appProp)
            addProperty(navProp)
            addProperty(routerProp)
            build()
        }
}

