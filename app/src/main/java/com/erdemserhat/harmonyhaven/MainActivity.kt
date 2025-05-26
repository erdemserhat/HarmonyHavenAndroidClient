package com.erdemserhat.harmonyhaven

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import androidx.compose.foundation.background

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen
import androidx.core.view.WindowInsetsControllerCompat
import com.erdemserhat.harmonyhaven.data.api.SSEClient
import com.erdemserhat.harmonyhaven.domain.usecase.ChatUseCase
import com.erdemserhat.harmonyhaven.domain.usecase.VersionControlUseCase
import com.erdemserhat.harmonyhaven.presentation.common.NetworkErrorScreen
import com.erdemserhat.harmonyhaven.presentation.common.UpdateAvailableScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.chat.ChatScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.NotificationScreen
import com.google.android.material.bottomsheet.BottomSheetBehavior.StableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext


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

    @Inject
    lateinit var versionControlUseCase: VersionControlUseCase

    private var currentVersionCode = BuildConfig.VERSION_CODE.toInt()

    @Inject
    lateinit var sseClient: SSEClient

    @Inject
    lateinit var useCase: ChatUseCase

    // Android 14+ için bildirim izni isteme
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        Log.d("MainActivity", "Notification permission granted: $isGranted")
        if (!isGranted) {
            Log.w("MainActivity", "Notification permission denied - media notifications may not work properly")
        }
    }

    @SuppressLint("NewApi", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        enableEdgeToEdge() // Add this line.
        window.isNavigationBarContrastEnforced = false
        super.onCreate(savedInstanceState)
        val internetAvailability = isInternetAvailable(this)

        // Android 14+ için bildirim izni kontrolü ve isteme
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("MainActivity", "Requesting notification permission for Android 14+")
                notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            } else {
                Log.d("MainActivity", "Notification permission already granted")
            }
        }

        val extraData = intent.getStringExtra("data")
        val isFirstLaunch = firstInstallingExperiencePreferences.getBoolean("isFirstLaunch", true)
        val isLoggedInBefore =
            firstInstallingExperiencePreferences.getBoolean("isLoggedInBefore", false)
        val isJwtExists = firstInstallingExperiencePreferences.getBoolean("isJwtExists", true)


        setContent {
            var isInternetAvailable by rememberSaveable { mutableStateOf(internetAvailability) }


            navController = rememberNavController()
            val context = LocalContext.current


            var scope = rememberCoroutineScope()


            var versionStatus by rememberSaveable {
                mutableIntStateOf(-1)
            }
            splashScreen.setKeepOnScreenCondition(SplashScreen.KeepOnScreenCondition {
                versionStatus == -1
            })
            LaunchedEffect(Unit) {
                versionStatus = versionControlUseCase.executeRequest(currentVersionCode)

            }
            if (versionStatus == 0) {
                UpdateAvailableScreen(navController)
            } else if (versionStatus == -2) {
                NetworkErrorScreen(
                    onRetry = {
                        scope.launch {
                            versionStatus = -1
                            versionStatus = versionControlUseCase.executeRequest(currentVersion = currentVersionCode.toInt())
                        }
                     }
                )
            } else {
                // Add this block:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    window.isNavigationBarContrastEnforced = false
                }

                HarmonyHavenTheme {
                    if (isInternetAvailable) {
                        SetupNavGraph(
                            navController = navController,
                            startDestination = when (isLoggedInBefore) {
                                true -> if (isJwtExists) Screen.Main.route else Screen.Login.route
                                false -> if (isFirstLaunch) Screen.Welcome.route else Screen.Login.route
                            },
                            modifier = Modifier,
                            window = window,

                            )

                    } else {
                        NetworkErrorScreen(
                            onRetry = {
                                scope.launch {
                                    isInternetAvailable = isInternetAvailable(context)
                                }
                            })

                    }




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

                            val screenCode = extraData.toInt()
                            val bundleMain = Bundle()

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
                        firstInstallingExperiencePreferences.edit()
                            .putBoolean("isFirstLaunch", false)
                            .apply()
                    }


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
            val pathSegments =
                uri.pathSegments // Örnek: ["articles", "29", "5-dakikalik-meditasyonla-zihninizi-degistirin-ve-daha-sakin-olun"]

            // Eğer yol segmentleri varsa ve "articles" içeriyorsa
            if (pathSegments.size >= 2 && pathSegments[0] == "articles") {
                val articleId =
                    pathSegments[1] // İkinci segment id'yi temsil eder (örneğin, "29" veya "32")
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


@SuppressLint("ServiceCast")
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnected == true
}






