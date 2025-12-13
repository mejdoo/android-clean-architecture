// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "14.0.1" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.8" apply false
}

buildscript {
    val kotlinVersion = "2.2.21"
    val gradleVersion = "8.13.2"

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:$kotlinVersion")
        // central code-quality plugin classpaths so applied script can use apply(plugin=...)
        classpath("org.jlleitschuh.gradle:ktlint-gradle:14.0.1")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// Clean task
tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

apply(from = "code-quality.gradle.kts")