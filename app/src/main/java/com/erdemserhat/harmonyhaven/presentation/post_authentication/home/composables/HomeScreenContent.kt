package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards.ArticleCard
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards.ArticleSearchBarCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreenContentNew(
    navController: NavController,
    articles: List<ArticlePresentableUIModel>,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    allArticles: List<ArticlePresentableUIModel>
) {

    var isFocusedSearchBar by remember {
        mutableStateOf(false)
    }

    var query by remember {
        mutableStateOf("")
    }

    var isKeyboardVisible by remember {
        mutableStateOf(false)
    }
    var selectedCategoryId = rememberSaveable { mutableIntStateOf(0) }





    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        item {
            Spacer(modifier = Modifier.size(15.dp))
                HomeScreenSearchBar(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    onActiveChange = { isFocusedSearchBar = it },
                    query = query,
                    onQueryChange = { query = it }
                )
                Spacer(modifier = Modifier.size(20.dp))


        }

        item {
            isKeyboardVisible = WindowInsets.isImeVisible
        }


        if (isFocusedSearchBar && isKeyboardVisible) {
            val filteredArticles = allArticles.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.content.contains(query, ignoreCase = true) ||
                        it.contentPreview.contains(query, ignoreCase = true)
            }
            items(filteredArticles) { filteredArticle ->
                var isVisible by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    isVisible = true
                }
                AnimatedVisibility(
                    visible = isVisible,
                    enter = expandVertically(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ),
                    exit = shrinkVertically(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeOut(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    )
                ) {
                    Column {
                        ArticleSearchBarCard(filteredArticle,navController)
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }
            }


        } else {
            item {
                CategoryRow(
                    categories = categories,
                    selectedCategoryId = selectedCategoryId.intValue,
                    onCategoryClick = { category ->
                        onCategorySelected(category)
                        selectedCategoryId.intValue = category.id

                    }

                )
            }

            items(articles) { article ->
                ArticleCard(
                    article,
                    onReadButtonClicked = {
                        //normal parcelable data
                        val bundle = Bundle()
                        bundle.putParcelable("article", article)
                        navController.navigate(
                            route = Screen.Article.route,
                            args = bundle
                        )

                    }

                )


            }


        }

    }


}





