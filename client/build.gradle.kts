plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

val serializationVersion: String by project
val kotlinWrappersVersion = "1.0.0-pre.338"
val crossFetchVersion = "3.1.5"

fun kotlinWrap(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

dependencies {
    implementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:${kotlinWrappersVersion}"))
    implementation(kotlinWrap("react"))
    implementation(kotlinWrap("react-dom"))
    implementation(kotlinWrap("react-router-dom"))
    implementation(kotlinWrap("redux"))
    implementation(kotlinWrap("react-redux"))
    implementation(kotlinWrap("react-query"))
    implementation(kotlinWrap("styled-next"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
    implementation(npm("cross-fetch", crossFetchVersion))
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}