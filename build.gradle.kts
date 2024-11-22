// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.library") version "8.1.4" apply false
    id("com.android.application") version "8.1.2" apply false
    //stable version of kotlin
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.21"


    //KSP
    //id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false

    //Hilt
    id("com.google.dagger.hilt.android") version "2.50" apply false

    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.1" apply false


}
