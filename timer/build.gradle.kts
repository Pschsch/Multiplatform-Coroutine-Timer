plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("maven-publish")
    id("com.jfrog.artifactory")
    id("com.pschsch.artifactory.publish")
}

group = findProperty("mavenGroup") ?: throw IllegalStateException()
version = findProperty("mavenArtifactVersion") ?: throw IllegalStateException()

kotlin {
    android {
        publishAllLibraryVariants()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()
    js(IR) {
        browser()
        nodejs()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }
        val androidMain by getting
        val jvmMain by getting
        val jsMain by getting {
            dependsOn(commonMain)
        }
        val iosMain by creating {
            dependsOn(commonMain)
        }
        listOf(
            "iosX64",
            "iosArm64",
            "iosSimulatorArm64"
        ).forEach {
            sourceSets.getByName(it + "Main").dependsOn(iosMain)
        }
    }
}

android {
    namespace = "com.pschsch.core.kmm.ktor.client.setup"
    compileSdk = 33
    sourceSets.getByName("main").manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 14
        targetSdk = 33
    }
    libraryVariants.all {
        generateBuildConfigProvider?.get()?.enabled = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

artifactoryPublishConfig {
    kotlinMultiplatform {
        includeAndroid = true
        includeIOS = true
        includeJVM = true
        includeJS = true
    }
}