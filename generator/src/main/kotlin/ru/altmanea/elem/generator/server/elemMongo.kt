package ru.altmanea.elem.generator.server

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.shared.*

fun ElemGenerator.elemMongo(): FileSpec {
    val base = elemBase(mongoClass, mongoInners)

    base.mainConstructorBuilder
        .addParameter(
            ParameterSpec
                .builder("_id", Mongo.Id.parameterizedBy(mongoClass))
                .defaultValue("%M()", Mongo.newId)
                .build()
        )

    base.mainClassBuilder
        .addProperty(
            PropertySpec
                .builder("_id", Mongo.Id.parameterizedBy(mongoClass))
                .addAnnotation(Serial.Contextual)
                .initializer("_id")
                .build()
        )

    val fileSpec = FileSpec
        .builder(packageName, mongoClass.simpleName)
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