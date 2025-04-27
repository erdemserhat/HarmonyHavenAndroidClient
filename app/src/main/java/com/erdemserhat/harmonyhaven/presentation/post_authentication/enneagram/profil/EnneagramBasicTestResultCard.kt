package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil

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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramFamousPeople
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramScore
import com.erdemserhat.harmonyhaven.markdowntext.MarkdownText
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun EnneagramResultCard(dominantType: EnneagramScore, wingType: EnneagramScore, description: String) {
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
            Image(
                painter = painterResource(id = R.drawable.ex),
                contentDescription = "Enneagram Tipi",
                modifier = Modifier
                    .size(350.dp)
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
                fontSize = 16.sp,
                color = Color.Black,
            )

            MarkdownText(
                maxLines = Int.MAX_VALUE,
                syntaxHighlightColor = Color.Black.copy(0.18f),
                style = customStyle,
                markdown = description,
                isTextSelectable = true,
            )

        }
    }
}

@Composable
fun FamousPersonCard(person: EnneagramFamousPeople) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = person.imageUrl)
                        .build()
                ),
                contentDescription = person.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = person.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = harmonyHavenDarkGreenColor
                )

                Text(
                    text = person.desc,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun TypeScoreItem(score: EnneagramScore) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Tip ${score.type}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = harmonyHavenDarkGreenColor,
            modifier = Modifier.padding(end = 16.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.LightGray)
        ) {
            // Calculate percentage (assuming maximum score is 12 for enneagram)
            val percentage = (score.score.toFloat() / 12).coerceIn(0f, 1f)

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage)
                    .clip(RoundedCornerShape(6.dp))
                    .background(harmonyHavenGreen)
            )
        }

        Text(
            text = "${score.score}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}