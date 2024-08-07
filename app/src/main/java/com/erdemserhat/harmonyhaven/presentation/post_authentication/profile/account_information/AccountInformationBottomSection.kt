package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.util.DefaultAppFont

@Composable
fun AccountInformationBottomSection(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,

        ) {
        Image(
            painter = painterResource(id = R.drawable.editsection),
            contentDescription = null

        )

        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "For e-mail changes, you can write to us at ",
                fontFamily = DefaultAppFont,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF5B5353)
            )

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "support@harmonyhaven.com",
                fontFamily = DefaultAppFont,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF5FA0FF), // Metnin rengini kırmızı olarak ayarlayın

            )
        }
    }
    
}