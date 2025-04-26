package com.erdemserhat.harmonyhaven.presentation.navigation

import AccountInformationScreen
import QuoteShareScreen
import android.graphics.Bitmap
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.presentation.feature.google_auth.TestScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables.ArticleScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.chat.ChatIntroScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.chat.ChatScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil.UserProfileScreenViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.test.EnneagramTestScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.HomeScreenNew
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.NotificationScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.SettingsScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.about_us.AboutUsScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.saved_articles.SavedArticlesScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainScreen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.LoginScreen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.passwordreset.mail.ForgotPasswordMailScreen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.RegisterScreen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.welcome.WelcomeScreen
import kotlinx.parcelize.Parcelize

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    window: Window,
    sharedViewModel: SharedViewModel = hiltViewModel(),
    sharedViewModelUserProfile: UserProfileScreenViewModel = hiltViewModel()
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize().background(Color.Black)

    ) {

        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navHostController = navController)


        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                navController = navController,
            )

        }

        composable(route = Screen.Register.route) {
            RegisterScreen(navController = navController)

        }


        composable(route = Screen.ForgotPasswordMail.route) {
            ForgotPasswordMailScreen(navController)

        }


        composable(route = Screen.ForgotPasswordReset.route) {
            //ForgotPasswordResetScreen(navController = navController)
        }

        composable(route = Screen.Profile.route) {
            AccountInformationScreen(navController)



        }


        composable(route = Screen.ChatScreen.route) {
            ChatScreen(navController = navController)


        }

        composable(route = Screen.ChatIntroScreen.route) {
            ChatIntroScreen(navController = navController)

        }

        composable(route = Screen.EnneagramTestScreen.route) {
            EnneagramTestScreen(navController = navController, sharedViewModel = sharedViewModelUserProfile)
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

        composable(route = Screen.Settings.route,
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
            //QuotesScreen()

        }

        composable(
            route = Screen.Article.route,
        ) { backStackEntry ->
            val bundle = backStackEntry.arguments
            val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle?.getParcelable("article", ArticlePresentableUIModel::class.java)

            } else {
                bundle?.getParcelable("article") as? ArticlePresentableUIModel
            }
            article?.let {

                Log.d("articleCaser",article.id.toString())



                ArticleScreen(article, navController)
            }
        }


        composable(
            route = Screen.Main.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(700)
                )
            }
        ) { backStackEntry ->

            val bundle = backStackEntry.arguments
            val params = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle?.getParcelable("params", MainScreenParams::class.java)
            } else {
                bundle?.getParcelable("params") as? MainScreenParams
            }


            if (params == null) {

                AppMainScreen(navController = navController, window = window, viewModel = sharedViewModel, userProfileSharedViewModel = sharedViewModelUserProfile)

            } else {
                AppMainScreen(navController, params, window,sharedViewModel, userProfileSharedViewModel = sharedViewModelUserProfile)

            }


        }


        composable(route = Screen.SavedArticles.route) {
            SavedArticlesScreen(navController = navController)
        }
        composable(route = Screen.AboutUs.route) {
            AboutUsScreen(navController = navController)
        }


        composable(route = Screen.Test.route) {
            TestScreen(navController = navController)
        }

        composable(
            route = Screen.QuoteShareScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, // Ekranın altından yukarı
                    animationSpec = tween(700) // 700 ms animasyon süresi
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down, // Ekranın altına kayarak çıkış
                    animationSpec = tween(700) // 700 ms animasyon süresi
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, // Geri dönüşte yukarıdan kayarak giriş
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down, // Geri dönüşte aşağıya kayarak çıkış
                    animationSpec = tween(700)
                )
            }
        ) { backStackEntry ->
            val bundle = backStackEntry.arguments
            val params = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle?.getParcelable("params", QuoteShareScreenParams::class.java)
            } else {
                bundle?.getParcelable("params") as? QuoteShareScreenParams
            }

            QuoteShareScreen(
                navController = navController,
                params = params!!,
                modifier = Modifier
            )

        }



    }


}

fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val nodeId = graph.findNode(route = route)?.id
    if (nodeId != null) {
        navigate(nodeId, args, navOptions, navigatorExtras)
    }
}

@Parcelize
data class MainScreenParams(
    var screenNo: Int = -1,
    val articleId: Int = 0
) : Parcelable

@Parcelize
data class QuoteShareScreenParams(
    var quote: String = "",
    var author:String ="",
    val quoteUrl: String = "",
    val bitmap: Bitmap? = null
) : Parcelable