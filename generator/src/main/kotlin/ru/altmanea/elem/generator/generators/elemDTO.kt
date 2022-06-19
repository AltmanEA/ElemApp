package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.utils.cfl


fun Generator.elemDto(description: ElemDescription): FileSpec {
    val className = description.name + "DTO"
    val rowClassConstructors =
        description.tables.map {
            FunSpec
                .constructorBuilder().apply {
                    it.props.map {
                        addParameter(it.key, it.value.type)
                    }
                }.build()
        }
    val rowClasses = description.tables.mapIndexed { index, rd ->
        TypeSpec
            .classBuilder("${description.name}${cfl(rd.name)}")
            .addAnnotation(kotlinx.serialization.Serializable::class)
            .primaryConstructor(rowClassConstructors[index])
            .addProperties(
                rd.props.map {
                    PropertySpec
                        .builder(it.key, it.value.type)
                        .initializer(it.key)
                        .build()
                })
            .build()
    }
    val elemClassConstructor =
        FunSpec
            .constructorBuilder()
            .apply {
                description.props.map {
                    addParameter(it.key, it.value.type)
                }
                description.tables.map {
                    addParameter(
                        ParameterSpec
                            .builder(
                                it.name,
                                LIST.parameterizedBy(
                                    ClassName(
                                        packageName,
                                        "${description.name}${cfl(it.name)}"
                                    )
                                )
                            )
                            .defaultValue("emptyList()")
                            .build()
                    )
                }
            }.build()
    val elemClass = TypeSpec
        .classBuilder(className)
        .addAnnotation(kotlinx.serialization.Serializable::class)
        .primaryConstructor(elemClassConstructor)
        .apply {
            description.props.map {
                addProperty(
                    PropertySpec
                        .builder(it.key, it.value.type)
                        .initializer(it.key)
                        .build()
                )
                description.tables.map {
                    addProperty(
                        PropertySpec
                            .builder(
                                it.name,
                                LIST.parameterizedBy(
                                    ClassName(
                                        packageName,
                                        "${description.name}${cfl(it.name)}"
                                    )
                                )
                            )
                            .initializer(it.name)
                            .build()
                    )
                }
            }
        }.build()
    return FileSpec
        .builder(packageName, className)
        .addType(elemClass)
        .apply {
            rowClasses.map {
                addType(it)
            }
        }
        .build()
}