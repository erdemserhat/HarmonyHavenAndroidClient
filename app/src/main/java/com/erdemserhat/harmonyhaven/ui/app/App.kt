package com.erdemserhat.harmonyhaven.ui.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.ui.login.LoginScreen
import com.erdemserhat.harmonyhaven.ui.navigation.Screens
import com.erdemserhat.harmonyhaven.ui.passwordreset.PasswordResetScreen
import com.erdemserhat.harmonyhaven.ui.register.RegisterScreen


@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Login) {
        composable(Screens.Login) {
            //Login Screen
            LoginScreen(navController = navController)
        }
        composable(Screens.Register) {
            //Register Screen
            RegisterScreen(navController=navController)
        }
        composable(Screens.PasswordReset){
            //Reset Password Screen
            PasswordResetScreen(navController)
        }
    }
}