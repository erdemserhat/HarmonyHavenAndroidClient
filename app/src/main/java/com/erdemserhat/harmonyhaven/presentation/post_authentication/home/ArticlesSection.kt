package com.erdemserhat.harmonyhaven.presentation.post_authentication.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.components.shimmerBrush
import com.erdemserhat.harmonyhaven.ui.theme.customFontInter
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenTitleTextColor

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