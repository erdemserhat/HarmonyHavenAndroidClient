package com.erdemserhat.harmonyhaven.presentation.navigation

import AccountInformationScreen
import QuoteShareScreen
import android.graphics.Bitmap
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.erdemserhat.harmonyhaven.presentation.post_authentication.chat.ChatExperienceCustomizationScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.chat.ChatHistoryScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.chat.ChatIntroScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.chat.ChatScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil.TestIntroScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil.UserProfileScreenViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.test.EnneagramTestScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.HomeScreenNew
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.NotificationScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.scheduler_screen.NotificationSchedulerScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.SettingsScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.about_us.AboutUsScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.saved_articles.SavedArticlesScreen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.LoginScreen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.passwordreset.mail.ForgotPasswordMailScreen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.RegisterScreen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.welcome.WelcomeScreen
import kotlinx.parcelize.Parcelize
import com.erdemserhat.harmonyhaven.presentation.post_authentication.player.MeditationMusic
import com.erdemserhat.harmonyhaven.presentation.post_authentication.player.MusicPlayerScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.journal.JournalScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.journal.JournalEditorScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.liked_quote_screen.LikedQuotesScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.liked_quote_screen.QuoteDetailScreen

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

        composable(route = Screen.AccountInformationScreen.route) {
            AccountInformationScreen(navController)
        }

        composable(route = Screen.EnneagramIntroScreen.route) {
            TestIntroScreen(navController)
        }


        composable(route = Screen.ChatScreen.route) {
            ChatScreen(navController = navController)
        }

        composable(route = Screen.ChatHistoryScreen.route) {
            ChatHistoryScreen(navController = navController)
        }

        composable(route = Screen.ChatIntroScreen.route) {
            ChatIntroScreen(navController = navController)
        }

        composable(route = Screen.EnneagramTestScreen.route) {
            EnneagramTestScreen(navController = navController, sharedViewModel = sharedViewModelUserProfile)
        }

        composable(route = Screen.ChatExperienceCustomizationScreen.route) {
            ChatExperienceCustomizationScreen(navController = navController)
        }

        composable(
            route = Screen.FamousPeopleScreen.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
        ) { backStackEntry ->
            val bundle = backStackEntry.arguments
            val famousPeople = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle?.getParcelableArrayList("famousPeople", com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramFamousPeople::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle?.getParcelableArrayList<com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramFamousPeople>("famousPeople")
            }
            
            famousPeople?.let {
                com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil.FamousPeopleScreen(
                    navController = navController,
                    famousPeople = it
                )
            }
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
        
        composable(
            route = Screen.NotificationScheduler.route,
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
            popEnterTransition = { fadeIn(animationSpec = tween(100)) },
            popExitTransition = { fadeOut(animationSpec = tween(100)) }
        ) {
            NotificationSchedulerScreen(viewModel = hiltViewModel(), navController = navController)
        }

        composable(route = Screen.Settings.route,
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
            popEnterTransition = { fadeIn(animationSpec = tween(100)) },
            popExitTransition = { fadeOut(animationSpec = tween(100)) }
        ) {
            SettingsScreen(navController = navController)

        }
        
        composable(route = Screen.LikedQuotesScreen.route,
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
            popEnterTransition = { fadeIn(animationSpec = tween(100)) },
            popExitTransition = { fadeOut(animationSpec = tween(100)) }
        ) {
            LikedQuotesScreen(navController = navController)
        }

        composable(route = Screen.QuoteDetailScreen.route,
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
            popEnterTransition = { fadeIn(animationSpec = tween(100)) },
            popExitTransition = { fadeOut(animationSpec = tween(100)) }
        ) { backStackEntry ->
            val bundle = backStackEntry.arguments
            val quote = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle?.getParcelable("quote", com.erdemserhat.harmonyhaven.dto.responses.Quote::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle?.getParcelable<com.erdemserhat.harmonyhaven.dto.responses.Quote>("quote")
            }
            
            quote?.let {
                QuoteDetailScreen(
                    quote = it,
                    navController = navController
                )
            }
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

        composable(
            route = "musicPlayer/{musicId}",
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
        ) { backStackEntry ->
            val musicId = backStackEntry.arguments?.getString("musicId")
            // For simplicity, we'll find the music by ID from a predefined list
            // In a real app, you might fetch this from a repository or viewModel
            val meditationMusic = remember {
                listOf(
                        MeditationMusic(
                            id = "1",
                            title = "Meditation Ambient Music",
                            artist = "Deep Healing Relaxing Music",
                            duration = "60:00",
                            imageUrl = "https://images.unsplash.com/photo-1534274988757-a28bf1a57c17?q=80&w=300",
                            audioUrl = "https://www.harmonyhavenapp.com/sources/musics/med1.mp3"
                        ),
                        MeditationMusic(
                            id = "2",
                            title = "White Noise Black Screen",
                            artist = "Sleep, Study, Focus",
                            duration = "10:00:00",
                            imageUrl = "https://www.harmonyhavenapp.com/sources/musics/med2.jpg",
                            audioUrl = "https://www.harmonyhavenapp.com/sources/musics/med2.mp3"
                        ),
                        MeditationMusic(
                            id = "3",
                            title = "Deep, Comforting Black Noise",
                            artist = "Study, Sleep, Tinnitus Relief and Focus",
                            duration = "12:00:00",
                            imageUrl = "https://www.harmonyhavenapp.com/sources/musics/med3.jpg",
                            audioUrl = "https://www.harmonyhavenapp.com/sources/musics/med3.mp3"
                        ),
                        MeditationMusic(
                            id = "4",
                            title = "Relaxing Sleep Music",
                            artist = "Relaxing Sleep Music",
                            duration = "3:00:21",
                            imageUrl = "https://www.harmonyhavenapp.com/sources/musics/med4.jpg",
                            audioUrl = "https://www.harmonyhavenapp.com/sources/musics/med4.mp3"
                        ),
                        MeditationMusic(
                            id = "5",
                            title = "50 Classical Music Masterpieces for Relaxation & the Soul",
                            artist = "Beethoven, Mozart, Chopin, Bach, Vivaldi",
                            duration = "3:25:27",
                            imageUrl = "https://www.harmonyhavenapp.com/sources/musics/med5.jpg",
                            audioUrl = "https://www.harmonyhavenapp.com/sources/musics/med5.mp3"
                        ),
                        MeditationMusic(
                            id = "6",
                            title = "Ambient Study Music To Concentrate",
                            artist = "Studying, Concentration and Memory",
                            duration = "3:57:51",
                            imageUrl = "https://www.harmonyhavenapp.com/sources/musics/med6.jpg",
                            audioUrl = "https://www.harmonyhavenapp.com/sources/musics/med6.mp3"
                        )
                    ).find { it.id == musicId } ?: MeditationMusic(
                    id = "1",
                    title = "Peaceful Rain Sounds",
                    artist = "Nature Sounds",
                    duration = "5:32",
                    imageUrl = "https://images.unsplash.com/photo-1534274988757-a28bf1a57c17?q=80&w=300",
                    audioUrl = "https://commondatastorage.googleapis.com/codeskulptor-demos/DDR_assets/Sevish_-__nbsp_.mp3"
                )
            }
            
            MusicPlayerScreen(
                navController = navController,
                music = meditationMusic
            )
        }
        
        // Journal screens
        composable(
            route = "journal",
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
        ) {
            JournalScreen(navController = navController)
        }
        
        composable(
            route = "journalEditor",
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
        ) {
            JournalEditorScreen(navController = navController)
        }
        
        composable(
            route = "journalDetail/{journalId}",
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
        ) { backStackEntry ->
            val journalId = backStackEntry.arguments?.getString("journalId")
            JournalEditorScreen(
                navController = navController,
                journalId = journalId
            )
        }
        
        composable(
            route = Screen.ChatWithHarmonia.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
        ) {
            // Redirect to the existing ChatScreen for now, since we're using the same functionality
            ChatScreen(navController = navController)
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