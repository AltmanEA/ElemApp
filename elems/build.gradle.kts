group = "ru.altmanea.elem.elems"
version = "0.1"

plugins {
    kotlin("multiplatform") version "1.6.21"
    id("com.google.devtools.ksp") version "1.6.21-1.0.5"
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

dependencies {
    add("kspJvm", "ru.altmanea.elem.generator:generator:0.1")
    add("kspJvm", "com.squareup:kotlinpoet:$kotlinPoetVersion")
    add("kspJvm", "com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
    add("kspJs", "ru.altmanea.elem.generator:generator:0.1")
    add("kspJs", "com.squareup:kotlinpoet:$kotlinPoetVersion")
    add("kspJs", "com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
}
