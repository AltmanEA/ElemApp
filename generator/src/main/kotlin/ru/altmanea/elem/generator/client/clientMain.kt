package ru.altmanea.elem.generator.client

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator

val documentHTML = MemberName("kotlinx.browser", "document")
val reactFC = ClassName("react", "FC")
val reactProps = ClassName("react", "Props")
val reactCreate = MemberName("react", "create")
val reactCreateRoot = MemberName("react.dom.client", "createRoot")
val reactH1 = MemberName("react.dom.html.ReactHTML","h1")

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

    val appCode =
        CodeBlock
            .builder()
            .beginControlFlow("%T<%T>", reactFC, reactProps)
            .beginControlFlow("%M", reactH1)
            .addStatement("+%S", "Hello")
            .endControlFlow()
            .endControlFlow()
            .build()

    val appProp =
        PropertySpec
            .builder("App", reactFC.parameterizedBy(reactProps))
            .initializer(appCode)
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
        .build()
}

