# **Harmony Haven Android Client (Devam Ediyor)**

Harmony Haven, şu anda geliştirilmekte olan bir motivasyon uygulamasıdır. Uygulama, kullanıcılara özel olarak seçilmiş yazarların özlü sözlerini bir akış halinde sunarak, günlük yaşamlarına ilham katar. Ayrıca, çeşitli kategorilerden makaleler sunar ve yapay zeka kullanarak kişiselleştirilmiş bildirimler sağlar.

## Üretim Ortamında Görüntüle
[![psico](https://github.com/user-attachments/assets/388b5df4-2096-4ced-a805-07550d46760d)](https://play.google.com/store/apps/details?id=com.erdemserhat.harmonyhaven)
(Tahminen 07.09.2024 tarihinde Play Store'da erişilebilir olacaktır.)

## Sistem İşleyişi Genel Görünümü

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/b02ad5f5-0154-48bf-a813-33b750f34397)

## Android Mimari Genel Görünümü

(Mimari geliştirme sırasında değişebilir)

```plaintext
app
├── build
└── src
    ├── androidTest
    └── main
        └── java
            └── com.erdemserhat.harmonyhaven
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

## Kullanılan Teknolojiler ve Paradigmalar

- **MVVM (Model-View-ViewModel) mimarisi**: Mimari
- **Clean Architecture**: Mimari
- **SOLID prensipleri**: En İyi Uygulamalar
- **Jetpack Compose**: UI
- **Dagger-Hilt (Bağımlılık Enjeksiyonu)**: DI
- **Room Database**: Yerel Veritabanı
- **Shared Preferences**: Güvenli Veri Saklama
- **Retrofit & OKHttp (REST API)**: Ağ İletişimi
- **Firebase (Analitik, Bulut Mesajlaşma)**: Analitik, Bildirimler
- **Coroutines (Asenkron İletişim)**: Eşzamanlılık
- **LiveData**: Reaktif UI
- **Accompanist Pager**: UI Sayfalama
- **Coil (Görsel Yükleme, GIF)**: Görsel Yükleme
- **Core Splash Screen API**: Splash Screen
- **Compose Markdown**: Markdown Rendering
- **Gson**: JSON Ayrıştırma

## Uygulama Ekranları

![1-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/639a2683-11b1-4bce-8814-027d3adc837e)

![2-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/7cf35236-97cc-4c52-82e6-f3cdb598a62e)

![3-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/e60042f5-42c1-4021-ad3c-d0064421a78e)

![4-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/68fc94d2-c267-414a-9899-c3e4d402d57e)

![5-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/88082c71-4b35-4bf2-867e-f28a9b0cf0a3)

![6-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/34820ee5-ec03-47fb-b404-40875cb2f48c)

![7-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/45009d57-7767-443b-8177-1234b061533e)

![8-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/5afcca94-52d7-437c-b368-dbb76d34876b)

![9-min](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/b7b322bb-1e8c-4589-be61-7f9bea1bbf7c)
