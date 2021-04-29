buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha14")
    }
}

plugins {
    `kotlin-dsl`
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.0-alpha14")
}