package com.erdemserhat.harmonyhaven.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.presentation.article.ArticleContent
import com.erdemserhat.harmonyhaven.presentation.article.ArticleScreen
import com.erdemserhat.harmonyhaven.presentation.home.HomeScreenContentNew
import com.erdemserhat.harmonyhaven.presentation.home.HomeScreenNew
import com.erdemserhat.harmonyhaven.presentation.home.HomeViewModel
import com.erdemserhat.harmonyhaven.presentation.home.MostReadArticleDev
import com.erdemserhat.harmonyhaven.presentation.home.MostReadHorizontalPagerDev
import com.erdemserhat.harmonyhaven.presentation.login.LoginScreen
import com.erdemserhat.harmonyhaven.presentation.notification.NotificationScreen
import com.erdemserhat.harmonyhaven.presentation.passwordreset.mail.ForgotPasswordMailScreen
import com.erdemserhat.harmonyhaven.presentation.profile.ProfileScreen
import com.erdemserhat.harmonyhaven.presentation.quotes.QuotesScreen
import com.erdemserhat.harmonyhaven.presentation.register.RegisterScreen
import com.erdemserhat.harmonyhaven.presentation.settings.SettingsScreen
import com.erdemserhat.harmonyhaven.presentation.splash.SplashScreen
import com.erdemserhat.harmonyhaven.presentation.welcome.WelcomeScreen
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier // Modifier'ı burada kullanın

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


        composable(
            route = Screen.Home.route,
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
            popEnterTransition = { fadeIn(animationSpec = tween(100)) },
            popExitTransition = { fadeOut(animationSpec = tween(100)) }


        ) {
            HomeScreenNew(navController)

        }

        composable(
            route = Screen.Notification.route,
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
            popEnterTransition = { fadeIn(animationSpec = tween(100)) },
            popExitTransition = { fadeOut(animationSpec = tween(100)) }



        ) {
            NotificationScreen(navController = navController)

        }

        composable(route = Screen.Profile.route,
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
            popEnterTransition = { fadeIn(animationSpec = tween(100)) },
            popExitTransition = { fadeOut(animationSpec = tween(100)) }
        ) {
            SettingsScreen(navController = navController)

        }
        composable(route = Screen.Quotes.route,
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
            popEnterTransition = { fadeIn(animationSpec = tween(100)) },
            popExitTransition = { fadeOut(animationSpec = tween(100)) }
        ) {
            QuotesScreen()

        }

        composable(
          route = Screen.Article.route
        ) {
            ArticleScreen(/*it.arguments?.getString("articleId")?.toInt() ?: 1*/)
        }


    }


}