package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.static_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.georgiaFont

@Composable
fun QuoteText(
    modifier: Modifier = Modifier,
    quote: String,
    quoteWriter: String,
    isFirmLogoVisible: Boolean = false
) {
    Box(
        modifier = modifier
            .padding(15.dp)
            .zIndex(2f)
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentSize().align(Alignment.Center)
        ) {
            // Display the quote text
            Text(
                text = quote,
                color = Color.White.copy(alpha = 0.9f), // Hafif şeffaf beyaz
                fontSize = 25.sp, // Orta büyüklükte
                fontWeight = FontWeight.Light, // Hafif yazı ağırlığı
                fontStyle = FontStyle.Italic, // İtalik görünüm
                textAlign = TextAlign.Center, // Ortalanmış metin
                fontFamily = FontFamily.Serif, // Şık bir Serif yazı tipi
                lineHeight = 32.sp, // Satırlar arası boşluk
                modifier = Modifier
                    .padding(16.dp) // Kenarlardan boşluk

            )

            // Display the quote writer if available

        }
        if (quoteWriter.isNotBlank()) {
            Text(
                text = quoteWriter,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontFamily = georgiaFont
            )
            Spacer(modifier = Modifier.size(10.dp)) // Add spacing
        }

        // Display the firm logo if enabled
        if (isFirmLogoVisible) {

            Row(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Siyah-beyaz logo
                Image(
                    painter = painterResource(id = R.drawable.harmony_haven_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .graphicsLayer(alpha = 0.4f)
                        .size(25.dp),
                )

                // Yazı: Belirsiz ve şeffaf
                Text(
                    text = "harmonyinhaven",
                    color = Color.White.copy(alpha = 0.6f), // Şeffaf beyaz
                    fontFamily = FontFamily.Serif,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 8.dp) // Logo ve yazı arasında boşluk
                )
            }

            Spacer(modifier = Modifier.size(10.dp)) // Add spacing
        }
    }
}
