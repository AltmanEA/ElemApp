plugins{
    kotlin("js") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation(npm("cross-fetch", "3.1.5"))
    testImplementation(kotlin("test"))
}


kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
 }

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ru.altmanea.elem"
            artifactId = "jslib"
            version = "0.1"

            from(components["kotlin"])
        }
    }
}