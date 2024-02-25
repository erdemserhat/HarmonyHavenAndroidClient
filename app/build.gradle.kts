
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //kotlin("kapt")
    //id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.paging:paging-common-android:3.3.0-alpha03")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")

    //Navigation Component
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Horizontal Pager and Indicators - Accompanist
    implementation ("com.google.accompanist:accompanist-pager:0.24.7-alpha")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.24.7-alpha")

    //Dependency injection with Hilt
    //implementation("com.google.dagger:hilt-android:2.44")
    //ksp("com.google.dagger:hilt-android-compiler:2.44")

    //room database

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    //ksp("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    //ksp("androidx.room:room-compiler:$room_version")

}
// Allow references to generated code

