package com.erdemserhat.harmonyhaven.presentation.prev_authentication.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults.flingBehavior
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.common.appcomponents.HarmonyHavenGreetingButton
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite
import com.erdemserhat.harmonyhaven.presentation.common.appcomponents.ClickableHorizontalPagerIndicator
import com.erdemserhat.harmonyhaven.util.customFontKumbhSans
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.DelicateCoroutinesApi


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class, DelicateCoroutinesApi::class)
@Composable
fun WelcomeScreen(navHostController: NavHostController) {
    // Display 3 items
    val pagerState: PagerState = rememberPagerState(pageCount = {
        3
    })


    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )


    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        harmonyHavenGradientGreen,
                        harmonyHavenWhite
                    )
                )
            ),
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

        //HorizontalPagerIndicator dots
        ClickableHorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary,
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
            onClick = { navHostController.navigate(Screen.Login.route) },
            modifier = Modifier.weight(2f)

        )


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
            modifier = Modifier.padding(top = 50.dp)
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
            textAlign = TextAlign.Justify,
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
            buttonText = stringResource(R.string.finish),
            onClick = onClick,
            modifier = modifier

        )
    }

}

