package com.erdemserhat.harmonyhaven

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.data.local.entities.QuoteEntity
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import com.erdemserhat.harmonyhaven.data.local.repository.QuoteRepository
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.presentation.common.HarmonyHavenTheme
import com.erdemserhat.harmonyhaven.presentation.navigation.MainScreenParams
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.SetupNavGraph
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @Inject
    lateinit var quoteRepository: QuoteRepository


    @Inject
    @Named("FirstInstallingExperience")
    lateinit var firstInstallingExperiencePreferences: SharedPreferences

    @Inject
    lateinit var jwtRepository: JwtTokenRepository


    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        val extraData = intent.getStringExtra("data")
        val isFirstLaunch = firstInstallingExperiencePreferences.getBoolean("isFirstLaunch", true)
        val isLoggedInBefore =
            firstInstallingExperiencePreferences.getBoolean("isLoggedInBefore", false)
        val isJwtExists = firstInstallingExperiencePreferences.getBoolean("isJwtExists", true)

        setContent {

            HarmonyHavenTheme {
                navController = rememberNavController()
                window

                SetupNavGraph(
                    navController = navController,
                    startDestination = when (isLoggedInBefore) {
                        true -> if (isJwtExists) Screen.Main.route else Screen.Login.route
                        false -> if (isFirstLaunch) Screen.Welcome.route else Screen.Login.route },
                    modifier = Modifier,
                    window = window,

                    )

                extraData?.let {
                    val bundleArticle = Bundle()
                    val shouldNavigateToPost = extraData.startsWith("-1")
                    if (shouldNavigateToPost) {
                        val postId = extraData.drop(2)
                        bundleArticle.putParcelable(
                            "article",
                            ArticlePresentableUIModel(
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
                    handleDeepLink(intent)
                    firstInstallingExperiencePreferences.edit().putBoolean("isFirstLaunch", false)
                        .apply()
                }


            }
        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Yeni gelen intent'i işle
        Log.d("testIntentt", "newIntent called")

        handleDeepLink(intent)
        intent.getStringExtra("data")?.let { data ->
            navController.navigate(data)
        }

    }

    // Deep Link'i işleyen fonksiyon
    private fun handleDeepLink(intent: Intent?) {
        Log.d("dsadsadsa", "linkintn")
        val data: Uri? = intent?.data
        data?.let {
            Log.d("dsadsadsa", it.toString())
            // URI bilgilerini al
            val path = it.path // Örnek: "/article/123"
            Log.d("dsadsadsa", path.toString())

            val queryParams = it.query // Sorgu parametreleri (eğer varsa)


            when {
                path?.startsWith("/article") == true -> {
                    val articleId = it.lastPathSegment
                    val bundleArticle = Bundle()

                    if (articleId != null) {
                        bundleArticle.putParcelable(
                            "article",
                            ArticlePresentableUIModel(
                                id = articleId.toInt()
                            )
                        )
                    }


                    navController.navigate(
                        route = Screen.Article.route,
                        args = bundleArticle
                    )
                }

                path == "/quote" -> {

                }

                path == "/home" -> {
                    //navigateToHomeScreen()
                }
            }
        }
    }

}

