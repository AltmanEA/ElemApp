package ru.altmanea.elem.generator.generators

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import ru.altmanea.elem.generator.Generator
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.shared.ImportDef
import ru.altmanea.elem.generator.shared.ufl
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

fun Generator.elemBase(elem: ElemDescription, className: String): ElemBase {
    val innerConstructors =
        elem.tables.map {
            FunSpec
                .constructorBuilder().apply {
                    it.props.map {
                        addParameter(it.key, it.value.type)
                    }
                }
        }
    val innerClasses = elem.tables.map {
        TypeSpec
            .classBuilder("${className}${ufl(it.name)}")
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
                elem.props.map {
                    addParameter(it.key, it.value.type)
                }
                elem.tables.map {
                    addParameter(
                        ParameterSpec
                            .builder(
                                it.name,
                                LIST.parameterizedBy(
                                    ClassName(
                                        packageName,
                                        "${className}${ufl(it.name)}"
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
            elem.props.map {
                addProperty(
                    PropertySpec
                        .builder(it.key, it.value.type)
                        .initializer(it.key)
                        .build()
                )
                elem.tables.map {
                    addProperty(
                        PropertySpec
                            .builder(
                                it.name,
                                LIST.parameterizedBy(
                                    ClassName(
                                        packageName,
                                        "${className}${ufl(it.name)}"
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