import kotlinx.serialization.encodeToString

plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    id("ru.altmanea.elem.plugin") version "0.1"
    application
}

repositories {
    mavenLocal()
    mavenCentral()
}

sourceSets.main {
    java.srcDirs("src/main/kotlin", "src-gen")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("ru.altmanea.elem:generator:0.1")
    implementation("org.litote.kmongo:kmongo-serialization:4.6.1")
    implementation("org.litote.kmongo:kmongo-id-serialization:4.6.1")
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-log4j12:1.7.36")
}

buildscript {
    dependencies {
        classpath("ru.altmanea.elem:config:0.1")
    }
}
the<ru.altmanea.elem.plugin.GenPluginExtension>().config =
    kotlinx.serialization.json.Json.encodeToString(config)