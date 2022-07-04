package ru.altmanea.elem.generator.poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec

fun FunSpec.Builder.control(
    controlFlow: String = "",
    vararg args: Any,
    builder: FunSpec.Builder.()->Unit
): FunSpec.Builder{
    beginControlFlow(controlFlow, *args)
    builder()
    endControlFlow()
    return this
}

fun CodeBlock.Builder.control(
    controlFlow: String = "",
    vararg args: Any,
    builder: CodeBlock.Builder.()->Unit
): CodeBlock.Builder{
    beginControlFlow(controlFlow, *args)
    builder()
    endControlFlow()
    return this
}