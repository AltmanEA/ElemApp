import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.altmanea.elem.plugin.GenPluginExtension
import ru.altmanea.elem.generator.config.ElemAppConfig
import ru.altmanea.elem.generator.config.ElemDescription

group = "ru.altmanea.elem.elems"
version = "0.1"

plugins {
    kotlin("multiplatform") version "1.7.0"
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
        val jvmMain by getting {
            dependencies {
                implementation("ru.altmanea.elem:generator:0.1")
            }
        }
    }
}

