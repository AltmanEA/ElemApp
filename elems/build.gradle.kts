import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.altmanea.elem.generator.GenPluginExtension
import ru.altmanea.elem.core.config.Config
import ru.altmanea.elem.core.config.ElemDescription

group = "ru.altmanea.elem.elems"
version = "0.1"

plugins {
    kotlin("multiplatform") version "1.7.0"
    id("ru.altmanea.elem.generator") version "0.1"
}

repositories {
    mavenCentral()
    mavenLocal()
}

val kotlinPoetVersion = "1.11.0"

kotlin {
    jvm { }
    js {
        browser()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("ru.altmanea.elem.core:core:0.1")
            }
        }
    }
}

the<GenPluginExtension>().config =
    Json.encodeToString(
        Config(
            "ConfigName",
            listOf(
                ElemDescription("Elem3"),
                ElemDescription("Elem2"),
            )
        )
    )