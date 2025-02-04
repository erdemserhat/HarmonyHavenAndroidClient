package com.erdemserhat.harmonyhaven.presentation.prev_authentication.welcome

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults.flingBehavior
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.presentation.common.appcomponents.ClickableHorizontalPagerIndicator
import com.erdemserhat.harmonyhaven.presentation.common.appcomponents.HarmonyHavenGreetingButton
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components.HarmonyHavenBackground
import com.erdemserhat.harmonyhaven.ui.theme.customFontKumbhSans
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenIndicatorColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.DelicateCoroutinesApi


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class, DelicateCoroutinesApi::class)
@Composable
fun WelcomeScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val activity = context as? Activity
    val window = activity?.window!!
    window.let {
        WindowCompat.setDecorFitsSystemWindows(
            it,
            false
        ) // content fill the system navbar- status bar
        val insetsController = WindowCompat.getInsetsController(it, it.decorView)

        it.statusBarColor = Color.Transparent.toArgb()
        it.navigationBarColor = Color.Transparent.toArgb()

        insetsController.isAppearanceLightStatusBars = true
        insetsController.isAppearanceLightNavigationBars = true

    }


    // Display 3 items
    val pagerState: PagerState = rememberPagerState(pageCount = {
        3
    })


    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )


    Box{
        HarmonyHavenBackground()
        Column(
            modifier = Modifier
                .background(Color.Transparent)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(
                modifier = Modifier
                    .weight(10f),
                state = pagerState,
                verticalAlignment = Alignment.Top,
                flingBehavior = flingBehavior(state = pagerState),
                userScrollEnabled = true,



                ) { page ->


                PagerScreen(onBoardingPage = pages[page])

            }



            ClickableHorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = harmonyHavenGreen,
                inactiveColor = Color.Gray,
                indicatorWidth = 16.dp,
                indicatorShape = CircleShape,
                spacing = 8.dp,
                pageCount = 3,
                modifier = Modifier
                    .weight(1f),

                )


            FinishButton(
                pagerState = pagerState,
                onClick = { navHostController.navigate(Screen.Register.route) },
                modifier = Modifier
                    .weight(2f)
                    .padding(bottom = 10.dp)

            )

            Spacer(modifier = Modifier.size(100.dp))






        }

    }


}

@Composable
fun Welcome() {


}

@Preview
@Composable
fun WelcomePreview() {
    val navHostController = rememberNavController()
    WelcomeScreen(navHostController = navHostController)

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(
            painter = painterResource(onBoardingPage.image),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 50.dp)
                .aspectRatio(16f / 9f) // Aspect ratio burada belirtiliyor
        )
        Text(
            text = onBoardingPage.title,
            fontFamily = customFontKumbhSans,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)

        )
        Text(
            text = onBoardingPage.description,
            fontFamily = customFontKumbhSans,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(25.dp)
        )


    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FinishButton(
    pagerState: PagerState,
    onClick: () -> Unit = {},
    modifier: Modifier
) {
    AnimatedVisibility(
        visible = pagerState.currentPage == 2
    ) {
        HarmonyHavenGreetingButton(
            buttonText = "Başla",
            onClick = onClick,
            modifier = modifier

        )


    }

}

