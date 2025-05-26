package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramFamousPeople
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramScore
import com.erdemserhat.harmonyhaven.domain.model.rest.Article
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.markdowntext.MarkdownText
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun EnneagramResultCard(
    dominantType: EnneagramScore,
    wingType: EnneagramScore,
    description: String,
    personalityImageUrl:String,
    navController:NavController,
    article: ArticlePresentableUIModel?


) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "Senin Enneagram Tipin:",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tip ${dominantType.type}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = harmonyHavenDarkGreenColor
                )

                if (wingType.type > 0) {
                    Text(
                        text = " (Kanat ${wingType.type})",
                        fontSize = 20.sp,
                        color = harmonyHavenDarkGreenColor
                    )
                }
            }

            // Header görseli
            AsyncImage(
                model = personalityImageUrl,
                contentDescription = "Enneagram Tipi",
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(16.dp))


            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = harmonyHavenGreen.copy(alpha = 0.3f)
            )

            val customStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
                lineHeight = 25.sp
            )

            MarkdownText(
                maxLines = Int.MAX_VALUE,
                syntaxHighlightColor = Color.Black.copy(0.18f),
                style = customStyle,
                markdown = description,
                isTextSelectable = true,
            )
            article.let {article->
                Spacer(modifier = Modifier.size(25.dp))
                Button(
                    onClick = {
                            val bundle = Bundle()
                            bundle.putParcelable("article",article)
                            navController.navigate(
                                route = Screen.Article.route,
                                args = bundle
                            )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = harmonyHavenGreen
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Detaylı Açıklamaya Git",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

            }


        }
    }
}
