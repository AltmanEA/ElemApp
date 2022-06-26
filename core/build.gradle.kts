group = "ru.altmanea.elem.core"
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
    implementation("org.litote.kmongo:kmongo-serialization:4.6.1")
    implementation("io.ktor:ktor-server-core:2.0.2")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ru.altmanea.elem"
            artifactId = "core"
            version = "0.1"

            from(components["kotlin"])
        }
    }
}