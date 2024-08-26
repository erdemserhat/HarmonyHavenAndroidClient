package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.erdemserhat.harmonyhaven.ui.theme.DefaultAppFont

@Composable
fun ArticleSearchBarCard(
    article: ArticlePresentableUIModel,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(100.dp)
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .clickable { //normal parcelable data
                val bundle = Bundle()
                bundle.putParcelable("article", article)
                navController.navigate(
                    route = Screen.Article.route,
                    args = bundle
                )

            }


    ) {
        AsyncImage(
            model = article.imagePath,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .height(120.dp)
                .weight(0.4f)
                .clip(shape = RoundedCornerShape(15.dp, 0.dp, 0.dp, 15.dp))
            ,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.weight(0.75f)
        ) {
            Text(
                text = article.title,
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp),
                maxLines = 2,
                textAlign = TextAlign.Center


            )
            Text(
                text = article.contentPreview,
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp),
                maxLines = 3,
                textAlign = TextAlign.Center


            )
        }
    }


}
