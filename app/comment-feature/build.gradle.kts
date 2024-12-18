plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.erdemserhat.comment_feature"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }


}



dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.espresso.core)

    // Dependency Injection (Hilt)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation)

    // Networking and Serialization
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx)
    implementation(libs.okhttp)
    implementation(libs.serialization.json)
    implementation(libs.retrofit.gson)

    // Coroutines and Lifecycle
    implementation(libs.coroutines.core)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.runtime.livedata)

    // Image Loading
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // Paging
    implementation(libs.paging.common)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.indicators)

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
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")  // Preview için gerekli bağımlılık


}

kotlin {
    jvmToolchain(17)

}