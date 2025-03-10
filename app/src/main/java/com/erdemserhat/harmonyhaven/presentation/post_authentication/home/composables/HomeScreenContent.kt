package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import android.os.Bundle
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards.ArticleCard
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards.ArticleSearchBarCard
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContentNew(
    isCategoryReady:Boolean,
    isArticlesReady:Boolean,
    navController: NavController,
    articles: List<ArticlePresentableUIModel>,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    allArticles: List<ArticlePresentableUIModel>,
    onRefreshed:(()->Unit)->Unit
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
    var selectedCategoryId = rememberSaveable { mutableIntStateOf(1) }

    val keyboardController = LocalSoftwareKeyboardController.current

    var isRefreshing by rememberSaveable {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()



    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            // Trigger refresh logic
            coroutineScope.launch {
                onRefreshed{
                    isRefreshing = false

                }
            }
        },
        modifier = Modifier.fillMaxSize() // Ensure it takes up the entire screen
    ){
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
                    onQueryChange = { query = it },
                    shouldShowExitButton = query.isNotEmpty() || isFocusedSearchBar,
                    onExitButtonClicked = {
                        query = ""
                        isFocusedSearchBar = false
                        keyboardController?.hide()
                    }
                )
                Spacer(modifier = Modifier.size(20.dp))


            }

            item {
                isKeyboardVisible = WindowInsets.isImeVisible
            }


            if (isFocusedSearchBar) {
                val filteredArticles = allArticles.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.content.contains(query, ignoreCase = true) ||
                            it.contentPreview.contains(query, ignoreCase = true)
                }
                if(filteredArticles.isNotEmpty()){
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

                            if(filteredArticles.isNotEmpty()){
                                Column {

                                    ArticleSearchBarCard(filteredArticle,navController)
                                    Spacer(modifier = Modifier.size(10.dp))
                                }

                            }

                        }
                    }
                }else{
                    item {
                        Text(text = "Bir sonuç bulunamadı.")
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

                        },
                        isReady = isCategoryReady,

                        )
                }

                if(isArticlesReady){
                    items(articles) { article ->
                        ArticleCard(
                            article,
                            onReadButtonClicked = {
                                //normal parcelable data
                                val bundle = Bundle()
                                bundle.putParcelable("article", article)
                                Log.d("articleCase",article.id.toString())
                                navController.navigate(
                                    route = Screen.Article.route,
                                    args = bundle
                                )

                            }

                        )


                    }
                }else{
                    items(2){
                        ShimmerArticleCard()
                    }

                }



            }

        }
    }





}


@Composable
fun ShimmerArticleCard(onItemClick: () -> Unit={}) {
    Card(
        modifier = Modifier
            .padding(bottom = 30.dp)
            .fillMaxWidth(0.9f)
            .height(350.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable { onItemClick() }
            .placeholder(
                visible = true,
                color = Color.LightGray.copy(alpha = 0.4f)
                ,
                shape = RoundedCornerShape(12.dp),
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.LightGray.copy(alpha = 0.9f)
                )
            ),
        elevation = CardDefaults.cardElevation(4.dp)  // Use CardDefaults to specify elevation
    ) {
        // Content of the card can be added here if needed
    }
}


