package ru.altmanea.elem.generator.client

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.poet.control



fun Generator.clientMain(): FileSpec {

    val navCode = CodeBlock.builder().run {
        control("%T<%T>", React.FC, React.Props) {
            control("%M", React.ul) {
                config.elems.forEach {
                    control("%M", React.li) {
                        control("%M", React.NavLink) {
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
        addStatement("+")
    }.build()

    val routerProp = PropertySpec
        .builder("Router", React.FC.parameterizedBy(React.Props))
        .initializer(routerCode)
        .build()

    val appCode = CodeBlock.builder().run {
        control("%T<%T>", React.FC, React.Props) {
            control("%M", React.HashRouter) {
                addStatement("%N {}", navProp)
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
            addFunction(mainFun.build())
            addProperty(appProp)
            addProperty(navProp)
//            addProperty(routerProp)
            build()
        }
}

