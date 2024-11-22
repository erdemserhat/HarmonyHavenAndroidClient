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
    compileSdk = 35



    defaultConfig {
        applicationId = "com.erdemserhat.harmonyhaven"
        minSdk = 24
        targetSdk = 35
        versionCode = 11
        multiDexEnabled =true
        versionName = "1.0.11"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"



        vectorDrawables {
            useSupportLibrary = true
        }
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
        }
    }

    buildTypes {
        //buildConfigField("String", "API_KEY", "\"your_actual_api_key_here\"")--> Use this to add new one

        release {
            buildConfigField("String", "SERVER_URL", "\"http://51.20.136.184:5000/api/\"")

            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            buildConfigField("String", "SERVER_URL", "\"http://192.168.137.214:5000/api/\"")
            //buildConfigField("String", "SERVER_URL", "\"http://51.20.136.184:5000/api/\"")
            isMinifyEnabled = false


        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"  // Update to JVM target 17 to match Java 17

    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
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
    // Android Core Dependencies
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-process:2.8.4")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    //implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")
    implementation("com.google.firebase:protolite-well-known-types:18.0.0")

    // Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Jetpack Compose Dependencies
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Paging Dependencies
    implementation("androidx.paging:paging-common-android:3.3.0")
    implementation("com.google.accompanist:accompanist-pager:0.24.7-alpha")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.24.7-alpha")

    // Room Database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Retrofit and Serialization
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Image Loading
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.coil-kt:coil-gif:2.0.0")

    // Coroutines and Lifecycle
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.6")

    // Media Playback
    implementation("androidx.media3:media3-exoplayer:1.0.0")
    implementation("androidx.media3:media3-ui:1.0.0")

    // Additional Dependencies
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.github.jeziellago:compose-markdown:0.5.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.28.0")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.31.2-alpha")


    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("androidx.credentials:credentials:1.3.0") //1
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0") //2
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    // Testing Dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    implementation ("com.github.SmartToolFactory:Compose-Screenshot:1.0.3")

    implementation ("dev.shreyaspatil:capturable:2.1.0")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
