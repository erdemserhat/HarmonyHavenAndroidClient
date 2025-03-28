package com.erdemserhat.harmonyhaven.presentation.navigation

sealed class Screen(val route:String) {
    //Screens
    object Splash: Screen("splash_screen")
    object Login: Screen("login_screen")
    object Register: Screen("register_screen")
    object ForgotPasswordMail: Screen("forgot_password_mail")
    object ForgotPasswordAuth: Screen("forgot_password_auth")
    object ForgotPasswordReset: Screen("forgot_password_reset")
    object Notification: Screen("notification_screen")
    object Settings: Screen("settings_screen")
    object Profile: Screen("profile_screen")
    object Home: Screen("home_screen")
    object Welcome: Screen("welcome_screen")
    object Dashboard: Screen("dashboard_screen")
    object Mail:Screen("forgot_password_mail_screen")
    object ResetPassword:Screen("forgot_password_code_screen")
    object HomeDev:Screen("home_screen_dev")
    object Article : Screen("article_screen")
    object RecentArticleDev :Screen("recent_articles")
    object Quotes:Screen("quotes_screen")
    object Main:Screen("main_screen")
    object SavedArticles:Screen("saved_articles_screen")
    object AboutUs:Screen("about_us_screen")
    object QuoteMain:Screen("quote_main")
    object Test:Screen("test_screen")
    object QuoteShareScreen:Screen("quote_share_screen")
    object ChatScreen:Screen("chat_screen")
    object ChatIntroScreen:Screen("chat_intro_screen")





}