
plugins {
    kotlin("multiplatform") version "1.3.70"
    id("maven-publish")
    id("com.dorongold.task-tree") version "1.4"
}

repositories {
    mavenCentral()
}

group = "de.hanno.apiclient"
version = "0.0.1-SNAPSHOT"

fun generateRandomPort() = `java.net`.ServerSocket(0).use { it.localPort }

val testServerPort = generateRandomPort()

val ktorVersion = "1.3.1"
kotlin {
    jvm {
        val main by compilations.getting {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    js {
        nodejs {
            testTask {
                useMocha()
            }
        }
    }

    targets.all {
        compilations.all {
            kotlinOptions {
//                allWarningsAsErrors = true
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvm().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("io.ktor:ktor-client-apache:$ktorVersion")
            }
        }
        jvm().compilations["test"].defaultSourceSet {

            tasks.withType<Test> {
                systemProperties(::testServerPort.name to testServerPort)
            }
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        js().compilations["main"].defaultSourceSet {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
                implementation(npm("abort-controller", "3.0.0"))
                implementation(npm("node-fetch", "2.6.0"))
            }
        }
        js().compilations["test"].defaultSourceSet {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
