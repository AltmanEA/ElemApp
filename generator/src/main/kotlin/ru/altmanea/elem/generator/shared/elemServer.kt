package ru.altmanea.elem.generator.shared

import com.squareup.kotlinpoet.*

fun ElemGenerator.elemServer(): FileSpec {
    val base = elemBase(serverClass, serverInners)

    base.mainConstructorBuilder
        .addParameter(
            ParameterSpec
                .builder("id", String::class)
                .defaultValue("\"\"")
                .build()
        )

    base.mainClassBuilder
        .addProperty(
            PropertySpec
                .builder("id", String::class)
                .initializer("id")
                .build()
        )

    val fileSpec = FileSpec
        .builder(packageName, serverClass.simpleName)
        .addElemBase(base)
    mongoToServer().forEach {
        fileSpec.addFunction(it)
    }
    return fileSpec.build()
}

private fun ElemGenerator.mongoToServer(): List<FunSpec> {
    val result = ArrayList<FunSpec>()

    elem.tables.forEach {
        val values = CodeBlock.builder().indent()
        it.props.forEach {
            values.addStatement("${it.key},")
        }
        values.unindent()
        val funSpec = FunSpec
            .builder("toServer")
            .receiver(mongoInners[it.name]!!)
            .addCode("return %T(\n", serverInners[it.name]!!)
            .addCode(values.build())
            .addCode(")")
            .build()
        result.add(funSpec)
    }

    val mainValues = CodeBlock.builder().indent()
    elem.props.forEach {
        mainValues.addStatement("${it.key},")
    }
    elem.tables.forEach {
        mainValues.addStatement("${it.name}.map{ it.toServer() },")
    }
    mainValues.addStatement("_id.toString()")
    mainValues.unindent()

    result.add(
        FunSpec
            .builder("toServer")
            .receiver(mongoClass)
            .addCode("return %T(\n", serverClass)
            .addCode(mainValues.build())
            .addCode(")")
            .build()
    )

    return result
}