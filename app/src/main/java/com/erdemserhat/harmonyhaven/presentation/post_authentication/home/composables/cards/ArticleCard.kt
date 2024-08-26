package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.ui.theme.DefaultAppFont
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun ArticleCard(
    article: ArticlePresentableUIModel,
    modifier: Modifier = Modifier,
    onReadButtonClicked: () -> Unit = {},

    ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(15.dp),

                ),
        contentAlignment = Alignment.Center

    ) {
        Card(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
                .fillMaxWidth(0.9f)// Köşeleri yuvarla ve 16dp'lik yarıçapa sahip olacak şekilde kırp
                .clickable { onReadButtonClicked() },
            colors = CardColors(
                containerColor = harmonyHavenComponentWhite, // Beyazdan yeşile çalan renk
                contentColor = Color.Black, // İçerik rengi
                disabledContainerColor = Color.Gray, // Devre dışı bırakılmış durumda arka plan rengi
                disabledContentColor = Color.LightGray // Devre dışı bırakılmış durumda içerik rengi
            )


        ) {
            var isLoading by remember { mutableStateOf(true) } // Yüklenme durumunu takip edin
            AsyncImage(

                model = article.imagePath,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)
                    .aspectRatio(2f) // // Genişlik / Yükseklik oranı 1.5
                    .align(Alignment.CenterHorizontally).placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.Gray.copy(0.3f)
                        ),
                        color = harmonyHavenComponentWhite
                    ),
                contentScale = ContentScale.Crop,
                onSuccess = { isLoading = false }, // Yükleme başarılı olursa shimmer efektini kaldır
                onError = { isLoading = false } // Yükleme başarısız olursa da shimmer efektini kaldır
            )
            Text(
                text = article.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = DefaultAppFont,
                color = harmonyHavenDarkGreenColor,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .align(Alignment.Start),
                overflow = TextOverflow.Ellipsis
            )



            Text(
                text = article.contentPreview,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = DefaultAppFont,
                color = harmonyHavenDarkGreenColor,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.Start),
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Read More...",
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(20.dp)
                    .background(color = Color.Transparent)

            )
        }


    }

}