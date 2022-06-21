package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.shared.ImportDef
import ru.altmanea.elem.generator.shared.cfl
import kotlin.reflect.KClass

typealias ClassBuilder = Pair<TypeSpec.Builder, FunSpec.Builder>
typealias ElemBase = Pair<ClassBuilder, List<ClassBuilder>>

fun ClassBuilder.build() =
    first
        .primaryConstructor(second.build())
        .build()

fun ClassBuilder.addPropParam(
    name: String,
    type: KClass<*>,
    modifiers: List<KModifier> = emptyList()
) {
    second.addParameter(name, type, modifiers)
    first.addProperty(
        PropertySpec
            .builder(name, type, modifiers)
            .initializer(name)
            .build()
    )
}

fun Generator.elemBase(description: ElemDescription, className: String): ElemBase {
    val innerConstructors =
        description.tables.map {
            FunSpec
                .constructorBuilder().apply {
                    it.props.map {
                        addParameter(it.key, it.value.type)
                    }
                }
        }
    val innerClasses = description.tables.map {
        TypeSpec
            .classBuilder("${className}${cfl(it.name)}")
            .addAnnotation(ImportDef.serializable)
            .addProperties(
                it.props.map {
                    PropertySpec
                        .builder(it.key, it.value.type)
                        .initializer(it.key)
                        .build()
                })
    }
    val baseConstructor =
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
                                        "${className}${cfl(it.name)}"
                                    )
                                )
                            )
                            .defaultValue("emptyList()")
                            .build()
                    )
                }
            }
    val baseClass = TypeSpec
        .classBuilder(className)
        .addAnnotation(ImportDef.serializable)
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
                                        "${className}${cfl(it.name)}"
                                    )
                                )
                            )
                            .initializer(it.name)
                            .build()
                    )
                }
            }
        }
    return Pair(
        Pair(baseClass, baseConstructor),
        innerClasses.zip(innerConstructors)
    )
}