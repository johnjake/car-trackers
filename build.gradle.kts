buildscript {
    repositories {
        google()
        mavenCentral {
            content {
                includeGroup("org.jetbrains.kotlinx")
            }
        }
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-beta03")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath("org.gradle.kotlin:gradle-kotlin-dsl-conventions:0.5.0")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.10.0"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint.debug.set(true)
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
