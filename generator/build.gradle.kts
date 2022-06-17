group = "ru.altmanea.elem.generator"
version = "0.1"


plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    `maven-publish`
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("com.squareup:kotlinpoet:1.12.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.3.1")
    testImplementation("io.kotest:kotest-assertions-core:5.3.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ru.altmanea.elem"
            artifactId = "generator"
            version = "0.1"

            from(components["kotlin"])
        }
    }
}
