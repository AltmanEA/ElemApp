group = "ru.altmanea.elem.config"
version = "0.1"

plugins{
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("ru.altmanea.elem:generator:0.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ru.altmanea.elem"
            artifactId = "config"
            version = "0.1"

            from(components["kotlin"])
        }
    }
}