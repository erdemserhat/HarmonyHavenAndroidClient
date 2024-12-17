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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .wrapContentSize()
          //  .background(
               // brush = Brush.linearGradient(
               //     colors = listOf(
               //         Color.Black.copy(alpha = 0.8f),
               //         Color.Gray.copy(alpha = 0.5f)
               //     )
               // ),
               // shape = RoundedCornerShape(16.dp)
          //  )
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
                color = Color.White.copy(alpha = 0.85f),  // Reduced alpha for a softer appearance
                fontSize = 18.sp,  // Slightly smaller font size for a more delicate look
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,  // You can experiment with a different serif font for elegance
                lineHeight = 28.sp,  // Slightly increased line height for better readability
                letterSpacing = 0.5.sp,  // Add slight letter spacing to improve readability
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)  // Increased padding for better visual separation
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
                    Box(
                        contentAlignment = Alignment.Center,
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.harmonyhaven_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding()
                                .size(40.dp)  // Slightly smaller size for a delicate look
                        )

                        Text(
                            text = "harmonyinhaven",
                            color = Color.White.copy(alpha = 0.8f),  // Slightly reduced alpha for a softer look
                            fontFamily = FontFamily.Serif,
                            fontSize = 14.sp,  // Reduced font size for a lighter feel
                            fontWeight = FontWeight.Light,  // Lighter weight for the text
                            letterSpacing = 0.5.sp,  // Added letter spacing for a refined look
                            modifier = Modifier.padding(start = 35.dp).align(Alignment.CenterEnd) // Added padding between the logo and text
                        )

                    }



                }
                Spacer(modifier = Modifier.size(12.dp))  // Increased space below to separate from other elements
            }

        }
    }
}
