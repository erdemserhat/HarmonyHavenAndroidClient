# **Harmony Haven Android Client**

**Harmony Haven** is an ongoing project under development, utilizing various libraries throughout its development process. Primarily a motivational application, *Harmony Haven* offers users a range of articles from different categories and delivers personalized notifications using artificial intelligence. It also provides users with personalized quotes, adding a touch of inspiration to their daily lives.

## System Operation Overview

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/b02ad5f5-0154-48bf-a813-33b750f34397)



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
- 

## App Screens

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/129db9b2-7ab4-46a2-a781-fbe6cd1b267a)


![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/bc1e3204-9f56-4622-9612-9e1e4912f1ad)




![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/53e949e4-932f-4047-899b-43f3a4dd4eba)

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/08f77661-a1aa-4696-8382-49afca9b01f4)

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/539f627a-0627-480d-aff2-e7a8bd627e1f)

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/f1b7a62a-6dc1-4ef9-af88-dcb5f8cb7449)

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/033d1f4e-e57e-4b5f-aa37-86f42a54d295)

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/604a1476-e685-4424-a11e-c00df950448c)

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/768bc659-b5b3-40f3-b005-63d7d3c52330)















