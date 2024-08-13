package com.erdemserhat.harmonyhaven

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.SetupNavGraph
import com.erdemserhat.harmonyhaven.presentation.common.HarmonyHavenTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject


@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @Inject
    lateinit var jwtTokenRepository: JwtTokenRepository

    @Inject
    lateinit var userUseCases: UserUseCases

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        installSplashScreen()

        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPrefs.getBoolean("isFirstLaunch", true)
        setContent {
            HarmonyHavenTheme {
                navController = rememberNavController()

                SetupNavGraph(
                    navController = navController,
                    startDestination = if (isFirstLaunch) Screen.Welcome.route else Screen.Main.route,
                    modifier = Modifier // Padding değerlerini burada kullanın
                )

                LaunchedEffect(key1 = Unit) {
                    sharedPrefs.edit().putBoolean("isFirstLaunch", false).apply()
                }



            }


        }


    }

}




