package com.erdemserhat.harmonyhaven.presentation.appcomponents.home2

import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.presentation.home.ArticlePrototype
import com.erdemserhat.harmonyhaven.presentation.home.components.shimmerBrush
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenTitleTextColor
import com.erdemserhat.harmonyhaven.util.customFontInter
import kotlinx.coroutines.time.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MostReadHorizontalPager(
    navController: NavController,
    articleList: List<ArticleResponseType>
) {
    if (articleList.isNotEmpty()) {


    val pagerState = rememberPagerState(pageCount = {
        4//list size
    })
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(5000) // Değişim aralığı, burada 5 saniye olarak belirtilmiştir. İstediğiniz süreyi ayarlayabilirsiniz.
            pagerState.animateScrollToPage(
                (pagerState.currentPage + 1) % pagerState.pageCount,
                animationSpec = TweenSpec(durationMillis = 1000)
            )
        }
    }




    Column(

    ) {
        Text(
            text = "Recent Articles",
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp),
            fontFamily = customFontInter,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenTitleTextColor,
            fontSize = MaterialTheme.typography.titleMedium.fontSize

        )
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(
                modifier = Modifier,
                state = pagerState,
                verticalAlignment = Alignment.Top,
            ) { page ->
                val articleId = articleList[page].id

                ArticlePrototype(articleList[page]) { navController.navigate(Screen.Article.route + "/$articleId") }


            }

            Row(

                horizontalArrangement = Arrangement.Center
            ) {

                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) harmonyHavenDarkGreenColor else harmonyHavenComponentWhite
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(16.dp)
                    )
                }

            }


        }
    }
    }else{
        MostReadHorizontalPagerShimmy()
    }

}


@Composable
fun MostReadHorizontalPagerShimmy() {
    Column(

    ) {
        Text(
            text = "Loading Recent Articles...",
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp),
            fontFamily = customFontInter,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenTitleTextColor,
            fontSize = MaterialTheme.typography.titleMedium.fontSize

        )
        Column(
            modifier = Modifier
                .size(width = 380.dp, height = 230.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(width = 360.dp, height = 210.dp)
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = true,
                            gradiantVariantFirst = harmonyHavenComponentWhite,
                            gradiantVariantSecond = harmonyHavenComponentWhite

                        )
                    )

            )

            Row(

                horizontalArrangement = Arrangement.Center
            ) {

                repeat(4) { iteration ->
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = true,
                                    gradiantVariantFirst = harmonyHavenComponentWhite,
                                    gradiantVariantSecond = harmonyHavenComponentWhite

                                )
                            )
                            .size(16.dp)
                    )
                }

            }


        }
    }


}