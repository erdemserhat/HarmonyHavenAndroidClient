package com.erdemserhat.harmonyhaven.presentation.article

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.erdemserhat.harmonyhaven.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite

val exArticle = Article("Embracing Life's Nuances","","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.")

@Composable
fun ArticleScreen() {

}

@Preview(showBackground = true)
@Composable
private fun ArticleScreenContentPreview() {
    ArticleScreenContent()

}


@Composable
fun ArticleScreenContent() {
    ArticleScreen(exArticle)


}



// Makale modeli
data class Article(val title: String, val imageUrl: String? = null, val content: String)

// Ekran tasarımı
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleScreen(article: Article) {
    Scaffold(
        topBar = {
            // Toolbar bileşeni
            ArticleToolbar(article.title)
        },
        content = {
            // Makale içeriğini gösteren bileşen
            ArticleContent(article)
        }
    )
}

// Geri butonu ve makale başlığı içeren Toolbar bileşeni
// Geri butonu ve makale başlığı içeren Toolbar bileşeni
@Composable
fun ArticleToolbar(title: String) {
    TopAppBar(
        title = { Text(title) },
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

// Makale içeriğini gösteren bileşen
@Composable
fun ArticleContent(article: Article) {
    Column(
        modifier = Modifier.padding(16.dp)
            .fillMaxSize()

    ) {
        // Makale fotoğrafı varsa
        article.imageUrl?.let { imageUrl ->
            Image(
                painter = painterResource(id = R.drawable.article_image), // yer tutucu görüntü
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Makale metni
        Text(
            text = article.content,
            fontSize = 16.sp,
            textAlign = TextAlign.Justify
        )
    }
}

