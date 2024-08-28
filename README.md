# **Harmony Haven Android Client (Work In Progress)**

**Harmony Haven** is an ongoing project under development, utilizing various libraries throughout its development process. Primarily a motivational application, *Harmony Haven* offers users a range of articles from different categories and delivers personalized notifications using artificial intelligence. It also provides users with personalized quotes, adding a touch of inspiration to their daily lives.
![ps_icon](https://github.com/user-attachments/assets/04e9ea31-5fc0-42bb-a987-2ae5ad51fe8e)

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

![1-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/639a2683-11b1-4bce-8814-027d3adc837e)

![2-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/7cf35236-97cc-4c52-82e6-f3cdb598a62e)

![3-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/e60042f5-42c1-4021-ad3c-d0064421a78e)

![4-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/68fc94d2-c267-414a-9899-c3e4d402d57e)

![5-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/88082c71-4b35-4bf2-867e-f28a9b0cf0a3)

![6-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/34820ee5-ec03-47fb-b404-40875cb2f48c)

![7-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/45009d57-7767-443b-8177-1234b061533e)

![8-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/5afcca94-52d7-437c-b368-dbb76d34876b)

![9-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/b7b322bb-1e8c-4589-be61-7f9bea1bbf7c)























