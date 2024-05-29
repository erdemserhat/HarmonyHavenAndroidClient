package com.erdemserhat.harmonyhaven.presentation.prev_authentication.splash

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.window.SplashScreen
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.navigation.NavHostController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite

@Composable
fun SplashScreen(navController: NavHostController) {
    val degrees = remember{ Animatable(0f) }
    LaunchedEffect(key1 = true){
        degrees.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1500,
                delayMillis = 0
            )
        )
    }
    Splash(degrees = degrees.value)

}

@Composable
fun Splash(degrees:Float) {
    if(isSystemInDarkTheme()){
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier.rotate(degrees = degrees),
                painter = painterResource(id = R.drawable.harmony_haven_icon),
                contentDescription = stringResource(R.string.app_logo)
            )
        }

    }else {
        Box(
            modifier = Modifier
                .background(Brush.verticalGradient(listOf(harmonyHavenGradientGreen, harmonyHavenGradientGreen)))
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.rotate(degrees = degrees),
                painter = painterResource(id = R.drawable.harmony_haven_icon),
                contentDescription = stringResource(R.string.app_logo)
            )
        }
    }

}

@Preview
@Composable
fun SplashScreenPreview() {
    Splash(0f)
}

@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun SplashScreenDarkPreview() {
    Splash(0f)
}


