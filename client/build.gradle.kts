import kotlinx.serialization.encodeToString

plugins {
    kotlin("js") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    id("ru.altmanea.elem.plugin") version "0.1"
}

repositories {
    mavenLocal()
    mavenCentral()
}


fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

val kotlinWrappersVersion = "1.0.0-pre.350"

dependencies {
    implementation("ru.altmanea.elem:jslib:0.1")
    implementation(enforcedPlatform(kotlinw("wrappers-bom:$kotlinWrappersVersion")))
    implementation(kotlinw("react"))
    implementation(kotlinw("react-dom"))
    implementation(kotlinw("react-table"))
    implementation(kotlinw("react-router-dom"))
    implementation(kotlinw("react-query"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
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
    sourceSets {
        val main by getting {
            kotlin.setSrcDirs(kotlin.srcDirs + File("src-gen"))
        }

    }
}

rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
    versions.webpackCli.version = "4.10.0"
}

buildscript {
    dependencies {
        classpath("ru.altmanea.elem:config:0.1")
    }
}
the<ru.altmanea.elem.plugin.GenPluginExtension>().config =
    kotlinx.serialization.json.Json.encodeToString(config)

tasks.register<Copy>("copyBuild") {
    from("/build/distributions/client.js", "/build/distributions/client.js.map")
    into("../server/src/main/resources/")
}
tasks.register<Copy>("copyBuildToBuild") {
    from("/build/distributions/client.js", "/build/distributions/client.js.map")
    into("../server/build/resources/main/")
}
tasks.named("build") { finalizedBy("copyBuild") }
tasks.named("build") { finalizedBy("copyBuildToBuild") }
tasks.named("browserDevelopmentWebpack") { finalizedBy("copyBuild") }
tasks.named("browserDevelopmentWebpack") { finalizedBy("copyBuildToBuild") }