package com.erdemserhat.harmonyhaven.presentation.post_authentication.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(
    mainType: Int,
    wingType: Int,
    description: String,
    onRetakeTest: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profilim", color = Color.White) },
                backgroundColor = Color(0xFFE53935),
                actions = {
                    IconButton(onClick = { /* Paylaşım işlemi */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            // Enneagram Test Sonucu Kartı
            Card(
                backgroundColor = Color(0xFFE53935),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Enneagram Test Sonucu",
                        color = Color.White,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Ana Tipin: $mainType",
                        color = Color.White,
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "Kanat Tipin: $wingType",
                        color = Color.White,
                        style = MaterialTheme.typography.body1
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Açıklama
            Text(
                text = description,
                color = Color.Black,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Testi Tekrar Çöz Butonu
            Button(
                onClick = onRetakeTest,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE53935)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Testi Tekrar Çözebilirsiniz",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Seninle Aynı Tipte Ünlüler Bölümü
            Text(
                text = "Seninle Aynı Tipte Ünlüler",
                style = MaterialTheme.typography.h6,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { // Ünlülerin görselleri için placeholder
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.Gray, shape = CircleShape)
                    )
                }
            }
        }
    }
}
