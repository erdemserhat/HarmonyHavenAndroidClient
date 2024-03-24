package com.erdemserhat.harmonyhaven.presentation.article

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.erdemserhat.harmonyhaven.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite

data class ArticleUIModel(
    val title: String,
    val content: String,
    val publishDate: String,
    val category: String?,
    val imagePath: String,
    val isLoaded:Boolean

)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleScreen(
    articleId: Int,
    viewModel: ArticleViewModel = hiltViewModel()
) {
    viewModel.prepareArticle(articleId)

    val articleScreenState by viewModel.articleState.collectAsState()

    val articleUIModel = ArticleUIModel(
        title = articleScreenState.articleTitle,
        content = articleScreenState.articleContent,
        publishDate = articleScreenState.publishDate,
        category = articleScreenState.category,
        imagePath = articleScreenState.imagePath,
        isLoaded = articleScreenState.isLoaded
    )
    ArticleScreenContent(articleUIModel)



}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleScreenContent(
    articleUIModel: ArticleUIModel
) {


    Scaffold(
        topBar = {
            ArticleToolbar(articleUIModel.title)
        },
        content = {
            ArticleContent(articleUIModel)
        }
    )


}





@Composable
fun ArticleToolbar(title: String) {
    TopAppBar(
        title = { Text("Harmony Haven") },
        navigationIcon = {
            IconButton(onClick = { /* Geri gitme işlemi */ }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Geri")
            }
        },
        actions = {
            // Kaydetme ve Paylaşma düğmeleri
            IconButton(onClick = { /* Kaydetme işlemi */ }) {
                Icon(Icons.Filled.Star, contentDescription = "Kaydet")
            }
            IconButton(onClick = { /* Paylaşma işlemi */ }) {
                Icon(Icons.Filled.Share, contentDescription = "Paylaş")
            }
        },
        backgroundColor = harmonyHavenWhite
    )
}

@Composable
fun ArticleContent(article: ArticleUIModel) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        AsyncImage(
            model = article.imagePath, // yer tutucu görüntü
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        HtmlText(article.content)
    }
}
@Preview(showBackground = true)
@Composable
private fun ArticleScreenContentPreview() {
    ArticleScreenContent(articleUIModel = ArticleUIModel(
        "Example Title",
        category = "a",
        imagePath = "",
        content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        publishDate = "",
        isLoaded = true
    )
    )

}

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )
}


