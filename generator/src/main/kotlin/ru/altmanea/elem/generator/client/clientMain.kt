package ru.altmanea.elem.generator.client

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator

val documentHTML = MemberName("kotlinx.browser", "document")
val reactFC = ClassName("react", "FC")
val reactProps = ClassName("react", "Props")
val reactCreate = MemberName("react", "create")
val reactCreateRoot = MemberName("react.dom.client", "createRoot")

val reactH1 = MemberName("react.dom.html.ReactHTML", "h1")
val reactUl = MemberName("react.dom.html.ReactHTML", "ul")
val reactLi = MemberName("react.dom.html.ReactHTML", "li")
val reactNav = MemberName("react.router.dom", "NavLink")
val reactHashRouter = MemberName("react.router.dom", "HashRouter")


//public fun main(): Unit {
//    val container = document.createElement("div")
//    document.body!!.appendChild(container)
//    createRoot(container)
//        .render(
//            App.create()
//        )
//}
//
//val App = FC<Props> {
//    h1{
//        +"Hello"
//    }
//}


fun Generator.clientMain(): FileSpec {
    val fileSpec = FileSpec
        .builder(config.packageName, "${config.name}ClientMain")

    val navCode = CodeBlock
        .builder()
        .beginControlFlow("%T<%T>", reactFC, reactProps)
        .beginControlFlow("%M", reactUl)
    config.elems.forEach {
        navCode
            .beginControlFlow("%M", reactLi)
            .beginControlFlow("%M", reactNav)
            .addStatement("to = %S", it.name)
            .addStatement("+%S", it.name)
            .endControlFlow()
            .endControlFlow()
    }
    navCode
        .endControlFlow()
        .endControlFlow()

    val navProp =
        PropertySpec
            .builder("Nav", reactFC.parameterizedBy(reactProps))
            .initializer(navCode.build())
            .build()

    val appCode =
        CodeBlock
            .builder()
            .beginControlFlow("%T<%T>", reactFC, reactProps)
            .beginControlFlow("%M", reactHashRouter)
            .addStatement("%N {}", navProp)
            .endControlFlow()
            .endControlFlow()

    val appProp =
        PropertySpec
            .builder("App", reactFC.parameterizedBy(reactProps))
            .initializer(appCode.build())
            .build()



    val mainFun = FunSpec
        .builder("main")
        .addStatement("val container = %M.createElement(%S)", documentHTML, "div")
        .addStatement("%M.body!!.appendChild(container)", documentHTML)
        .addStatement("%M(container).render(%N.%M())", reactCreateRoot, appProp, reactCreate)
        .build()


    return fileSpec
        .addFunction(mainFun)
        .addProperty(appProp)
        .addProperty(navProp)
        .build()
}

