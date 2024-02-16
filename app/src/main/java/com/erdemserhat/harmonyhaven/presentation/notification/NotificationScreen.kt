package com.erdemserhat.harmonyhaven.presentation.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite
import com.erdemserhat.harmonyhaven.util.customFontInter

@Composable
fun NotificationScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        harmonyHavenGradientGreen,
                        harmonyHavenGradientWhite
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())

        ) {
            repeat(10){
                NotificationContent()
            }

        }


    }


}
@Preview
@Composable
fun NotificationScreenPreview() {
    val navController = rememberNavController()
    NotificationScreen(navController = navController)
}

@Composable
fun NotificationContent() {

    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            text = "Tuesday, February 6, 202417:12",
            fontFamily = customFontInter,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(bottom = 5.dp)
        )
        Box(
            modifier = Modifier
                .width(380.dp)
                .wrapContentHeight()
                .background(color = harmonyHavenComponentWhite, shape = RoundedCornerShape(20.dp)),

            ) {
            Column {
                Text(
                    text = "Stay True to Your Plan",
                    fontFamily = customFontInter,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 5.dp, bottom = 10.dp)

                )
                Text(
                    text = "Hey Jenny, jusust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyust a friendlyt a friendly reminder that everything you're striving for is just around the corner. Stay true to your plan, keep pushing forward, and soon you'll see your efforts pay off. You've got this!",
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                        .width(310.dp),
                    textAlign = TextAlign.Justify,
                    fontFamily = customFontInter,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize

                )

            }

            Image(
                painter = painterResource(id = R.drawable.shareicon),
                contentDescription = null,
                Modifier
                    .size(50.dp)
                    .padding(10.dp)
                    .align(Alignment.BottomEnd),

                )
            Image(
                painter = painterResource(id = R.drawable.harmony_haven_icon),
                contentDescription = null,
                Modifier
                    .size(50.dp)
                    .padding(10.dp)
                    .align(Alignment.TopEnd),

                )

        }
    }
    
}