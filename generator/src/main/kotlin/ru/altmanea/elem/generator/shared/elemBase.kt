package ru.altmanea.elem.generator.shared

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class ElemBase(
    val mainClassBuilder: TypeSpec.Builder,
    val mainConstructorBuilder: FunSpec.Builder,
    val innerClassBuilders: List<TypeSpec.Builder>,
    val innerConstructorBuilders: List<FunSpec.Builder>
) {
    val mainConstructor
        get() = mainConstructorBuilder.build()
    val mainClass
        get() = mainClassBuilder
            .primaryConstructor(mainConstructor)
            .build()

}

fun FileSpec.Builder.addElemBase(elemBase: ElemBase) =
    addType(elemBase.mainClass)
        .apply {
            elemBase.innerClassBuilders
                .zip(elemBase.innerConstructorBuilders)
                .map {
                    addType(
                        it.first
                            .primaryConstructor(it.second.build())
                            .build()
                    )
                }

        }

fun ElemGenerator.elemBase(
    className: ClassName,
    inners: Map<String, ClassName>
): ElemBase {
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
            .classBuilder(inners[it.name]!!)
            .addAnnotation(Serial.Serializable)
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
                                    inners[it.name]!!
                                )
                            )
                            .defaultValue("emptyList()")
                            .build()
                    )
                }
            }
    val baseClass = TypeSpec
        .classBuilder(className)
        .addAnnotation(Serial.Serializable)
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
                                    inners[it.name]!!
                                )
                            )
                            .initializer(it.name)
                            .build()
                    )
                }
            }
        }
    return ElemBase(
        baseClass,
        baseConstructor,
        innerClasses,
        innerConstructors
    )
}