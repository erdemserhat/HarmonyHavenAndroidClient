package com.erdemserhat.harmonyhaven.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

import androidx.compose.ui.graphics.Color
import com.erdemserhat.harmonyhaven.util.DefaultAppFont

private val Amber700 = Color(0xFF9C27B0)
private val Amber500 = Color(0xFFFF9800)


data class AppColors(
    val material: Colors,
    //Custom Colors.
    val textFieldBorderColor:Color,
    val containerBorderColor:Color,
    val buttonSurfaceColor:Color,
    val buttonSurfaceClickedColor:Color,
    val buttonTextColor:Color,
    val buttonTextClickedColor:Color


) {
    val primary: Color get() = material.primary
    val primaryVariant: Color get() = material.primaryVariant
    val secondary: Color get() = material.secondary
    val secondaryVariant: Color get() = material.secondaryVariant
    val background: Color get() = material.background
    val surface: Color get() = material.surface

    val onPrimary: Color get() = material.onPrimary
    val onSecondary: Color get() = material.onSecondary
    val onBackground: Color get() = material.onBackground
    val onSurface: Color get() = material.onSurface
    val isLight: Boolean get() = material.isLight

    val onError: Color get() = material.onError
    val error: Color get() = material.error
}

/////////////////////      LIGHT THEME           ////////////////////////////////////

private val LightColorPalette = AppColors(
    material = lightColors(
        primary = Color(0xFF333333),
        primaryVariant = Color(0xFF444444),
        background = Color.White,
        secondary = Color(0xFF333333),
        secondaryVariant = Color(0xFFFFFFFF),
        error = Amber700,
        surface = Color(0xFF222222)

    ),
    textFieldBorderColor = Color(0xFF888888),
    containerBorderColor = Color(0xFFD9D9D9),
    buttonSurfaceColor =  Color(0xFF222222),
    buttonSurfaceClickedColor = Color(0xFFCABAFF),
    buttonTextColor = Color(0xFFFFFFFF),
    buttonTextClickedColor = Color(0xFF222222)


)

/////////////////////      BLACK THEME           ////////////////////////////////////
private val DarkColorPalette = AppColors(
    material = darkColors(
        //customizable colors.
        error = Amber500,
        background = Color.Black,
        surface = Color(0xFF222222)



    ),
    textFieldBorderColor = Color.Black,
    containerBorderColor = Color.Black,
    buttonSurfaceColor =  Color(0xFFCABAFF),
    buttonSurfaceClickedColor = Color(0xFF222222),
    buttonTextColor = Color(0xFF222222),
    buttonTextClickedColor = Color(0xFFCABAFF)

)
private val LocalColors = staticCompositionLocalOf { LightColorPalette }
@Composable
fun HarmonyHavenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            colors = colors.material,
            typography = Typography(defaultFontFamily = DefaultAppFont),
            content = content,
        )
    }
}

val MaterialTheme.AppColors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current
