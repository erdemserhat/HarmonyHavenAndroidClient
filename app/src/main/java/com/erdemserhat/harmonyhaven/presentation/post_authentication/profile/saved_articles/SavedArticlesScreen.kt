package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.saved_articles

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticleResponseType
import com.erdemserhat.harmonyhaven.presentation.post_authentication.article.ArticleContent
import com.erdemserhat.harmonyhaven.presentation.post_authentication.article.ArticleToolbar
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.HomeViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.MinimizedArticleItem
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.saved_articles.MockSavedArticles.mockArticle

//To mock data///
object MockSavedArticles {
    var mockArticle: List<ArticleResponseType> = listOf()


    fun setData(artciles: List<ArticleResponseType>) {
        mockArticle = artciles
    }

    fun getData(): List<ArticleResponseType> {
        return mockArticle
    }


}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SavedArticlesScreen(navController: NavController) {
    val homeViewmodel: HomeViewModel = hiltViewModel()

    SavedArticlesScreenContent(navController, mockArticle)

}

@Composable
fun SavedArticlesScreenContent(
    navController: NavController,
    savedArticles: List<ArticleResponseType>

) {

    Scaffold(
        topBar = {
            SavedArticlesToolBar(
                navController = navController
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(savedArticles) { savedArticle ->

                    MinimizedArticleItem(savedArticle, navController)
                    Spacer(modifier = Modifier.size(10.dp))

                }


            }
        }
    )

}

@Preview
@Composable
private fun SavedArticlesContentPreview() {
    SavedArticlesScreenContent(rememberNavController(), listOf())

}


@Composable
fun SavedArticlesToolBar(
    navController: NavController

) {
    TopAppBar(
        elevation = 0.dp, // Kenarlık kalınlığını sıfıra ayarlar
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        title = {
            Text(text = "Saved Articles")
        },
        navigationIcon = {
            IconButton(onClick = { /* Geri gitme işlemi */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.return_back_icon),
                    contentDescription = "Geri",
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .size(32.dp)
                        .clickable {
                            navController.popBackStack()
                        }


                )
            }
        },

        )
}