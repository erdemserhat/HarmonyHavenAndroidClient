package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.static_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.georgiaFont

@Composable
fun QuoteCard(
    modifier: Modifier = Modifier,
    quote: String,
    quoteWriter: String,
    isFirmLogoVisible: Boolean = false
) {
    Box(
        modifier = modifier
            .padding(15.dp)
            .wrapContentSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.8f),
                        Color.Gray.copy(alpha = 0.5f)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(15.dp) // Padding for content
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentSize()
        ) {
            // Display the quote text
            Text(
                text = quote,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                lineHeight = 30.sp,
                modifier = Modifier.padding(bottom = 10.dp) // Add spacing
            )

            // Display the quote writer if available
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
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.harmony_haven_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(20.dp)
                    )

                    Text(
                        text = "harmonyinhaven",
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.size(10.dp)) // Add spacing
            }
        }
    }
}
