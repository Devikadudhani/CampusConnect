// Top-level build file
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // Add this line
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.8.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.0")
        classpath("com.google.gms:google-services:4.4.1")
    }
}

plugins {
    id("com.android.application") version "8.8.0" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false

}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}