package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

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
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.ui.theme.georgiaFont

@Composable
fun QuoteSentence(
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

            .padding(0.dp) // İçerik için padding
    )
    {
        //  Image(painter = painterResource(id = R.drawable.a1), contentDescription =null,modifier = Modifier.align(
        //    Alignment.TopStart).padding(12.dp), colorFilter = ColorFilter.tint(Color.Gray.copy(alpha = 0.4f)))


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                color = Color.White.copy(0.9f),
                text = quote,
                modifier = Modifier.padding(15.dp),
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                lineHeight = 30.sp // Satır yüksekliğini ayarlama
            )

            if(quoteWriter != ""){
                Text(
                    color = Color.White.copy(0.7f),
                    text = quoteWriter,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = georgiaFont
                )


            }
            Spacer(modifier = Modifier.size(10.dp))


            if (isFirmLogoVisible) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painter = painterResource(id = R.drawable.harmony_haven_icon),
                        contentDescription =null, modifier = Modifier.padding(5.dp).size(20.dp))

                    Text("harmonyinhaven", color = Color.White, fontFamily = FontFamily.Serif, fontSize = 15.sp)
                }
                Spacer(modifier = Modifier.size(10.dp))

            }
        }
    }

}