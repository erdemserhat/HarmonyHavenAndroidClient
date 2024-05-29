package com.erdemserhat.harmonyhaven.presentation.post_authentication.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.MostReadArticleModel
import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.components.shimmerBrush
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenTitleTextColor
import com.erdemserhat.harmonyhaven.util.customFontInter
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MostReadHorizontalPagerDev(
    navController: NavController,
    articles: List<ArticleResponseType>,
) {
    if (articles.size == 4) {
        val pagerState = rememberPagerState(pageCount = {
            4//list size
        })

        LaunchedEffect(Unit) {
            while (true) {
                delay(3000) // 3 saniyelik gecikme
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(1000) // Geçiş süresini 1000 milisaniyeye ayarlayın
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
                modifier = Modifier
                    .size(width = 380.dp, height = 230.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HorizontalPager(
                    modifier = Modifier
                        .size(width = 360.dp, height = 210.dp),
                    state = pagerState,
                    verticalAlignment = Alignment.Top,
                ) { page ->


                    MostReadArticleDev(articles[page]) {
                        val articleId = articles[page].id
                        navController.navigate(Screen.Article.route + "/$articleId")
                    }


                }

                Row(

                    horizontalArrangement = Arrangement.Center
                ) {

                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) harmonyHavenDarkGreenColor else harmonyHavenGradientWhite
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

    }


}


@Composable
fun MostReadArticleDev(
    article: ArticleResponseType,
    onReadButtonClicked: () -> Unit,


    ) {
    var shouldShowShimmer by rememberSaveable {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .background(
                shimmerBrush(
                    targetValue = 1300f,
                    showShimmer = shouldShowShimmer,
                    gradiantVariantFirst = harmonyHavenComponentWhite,
                    gradiantVariantSecond = harmonyHavenComponentWhite
                ),
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                text = article.title,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = customFontInter,
                color = harmonyHavenDarkGreenColor,
                modifier = Modifier
                    .padding(10.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                Modifier.size(width = 360.dp, height = 110.dp)
            ) {
                AsyncImage(
                    model = article.imagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(start = 10.dp, end = 10.dp),
                    onSuccess = { shouldShowShimmer = true },
                    contentScale = ContentScale.Fit
                )


                Text(
                    text = article.content,
                    fontFamily = customFontInter,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(end = 10.dp, bottom = 10.dp)


                )

            }
        }

        Button(
            onClick = onReadButtonClicked,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 10.dp, end = 10.dp)

                .size(width = 100.dp, height = 35.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = harmonyHavenGreen
            )

        ) {
            Text(
                text = "Read",
                textAlign = TextAlign.Center,
                fontFamily = customFontInter,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.CenterVertically)


            )

        }


    }
}






