plugins {
    kotlin("jvm")
}

val kspVersion: String by project

kotlin {
    dependencies {
        implementation(project(":annotations"))
        implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
    }
}

