package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun TestIntroScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()

            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 24.dp)
        ) {
            // Enneagram logosu
            Image(
                painter = painterResource(id = R.drawable.enneagram),
                contentDescription = "Enneagram Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Enneagram Yolculuğunuza Başlayın",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = harmonyHavenDarkGreenColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Enneagram kişilik tipinizi keşfetmek için seviyenize uygun bir test modu seçin.",
                fontSize = 16.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                style = TextStyle(lineHeight = 24.sp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Basit Test Modu - Aktif
            TestModeCard(
                title = "Basit Test",
                description = "• Hızlı sonuç için 36 soru\n• 5-10 dakika sürer\n• Temel tip analizi",
                onClick = {
                    // Basit test için navigate
                    navController.navigate(Screen.EnneagramTestScreen.route + "?mode=simple")
                },
                isEnabled = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Standart Test Modu - Pasif
            TestModeCard(
                title = "Standart Test",
                description = "• Detaylı analiz için 60 soru\n• 10-15 dakika sürer\n• Kanat tipi analizi içerir",
                onClick = { /* Pasif, tıklanınca hiçbir şey yapmayacak */ },
                isEnabled = false,
                comingSoonText = "Yakında"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Profesyonel Test Modu - Pasif
            TestModeCard(
                title = "Profesyonel Test",
                description = "• Derinlemesine analiz için 108 soru\n• 20-30 dakika sürer\n• Stres ve gelişim hatları dahil",
                onClick = { /* Pasif, tıklanınca hiçbir şey yapmayacak */ },
                isEnabled = false,
                comingSoonText = "Yakında"
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

    }
}

@Composable
fun TestModeCard(
    title: String,
    description: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    comingSoonText: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isEnabled, onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) Color.White else Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, if (isEnabled) harmonyHavenGreen.copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.3f))
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isEnabled) harmonyHavenDarkGreenColor else Color.Gray
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = if (isEnabled) Color.DarkGray else Color.Gray,
                        style = TextStyle(lineHeight = 20.sp)
                    )
                }

                if (isEnabled) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Başla",
                        tint = harmonyHavenGreen,
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(180f)
                    )
                }
            }

            // Yakında etiketi
            comingSoonText?.let {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            color = harmonyHavenGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = harmonyHavenDarkGreenColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
