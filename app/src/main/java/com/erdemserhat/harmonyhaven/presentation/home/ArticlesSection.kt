package com.erdemserhat.harmonyhaven.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
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
import com.erdemserhat.harmonyhaven.presentation.article.HtmlText
import com.erdemserhat.harmonyhaven.presentation.home.components.shimmerBrush
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenTitleTextColor
import com.erdemserhat.harmonyhaven.util.customFontInter
import kotlinx.coroutines.delay

@Composable
fun ArticleSection(
    navController: NavController,
    articles: List<ArticleResponseType>,
) {
    if (articles.isNotEmpty()) {
        Column(
            modifier = Modifier

        ) {
            Spacer(modifier = Modifier.padding(20.dp))
            Text(
                text = articles[0].category.toString(),
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                fontFamily = customFontInter,
                fontWeight = FontWeight.Bold,
                color = harmonyHavenTitleTextColor,
                fontSize = MaterialTheme.typography.titleLarge.fontSize

            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                for (article in articles) {
                    Spacer(modifier = Modifier.height(10.dp))
                    ArticlePrototype(article) {
                        val articleId = article.id
                        navController.navigate(Screen.Article.route + "/$articleId")
                    }

                }
            }

        }


    } else {
        Column(
            modifier = Modifier

        ) {
            Spacer(modifier = Modifier.padding(20.dp))
            Text(
                text = "Loading....",
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                fontFamily = customFontInter,
                fontWeight = FontWeight.Bold,
                color = harmonyHavenTitleTextColor,
                fontSize = MaterialTheme.typography.titleLarge.fontSize

            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                repeat(10) {
                    Spacer(modifier = Modifier.height(10.dp))
                    ArticlePrototypeShimmy()
                }

            }


        }


    }
}


@Composable
fun ArticlePrototype(
    article: ArticleResponseType,
    onReadButtonClicked: () -> Unit
) {
    var shouldShowShimmer by rememberSaveable {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .size(width = 350.dp, height = 200.dp)

            .padding(10.dp)
            .background(
                shimmerBrush(
                    targetValue = 1300f,
                    showShimmer = shouldShowShimmer,
                    gradiantVariantFirst = harmonyHavenComponentWhite,
                    gradiantVariantSecond = harmonyHavenComponentWhite
                ),
                shape = RoundedCornerShape(15.dp)
            )
            .border(width = 0.1.dp, color = Color.Black, shape = RoundedCornerShape(15.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, radius = 100.dp)
            ) { onReadButtonClicked() }

    ) {
            AsyncImage(
                model = article.imagePath,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterStart),
                onSuccess = { shouldShowShimmer = false },
                contentScale = ContentScale.Inside
            )

            Text(
                text = article.title,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = customFontInter,
                color = harmonyHavenDarkGreenColor,
                modifier = Modifier.padding(10.dp),
                overflow = TextOverflow.Ellipsis
            )


    }
}

@Composable
fun ArticlePrototypeShimmy() {
    val shouldShowShimmer by rememberSaveable {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .size(width = 350.dp, height = 200.dp)
            .background(
                shimmerBrush(
                    targetValue = 1300f,
                    showShimmer = shouldShowShimmer,
                    gradiantVariantFirst = harmonyHavenComponentWhite,
                    gradiantVariantSecond = harmonyHavenComponentWhite
                ),
                shape = RoundedCornerShape(15.dp)
            )
    )
}

@Preview
@Composable
private fun example() {
    ArticlePrototypeShimmy()
}