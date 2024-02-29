package com.erdemserhat.harmonyhaven.navigation

sealed class Screen(val route:String) {
    object Splash:Screen("splash_screen")
    object Login:Screen("login_screen")
    object Register:Screen("register_screen")
    object PasswordReset:Screen("password_reset_screen")
    object Notification:Screen("notification_screen")
    object Profile:Screen("profile_screen")
    object Home:Screen("home_screen")
    object Welcome:Screen("welcome_screen")
    object Dashboard:Screen("dashboard_screen")



}