package com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun ArticleContent(
    article: ArticlePresentableUIModel,
    fontSize: Int
) {
    Column(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
            .padding(end = 16.dp, start = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        var isLoading by remember { mutableStateOf(true) }

        AsyncImage(
            model = article.imagePath,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .clip(RoundedCornerShape(8.dp))
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.Gray.copy(0.3f)
                    ),
                    color = harmonyHavenComponentWhite
                ),

            contentScale = ContentScale.Fit,
            onSuccess = { isLoading = false },
            onError = { isLoading = false }
        )

        Spacer(modifier = Modifier.height(16.dp))


        MarkdownArticleContent(article.content, fontSize)
    }
}


@Composable
fun MarkdownArticleContent(
    articleContent: String,
    fontSize: Int = 16
) {
    MarkdownText(
        color = if(isSystemInDarkTheme()) Color.White else Color.Black,
        fontSize = fontSize.sp,
        markdown = articleContent,
        modifier = Modifier.fillMaxSize()


    )
}
