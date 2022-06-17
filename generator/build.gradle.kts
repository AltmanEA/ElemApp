group = "ru.altmanea.elem"
version = "0.1"

plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    id("java-gradle-plugin")
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}

// Kotest require Gradle 7.5 (embedded kotlin)
val functionalTest: SourceSet by sourceSets.creating
val functionalTestTask = tasks.register<Test>("functionalTest") {
    description = "Runs the functional tests."
    group = "verification"
    testClassesDirs = functionalTest.output.classesDirs
    classpath = functionalTest.runtimeClasspath
    mustRunAfter(tasks.test)
}
tasks.check {
    dependsOn(functionalTest)
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    implementation("ru.altmanea.elem.core:core:0.1")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.3.1")
    testImplementation("io.kotest:kotest-assertions-core:5.3.1")
    "functionalTestImplementation"(project)
    "functionalTestImplementation"("io.kotest:kotest-runner-junit5:5.3.1")
    "functionalTestImplementation"("io.kotest:kotest-assertions-core:5.3.1")
    "functionalTestImplementation"("ru.altmanea.elem.core:core:0.1")
}

gradlePlugin {
    plugins {
        create("generator") {
            id = "ru.altmanea.elem.generator"
            implementationClass = "ru.altmanea.elem.generator.GenPlugin"
        }
    }
    testSourceSets(functionalTest)
}
