package com.erdemserhat.harmonyhaven.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.presentation.article.ArticleContent
import com.erdemserhat.harmonyhaven.presentation.article.ArticleScreen
import com.erdemserhat.harmonyhaven.presentation.dashboard.DashboardScreen
import com.erdemserhat.harmonyhaven.presentation.home.HomeScreen
import com.erdemserhat.harmonyhaven.presentation.home.HomeViewModel
import com.erdemserhat.harmonyhaven.presentation.home.MostReadArticleDev
import com.erdemserhat.harmonyhaven.presentation.home.MostReadHorizontalPagerDev
import com.erdemserhat.harmonyhaven.presentation.login.LoginScreen
import com.erdemserhat.harmonyhaven.presentation.passwordreset.mail.ForgotPasswordMailScreen
import com.erdemserhat.harmonyhaven.presentation.register.RegisterScreen
import com.erdemserhat.harmonyhaven.presentation.splash.SplashScreen
import com.erdemserhat.harmonyhaven.presentation.welcome.WelcomeScreen
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetupNavGraph(navController: NavHostController,startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {


        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navHostController = navController)
        }

        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)

        }

        composable(route = Screen.Register.route) {
            //RegisterScreen(navController = navController)
            RegisterScreen(navController = navController)

        }


        composable(route = Screen.ForgotPasswordMail.route) {
            ForgotPasswordMailScreen(navController)

        }


        composable(route = Screen.ForgotPasswordReset.route) {
            //ForgotPasswordResetScreen(navController = navController)
        }

        //composable(route= Screen.Dashboard.route){
        //DashboardScreen(navHostController = navController)
        //}

        composable(route = Screen.Dashboard.route) {
            DashboardScreen(navController)
        }

        composable(route = Screen.Home.route) {

        }

        composable(route = Screen.Notification.route) {

        }

        composable(route = Screen.Profile.route) {

        }

        composable(
            route = Screen.Article.route + "/{articleId}"
        ) {
            ArticleScreen(it.arguments?.getString("articleId")?.toInt() ?: 1)
        }


    }


}