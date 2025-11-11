// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    // Define extra properties
    val gradleVersion by extra("8.13.1")
    val kotlinVersion by extra("2.2.21")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
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

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "13.1.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.8" apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    // Configure KtLint after the plugin is applied
    extensions.findByType(org.jlleitschuh.gradle.ktlint.KtlintExtension::class.java)?.apply {
        android.set(true)
        ignoreFailures.set(false)
    }

    // Configure Detekt after the plugin is applied
    extensions.findByType(io.gitlab.arturbosch.detekt.extensions.DetektExtension::class.java)
        ?.apply {
            buildUponDefaultConfig = true
            allRules = true
            ignoreFailures = false
            // Optional config file
            // config.setFrom(rootDir.resolve("config/detekt/detekt.yml"))
        }
}

// Root-level task to run both checks
tasks.register("codeQualityCheck") {
    dependsOn(subprojects.flatMap {
        listOf(
            it.tasks.named("ktlintCheck"),
            it.tasks.named("detekt")
        )
    })
}

//apply(from = "code-quality.gradle.kts")