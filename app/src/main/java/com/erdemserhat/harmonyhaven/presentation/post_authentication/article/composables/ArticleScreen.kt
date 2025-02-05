package com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables

import android.annotation.SuppressLint
import android.util.Log
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.article.ArticleViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.article.toArticleResponseType


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleScreen(
    article: ArticlePresentableUIModel,
    navController: NavController,
) {
    val articleVM: ArticleViewModel = hiltViewModel()

    if (!article.ready) {
        val articleState = articleVM.articleScreenState.collectAsState()
        LaunchedEffect(key1 = article) {
            articleVM.prepareArticle(article.id)

        }
        ArticleScreenContent(articleState.value.toArticleResponseType(), navController,articleVM)


    } else {
        ArticleScreenContent(article, navController,articleVM)


    }
}










