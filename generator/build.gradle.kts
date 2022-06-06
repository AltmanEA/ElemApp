group = "ru.altmanea.elem.generator"
version = "0.1"

plugins {
    kotlin("multiplatform") version "1.6.21"
    id("com.google.devtools.ksp") version "1.6.21-1.0.5"
}

val kotlinPoetVersion = "1.11.0"
val kotestVersion = "5.3.0"
val compileTestingVersion = "1.4.8"

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
                implementation(files("/../annotations/build/libs/annotations-jvm-0.1.jar"))
                implementation("com.google.devtools.ksp:symbol-processing-api:1.6.21-1.0.5")
                implementation("com.squareup:kotlinpoet:$kotlinPoetVersion")
                implementation("com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
                implementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
                implementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:$compileTestingVersion")
            }
        }
    }
}


