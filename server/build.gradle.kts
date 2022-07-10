import kotlinx.serialization.encodeToString

plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    id("ru.altmanea.elem.plugin") version "0.1"
    application
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
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
    implementation("ru.altmanea.elem:core:0.1")
    implementation("org.litote.kmongo:kmongo-serialization:4.6.1")
    implementation("org.litote.kmongo:kmongo-id-serialization:4.6.1")
    implementation("io.ktor:ktor-server-core-jvm:2.0.3")
    implementation("io.ktor:ktor-server-netty-jvm:2.0.3")
    implementation("io.ktor:ktor-server-content-negotiation:2.0.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.2")
    implementation("io.ktor:ktor-server-html-builder:2.0.2")
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-log4j12:1.7.36")
    testImplementation("io.kotest:kotest-runner-junit5:5.3.1")
    testImplementation("io.kotest:kotest-assertions-core:5.3.1")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.0.3")
}

buildscript {
    dependencies {
        classpath("ru.altmanea.elem:config:0.1")
    }
}
the<ru.altmanea.elem.plugin.GenPluginExtension>().config =
    kotlinx.serialization.json.Json.encodeToString(config)