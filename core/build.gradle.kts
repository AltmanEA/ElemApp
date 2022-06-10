group = "ru.altmanea.elem.core"
version = "0.1"

plugins {
    kotlin("multiplatform") version "1.6.21"
    id("maven-publish")
}

repositories {
    mavenCentral()
    mavenLocal()
}

kotlin {
    jvm { }
    js {
        browser()
    }
}
