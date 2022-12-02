plugins {
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10" apply false
    id("com.pschsch.artifactory.publish") version "0.8.0" apply false
    id("com.jfrog.artifactory") version "4.13.0" apply false
}

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}
