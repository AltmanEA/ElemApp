package ru.altmanea.elem.generator.poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec

enum class Bracket(val open: String, val close: String){
    Round("(", ")"),
    Curly("{", "}"),
    Square("[","]")
}

fun FunSpec.Builder.block(
    controlFlow: String = "",
    vararg args: Any,
    builder: FunSpec.Builder.()->Unit
): FunSpec.Builder{
    beginControlFlow(controlFlow, *args)
    builder()
    endControlFlow()
    return this
}

fun CodeBlock.Builder.block(
    controlFlow: String = "",
    vararg args: Any,
    bracket: Bracket = Bracket.Curly,
    end: String = "\n",
    builder: CodeBlock.Builder.()->Unit
): CodeBlock.Builder{
    addStatement("$controlFlow ${bracket.open}", *args)
    indent()
    builder()
    unindent()
    add(bracket.close + end)
    return this
}

