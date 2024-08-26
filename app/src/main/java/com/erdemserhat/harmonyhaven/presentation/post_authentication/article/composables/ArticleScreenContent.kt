package com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.article.ArticleScreenConstants

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleScreenContent(
    article: ArticlePresentableUIModel,
    navController: NavController
) {
    var fontSize by rememberSaveable {
        mutableIntStateOf(ArticleScreenConstants.DEFAULT_FONT_SIZE)
    }



    Scaffold(
        topBar = {
            ArticleScreenTopBar(
                onTextFontMinusClicked = { if (fontSize >= ArticleScreenConstants.MIN_FONT_SIZE) fontSize-- },
                onTextFontPlusClicked = { if (fontSize <= ArticleScreenConstants.MAX_FONT_SIZE) fontSize++ },
                navController = navController
            )
        },
        content = {
            ArticleContent(article, fontSize)
        }
    )


}
