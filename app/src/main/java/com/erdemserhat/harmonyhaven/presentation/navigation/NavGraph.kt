package com.erdemserhat.harmonyhaven.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erdemserhat.harmonyhaven.presentation.article.ArticleContent
import com.erdemserhat.harmonyhaven.presentation.article.ArticleScreen
import com.erdemserhat.harmonyhaven.presentation.article.exArticle
import com.erdemserhat.harmonyhaven.presentation.dashboard.DashboardScreen
import com.erdemserhat.harmonyhaven.presentation.home.HomeScreen
import com.erdemserhat.harmonyhaven.presentation.home.HomeViewModel
import com.erdemserhat.harmonyhaven.presentation.login.LoginScreen
import com.erdemserhat.harmonyhaven.presentation.passwordreset.mail.ForgotPasswordMailScreen
import com.erdemserhat.harmonyhaven.presentation.register.RegisterScreen
import com.erdemserhat.harmonyhaven.presentation.splash.SplashScreen
import com.erdemserhat.harmonyhaven.presentation.welcome.WelcomeScreen
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetupNavGraph(navController:NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route){

        composable(route = Screen.Splash.route){
            SplashScreen(navController = navController)
            // Splash ekranı tamamlandığında bekle ve ardından ana sayfaya geç
            LaunchedEffect(Unit) {
                delay(2000) // 2 saniye bekle
                navController.navigate(Screen.Welcome.route)
            }
        }

        composable(route= Screen.Welcome.route){
            WelcomeScreen(navHostController=navController)
        }

        composable(route = Screen.Login.route){
            LoginScreen(navController = navController)

        }

        composable(route = Screen.Register.route){
            //RegisterScreen(navController = navController)
            RegisterScreen(navController = navController)

        }


        composable(route = Screen.ForgotPasswordMail.route){
            ForgotPasswordMailScreen(navController)

        }


        composable(route = Screen.ForgotPasswordReset.route){
            //ForgotPasswordResetScreen(navController = navController)
        }
        
        //composable(route= Screen.Dashboard.route){
            //DashboardScreen(navHostController = navController)
        //}

        composable(route = Screen.Dashboard.route){
            DashboardScreen(navController)
        }

        composable(route = Screen.Home.route){

        }

        composable(route = Screen.Notification.route){

        }

        composable(route = Screen.Profile.route){

        }

        composable(route = Screen.Article.route){
            ArticleScreen(exArticle)

        }

    }

    
}