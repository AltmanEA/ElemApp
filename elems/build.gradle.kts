import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.altmanea.elem.plugin.GenPluginExtension
import ru.altmanea.elem.generator.config.ElemAppConfig
import ru.altmanea.elem.generator.config.ElemDescription
import ru.altmanea.elem.generator.config.PropsType

group = "ru.altmanea.elem.elems"
version = "0.1"

plugins {
    kotlin("multiplatform") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    id("ru.altmanea.elem.plugin") version "0.1"
}

repositories {
    mavenCentral()
    mavenLocal()
}

kotlin {
    jvm { }
    js {
        browser()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("ru.altmanea.elem:generator:0.1")
            }
            kotlin.setSrcDirs(kotlin.srcDirs + File("src-gen/jvmMain"))
        }
        val jsMain by getting {
            kotlin.setSrcDirs(kotlin.srcDirs + File("src-gen/jsMain"))
        }
    }
}

the<GenPluginExtension>().config =
    Json.encodeToString(
        ElemAppConfig(
            "ConfigName",
            listOf(
                ElemDescription(
                    "TestElem",
                    mapOf(
                        "name" to PropsType.STRING,
                        "age" to PropsType.INT
                    ),
                    listOf(
                        ru.altmanea.elem.generator.config.RowDescription(
                            "firstTable",
                            mapOf(
                                "number" to PropsType.INT
                            )
                        )
                    )
                )
            )
        )
    )