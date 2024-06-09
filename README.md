# **Harmony Haven**

**Harmony Haven** is an ongoing project under development, utilizing various libraries throughout its development process. Primarily a motivational application, *Harmony Haven* offers users a range of articles from different categories and delivers personalized notifications using artificial intelligence. It also provides users with personalized quotes, adding a touch of inspiration to their daily lives.

## System Operation Overview

![arcitecture](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/94e25da1-4de3-4c36-abed-01964333d386)

## Android Architecture Overview


```plaintext
app
├── build
└── src
    ├── androidTest
    └── main
        └── java
            └── com.erdemsah.armonyhaven
                ├── data
                │   ├── api
                │   │   ├── article
                │   │   ├── fcm
                │   │   └── user
                │   └── local
                │       ├── dao
                │       ├── entities
                │       └── repository
                ├── di
                │   ├── database
                │   ├── network
                │   └── usecase
                ├── domain
                │   ├── model
                │   │   └── rest
                │   └── usecase
                │       ├── article
                │       ├── password_reset
                │       ├── user
                │       └── validation
                ├── dto
                │   ├── requests
                │   └── responses
                ├── presentation
                │   ├── common
                │   │   └── appcomponents
                │   ├── navigation
                │   ├── post_authentication
                │   │   ├── article
                │   │   ├── home
                │   │   │   └── components
                │   │   ├── notification
                │   │   └── profile
                │   │       ├── about_us
                │   │       ├── account_information
                │   │       └── saved_articles
                │   ├── prev_authentication
                │   │   ├── login
                │   │   │   ├── components
                │   │   │   ├── state
                │   │   │   └── util
                │   │   ├── passwordreset
                │   │   │   ├── auth
                │   │   │   ├── mail
                │   │   │   └── reset
                │   │   ├── register
                │   │   │   ├── components
                │   │   │   └── state
                │   │   ├── splash
                │   │   └── welcome
                │   └── unused
                │       └── profile
                ├── test
                │   └── unitTest
                └── util
```

## Technologies and Paradigms Used:


- **MVVM (Model-View-ViewModel) architecture**: Architecture
- **Clean Architecture**: Architecture
- **SOLID principles**: Best Practices
- **Jetpack Compose**: UI
- **Dagger-Hilt (Dependency Injection)**: DI
- **Room Database**: Local Database
- **Shared Preferences**: Secure Data Retention
- **Retrofit & OKHttp (REST API)**: Networking
- **Firebase (Analytics, Cloud Messaging)**: Analytics, Notifications
- **Coroutines (Asynchronous Communication)**: Concurrency
- **LiveData**: Reactive UI
- **Accompanist Pager**: UI Paging
- **Coil (Image Loading, GIF)**: Image Loading
- **Core Splash Screen API**: Splash Screen
- **Compose Markdown**: Markdown Rendering
- **Gson**: JSON Parsing
