plugins {
    kotlin("jvm")
}

val kspVersion: String by project
val kotestVersion: String by project
val kotlinPoetVersion = "1.11.0"


kotlin {
    dependencies {
        implementation(project(":annotations"))
        implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
        implementation("com.squareup:kotlinpoet:$kotlinPoetVersion")
        implementation("com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
        testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.4.8")
    }
}

tasks.test {
    useJUnitPlatform()
}
