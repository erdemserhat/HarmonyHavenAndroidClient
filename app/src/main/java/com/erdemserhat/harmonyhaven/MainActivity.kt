package com.erdemserhat.harmonyhaven

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.SetupNavGraph
import com.erdemserhat.harmonyhaven.presentation.common.HarmonyHavenTheme
import com.erdemserhat.harmonyhaven.presentation.navigation.MainScreenParams
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

        setContent {
            //val systemUiController = rememberSystemUiController()
            //systemUiController.setSystemBarsColor(color = Color.Black, darkIcons = true)

            HarmonyHavenTheme {
                SetStatusBarAppearance(
                    statusBarColor = Color.White, // Durum çubuğunun arka plan rengi
                    darkIcons = true // Simgelerin siyah olmasını sağla
                            // )
                )
              //  )

              //  WindowCompat.setDecorFitsSystemWindows(
               //     window,
                //    false
               // )

                //window.setFlags(
                 //   WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                  //  WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

                navController = rememberNavController()



                SetupNavGraph(
                    navController = navController,
                    startDestination = if (isFirstLaunch) Screen.Welcome.route else Screen.Main.route,
                    modifier = Modifier // Padding değerlerini burada kullanın
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
                        bundleMain.putParcelable("params", MainScreenParams(screenNo = screenCode))
                        navController.navigate(
                            route = Screen.Main.route,
                            args = bundleMain
                        )

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
            insetsController?.isAppearanceLightStatusBars = darkIcons
        }
    }
}


