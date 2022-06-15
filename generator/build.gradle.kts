group = "ru.altmanea.elem"
version = "0.1"

plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    id("java-gradle-plugin")
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}


dependencies {
    implementation("com.squareup:kotlinpoet:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    implementation("ru.altmanea.elem.core:core:0.1")
}

gradlePlugin {
    plugins {
        create("generator") {
            id = "ru.altmanea.elem.generator"
            implementationClass = "ru.altmanea.elem.generator.GenPlugin"
        }
    }
}