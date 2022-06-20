import kotlinx.serialization.encodeToString

plugins {
    kotlin("js") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    id("ru.altmanea.elem.plugin") version "0.1"
}

repositories {
    mavenLocal()
    mavenCentral()
}

kotlin {
    js {
        browser {}
    }
    sourceSets {
        val main by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
            }
            kotlin.setSrcDirs(kotlin.srcDirs + File("src-gen"))
        }
    }
}
buildscript{
    dependencies{
        classpath("ru.altmanea.elem:config:0.1")
    }
}
the<ru.altmanea.elem.plugin.GenPluginExtension>().config =
    kotlinx.serialization.json.Json.encodeToString(config)