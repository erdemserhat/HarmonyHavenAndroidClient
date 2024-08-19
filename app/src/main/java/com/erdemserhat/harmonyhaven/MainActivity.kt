package com.erdemserhat.harmonyhaven

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.presentation.common.HarmonyHavenTheme
import com.erdemserhat.harmonyhaven.presentation.navigation.MainScreenParams
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.SetupNavGraph
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @Inject
    lateinit var jwtTokenRepository: JwtTokenRepository

    @Inject
    lateinit var userUseCases: UserUseCases

    @Inject
    lateinit var articleUseCases: ArticleUseCases


    @SuppressLint(
        "UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation",
        "CoroutineCreationDuringComposition"
    )


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        val extraData = intent.getStringExtra("data")
        if (extraData != null) {
            Log.d("testIntentt", "Received data: $extraData")
        } else {
            Log.d("testIntentt", "No data received")
        }

        installSplashScreen()

        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPrefs.getBoolean("isFirstLaunch", true)
        val isLoggedInBefore = sharedPrefs.getBoolean("isLoggedInBefore", false)

        setContent {

            HarmonyHavenTheme {
                navController = rememberNavController()
                window



                SetupNavGraph(
                    navController = navController,
                    startDestination =when(isLoggedInBefore){
                        true -> Screen.QuoteMain.route
                        false-> if (isFirstLaunch) Screen.Welcome.route else Screen.Register.route
                    },
                    modifier = Modifier, // Padding değerlerini burada kullanın
                    window = window

                )

                extraData?.let {
                    val bundleArticle = Bundle()
                    val shouldNavigateToPost = extraData.startsWith("-1")
                    if (shouldNavigateToPost) {
                        val postId = extraData.drop(2)
                        bundleArticle.putParcelable(
                            "article",
                            ArticleResponseType(
                                id = postId.toInt()
                            )
                        )

                        navController.navigate(
                            route = Screen.Article.route,
                            args = bundleArticle
                        )

                    } else {
                        val bundleMain = Bundle()
                        val screenCode = extraData.toInt()

                        if (screenCode == 2) {

                        } else {
                            bundleMain.putParcelable(
                                "params",
                                MainScreenParams(screenNo = screenCode)
                            )
                            navController.navigate(
                                route = Screen.Main.route,
                                args = bundleMain
                            )

                        }


                    }

                }

                LaunchedEffect(key1 = Unit) {
                    sharedPrefs.edit().putBoolean("isFirstLaunch", false).apply()
                }

                // Eğer intent ile veri geldiyse, ilgili ekrana yönlendir

            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Yeni gelen intent'i işle
        Log.d("testIntentt", "newIntent called")
        intent.getStringExtra("data")?.let { data ->
            navController.navigate(data)
        }
    }

}

@Composable
fun SetStatusBarAppearance(statusBarColor: Color, darkIcons: Boolean) {
    val window = (LocalView.current.context as? ComponentActivity)?.window
    window?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            it.statusBarColor = statusBarColor.toArgb()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val insetsController = WindowCompat.getInsetsController(window, window.decorView)
            insetsController.isAppearanceLightStatusBars = darkIcons
        }
    }
}

@Composable
fun SetSystemBarsAppearance(
    statusBarColor: Color,
    statusBarDarkIcons: Boolean,
    navigationBarColor: Color,
    navigationBarDarkIcons: Boolean
) {
    val window = (LocalView.current.context as? ComponentActivity)?.window
    window?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            it.statusBarColor = statusBarColor.toArgb()
            it.navigationBarColor = navigationBarColor.toArgb()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val insetsController = WindowCompat.getInsetsController(it, it.decorView)
            insetsController.isAppearanceLightStatusBars = statusBarDarkIcons
            insetsController.isAppearanceLightNavigationBars = navigationBarDarkIcons
        }
    }
}


