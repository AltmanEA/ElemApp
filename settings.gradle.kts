pluginManagement {
    // Versions are declared in 'gradle.properties' file
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("js") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("com.google.devtools.ksp") version kspVersion apply false
    }
    repositories {
        gradlePluginPortal()
        google()
    }
 }

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ElemApp"

include(":annotations")
include(":model")
include(":client")
include(":server")
include(":generator")