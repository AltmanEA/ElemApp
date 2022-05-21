plugins {
    kotlin("jvm")
}

val kspVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":model"))
    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
}