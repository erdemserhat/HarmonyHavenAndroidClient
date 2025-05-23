package com.erdemserhat.harmonyhaven.presentation.navigation

sealed class Screen(val route: String) {
    //Screens
    data object Splash : Screen("splash_screen")
    data object Login : Screen("login_screen")
    data object Register : Screen("register_screen")
    data object ForgotPasswordMail : Screen("forgot_password_mail")
    data object ForgotPasswordAuth : Screen("forgot_password_auth")
    data object ForgotPasswordReset : Screen("forgot_password_reset")
    data object Notification : Screen("notification_screen")
    data object NotificationScheduler : Screen("notification_scheduler_screen")
    data object Settings : Screen("settings_screen")
    data object Profile : Screen("profile_screen")
    data object Home : Screen("home_screen")
    data object Welcome : Screen("welcome_screen")
    data object Dashboard : Screen("dashboard_screen")
    data object Mail : Screen("forgot_password_mail_screen")
    data object ResetPassword : Screen("forgot_password_code_screen")
    data object HomeDev : Screen("home_screen_dev")
    data object Article : Screen("article_screen")
    data object RecentArticleDev : Screen("recent_articles")
    data object Quotes : Screen("quotes_screen")
    data object Main : Screen("main_screen")
    data object SavedArticles : Screen("saved_articles_screen")
    data object AboutUs : Screen("about_us_screen")
    data object QuoteMain : Screen("quote_main")
    data object Test : Screen("test_screen")
    data object QuoteShareScreen : Screen("quote_share_screen")
    data object ChatScreen : Screen("chat_screen")
    data object ChatIntroScreen : Screen("chat_intro_screen")
    data object EnneagramTestScreen : Screen("enneagram_test_screen")
    data object EnneagramIntroScreen : Screen("enneagram_intro_screen")


}