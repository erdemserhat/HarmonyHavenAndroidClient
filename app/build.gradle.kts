plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    kotlin("kapt")

}

android {
    namespace = "com.erdemserhat.harmonyhaven"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.erdemserhat.harmonyhaven"
        minSdk = 24
        targetSdk = 35
        versionCode = project.findProperty("VERSION_CODE")?.toString()?.toInt() ?: 1
        versionName = project.findProperty("VERSION_NAME")?.toString() ?: "1.0.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        buildConfigField("String", "SSL_CERTIFICATE", "\"${project.findProperty("SSL_PINNING_CERTIFICATE")}\"")
        buildConfigField("String", "SERVER_MAIN_PATTERN", "\"${project.findProperty("SERVER_MAIN_PATTERN")}\"")


    }
    buildTypes {

        release {
                isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY_PRODUCTION")}\"")
            buildConfigField("String", "SERVER_URL", "\"${project.findProperty("SERVER_URL_PRODUCTION")}\"")
        }

        debug {
            isMinifyEnabled = false
            buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY_DEVELOPMENT")}\"")
            buildConfigField("String", "SERVER_URL", "\"${project.findProperty("SERVER_URL_DEVELOPMENT")}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"

    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    //This version 1.5.15 targeting kotlin 1.9.25 please consider version compatibility before migration
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    externalNativeBuild {

    }


}

kotlin {
    jvmToolchain(17)

}



dependencies {
   // implementation(project("comment-feature"))


    // Core Dependencies
    implementation(libs.appcompat)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.process)
    implementation(libs.splashscreen)

    // Notifications and Media
    implementation("androidx.media:media:1.6.0")

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.protolite)

    // Dependency Injection (Hilt)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation)

    // Jetpack Compose
    implementation(libs.androidx.material3)
    implementation(libs.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx.v287)
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    androidTestImplementation(platform(libs.compose.bom.test))
    implementation(libs.navigation.compose)

    // Paging
    implementation(libs.paging.common)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.indicators)

    // Room Database
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // Networking and Serialization
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx)
    implementation(libs.okhttp)
    implementation(libs.serialization.json)
    implementation(libs.retrofit.gson)

    // Image Loading
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // Coroutines and Lifecycle
    implementation(libs.coroutines.core)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.runtime.livedata)

    // Media Playback
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)

    // Additional Dependencies
    implementation(libs.material)
    implementation(libs.compose.markdown)
    implementation(libs.accompanist.systemui)
    implementation(libs.accompanist.animation)
    implementation(libs.accompanist.placeholder)
    implementation(libs.play.services.auth)
    implementation(libs.credentials)
    implementation(libs.credentials.play)
    implementation(libs.google.id)
    implementation(libs.dotenv)

    // Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Capturable Library
    implementation(libs.capturable)

    implementation(libs.palette)

    val markwon_version = "4.6.2"

    implementation ("io.noties.markwon:core:$markwon_version")
    implementation ("io.noties.markwon:ext-strikethrough:$markwon_version")
    implementation ("io.noties.markwon:ext-tables:$markwon_version")
    implementation ("io.noties.markwon:html:$markwon_version")
    implementation ("io.noties.markwon:linkify:$markwon_version")
    implementation ("io.noties.markwon:ext-tasklist:$markwon_version")
    implementation (libs.androidx.autofill)
    implementation ("androidx.media:media:1.6.0")
    implementation("com.google.android.play:review:2.0.2")

    // For Kotlin users, also import the Kotlin extensions library for Play In-App Review:
    implementation("com.google.android.play:review-ktx:2.0.2")
    implementation (libs.androidx.material.icons.extended)



}


// Allow references to generated code
kapt {
    correctErrorTypes = true
}


