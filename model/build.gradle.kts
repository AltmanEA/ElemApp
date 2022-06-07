group = "ru.altmanea.elem.model"
version = "0.1"

plugins {
    kotlin("multiplatform") version "1.6.21"
    id("com.google.devtools.ksp") version "1.6.21-1.0.5"
}

val kotlinPoetVersion = "1.11.0"

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            repositories {
                mavenCentral()
            }
            dependencies {
                implementation(files("/../generator/build/libs/generator-jvm-0.1.jar"))
                implementation("com.squareup:kotlinpoet:$kotlinPoetVersion")
                implementation("com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")

            }
        }
    }
}

dependencies {
    add("kspJvm", files("/../generator/build/libs/generator-jvm-0.1.jar"))
    add("kspJvm", "com.squareup:kotlinpoet:$kotlinPoetVersion")
    add("kspJvm", "com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
}
