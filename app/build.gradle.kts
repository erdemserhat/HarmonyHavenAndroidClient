
plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id ("kotlin-parcelize")

    id("org.jetbrains.kotlin.android")
    //id("com.google.devtools.ksp")
    kotlin("kapt")



    // id ("dagger.hilt.android.plugin")
    //id ("kotlin-android-extensions")
}

android {
    namespace = "com.erdemserhat.harmonyhaven"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.erdemserhat.harmonyhaven"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }



}
kotlin {
    jvmToolchain(17)
}


dependencies {

    //Android Core Dependencies

    implementation("androidx.appcompat:appcompat:1.6.1") // AndroidX AppCompat library to make new Android features available on older Android versions.
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")

    //Test Dependencies

    testImplementation("junit:junit:4.13.2") // JUnit test framework.
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // AndroidX JUnit extension.
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Espresso library for UI automation in Android tests.
    androidTestImplementation("androidx.compose.ui:ui-test-junit4") // UI tests for Jetpack Compose.

    //Jetpack Compose Dependencies

    implementation("androidx.compose.material3:material3") // Jetpack Compose Material3 components.
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0") // Android Jetpack Lifecycle components.
    implementation("androidx.activity:activity-compose:1.9.0") // AndroidX Activity Compose library for building Android activities with Jetpack Compose.
    implementation(platform("androidx.compose:compose-bom:2024.05.00")) // BOM (Bill of Materials) for Jetpack Compose libraries.
    implementation("androidx.compose.ui:ui") // Jetpack Compose UI components.
    implementation("androidx.compose.ui:ui-graphics") // Jetpack Compose graphic components.
    implementation("androidx.compose.ui:ui-tooling-preview") // Preview tools for Jetpack Compose.
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00")) // BOM (Bill of Materials) for Jetpack Compose libraries.
    debugImplementation("androidx.compose.ui:ui-tooling") // Developer tools for Jetpack Compose.
    implementation("androidx.navigation:navigation-compose:2.7.7") // Android Navigation Component with Compose support.

    //Paging Dependencies for onboarding screen

    implementation ("com.google.accompanist:accompanist-pager:0.24.7-alpha") // Accompanist Pager library for providing paging functionality.
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.24.7-alpha") // Accompanist Pager Indicators library for providing paging indicators.
    implementation("androidx.paging:paging-common-android:3.3.0") // AndroidX Paging library to extend RecyclerViews with paging capabilities.

    //Room Database
    //2.5.0 is compatible with ksp

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version") // KTX artifact ekleyin



    //Dagger-Hilt
    //2.48 is compatible with ksp
    
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")


    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Retrofit with Kotlin serialization Converter
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    // Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    //Consturcts hiltviewmodels()
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    //for image loading


    //coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation ("androidx.compose.runtime:runtime-livedata:1.6.6")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")


    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries


    implementation ("com.google.code.gson:gson:2.10.1")

    implementation ("com.google.android.material:material:1.12.0")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation ("com.github.jeziellago:compose-markdown:0.5.0")














}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}



