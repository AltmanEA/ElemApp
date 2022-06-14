group = "ru.altmanea.elem.core"
version = "0.1"


plugins {
    kotlin("multiplatform") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    id("maven-publish")
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
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
            }
        }
    }
}