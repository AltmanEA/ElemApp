package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.shared.*

private val newIdFun = MemberName(Def.packageKMongo, "newId")

fun ElemGenerator.elemMongo(): FileSpec {
    val base = elemBase(mongoClass, mongoInners)

    base.mainConstructorBuilder
        .addParameter(
            ParameterSpec
                .builder("id", Def.idClassName.parameterizedBy(mongoClass))
                .defaultValue("%M()", newIdFun)
                .build()
        )

    base.mainClassBuilder
        .addProperty(
            PropertySpec
                .builder("id", Def.idClassName.parameterizedBy(mongoClass))
                .addAnnotation(Def.contextual)
                .initializer("id")
                .build()
        )

    val fileSpec = FileSpec
        .builder(packageName, elem.mongo)
        .addElemBase(base)
    clientToMongo().forEach {
        fileSpec.addFunction(it)
    }
    return fileSpec.build()
}

private fun ElemGenerator.clientToMongo(): List<FunSpec> {
    val result = ArrayList<FunSpec>()

    elem.tables.forEach {
        val values = CodeBlock.builder().indent()
        it.props.forEach{
            values.addStatement("${it.key},")
        }
        values.unindent()
        val funSpec = FunSpec
            .builder("toMongo")
            .receiver(clientInners[it.name]!!)
            .addCode("return %T(\n", mongoInners[it.name]!!)
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
        mainValues.addStatement("${it.name}.map{ it.toMongo() },")
    }
    mainValues.unindent()

    result.add(FunSpec
        .builder("toMongo")
        .receiver(clientClass)
        .addCode("return %T(\n", mongoClass)
        .addCode(mainValues.build())
        .addCode(")")
        .build()
    )

    return result
}