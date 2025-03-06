package com.erdemserhat.harmonyhaven

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
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

import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior.StableState


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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        enableEdgeToEdge() // Add this line.
        window.isNavigationBarContrastEnforced = false
        super.onCreate(savedInstanceState)


        val extraData = intent.getStringExtra("data")
        val isFirstLaunch = firstInstallingExperiencePreferences.getBoolean("isFirstLaunch", true)
        val isLoggedInBefore =
            firstInstallingExperiencePreferences.getBoolean("isLoggedInBefore", false)
        val isJwtExists = firstInstallingExperiencePreferences.getBoolean("isJwtExists", true)

        setContent {

            // Add this block:
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }

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


                /*

                val bundleArticle = Bundle().apply {
                    putParcelable(
                        "article",
                        ArticlePresentableUIModel(
                            id = 46 // String'i Int'e çevir
                        )
                    )
                }

                // Article ekranına yönlendir
                navController.navigate(
                    route = Screen.Article.route,
                    args = bundleArticle
                )

                 */
                



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
        Log.d("DeepLinkHandler", "Link intent alındı")
        val data: Uri? = intent?.data

        data?.let { uri ->
            Log.d("DeepLinkHandler", "URI: $uri")

            // URI'nin yolunu al
            val pathSegments = uri.pathSegments // Örnek: ["articles", "29", "5-dakikalik-meditasyonla-zihninizi-degistirin-ve-daha-sakin-olun"]

            // Eğer yol segmentleri varsa ve "articles" içeriyorsa
            if (pathSegments.size >= 2 && pathSegments[0] == "articles") {
                val articleId = pathSegments[1] // İkinci segment id'yi temsil eder (örneğin, "29" veya "32")
                Log.d("DeepLinkHandler", "Article ID: $articleId")

                // Article ID'yi kullanarak Bundle oluştur
                val bundleArticle = Bundle().apply {
                    putParcelable(
                        "article",
                        ArticlePresentableUIModel(
                            id = articleId.toInt() // String'i Int'e çevir
                        )
                    )
                }

                // Article ekranına yönlendir
                navController.navigate(
                    route = Screen.Article.route,
                    args = bundleArticle
                )
            } else {
                Log.d("DeepLinkHandler", "Geçersiz veya tanımlanamayan URI yolu: $uri")
            }
        }
    }

}



