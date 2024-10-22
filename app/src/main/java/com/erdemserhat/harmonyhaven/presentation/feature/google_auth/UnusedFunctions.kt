package com.erdemserhat.harmonyhaven.presentation.feature.google_auth

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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

