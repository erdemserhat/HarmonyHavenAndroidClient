package com.erdemserhat.harmonyhaven.presentation.post_authentication.test
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R


@Composable
fun ReminderScreen(onStartTest: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hatırlatma", color = Color.Black) },
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { /* Geri dönme işlemi buraya */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ReminderItem(text = "Test yaklaşık 10-15 dakika sürecektir.")
                ReminderItem(text = "Kendinize en yakın olan seçeneklere Evet cevabını verin.")
                ReminderItem(text = "Tek olaya bakarak değil, genelde sergilediğiniz tutumları düşünerek cevap verin.")
            }

            Button(
                onClick = onStartTest,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE53935)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Teste Başla", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ReminderItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.house), // Buraya ikonunuzu ekleyin
            contentDescription = null,
            tint = Color(0xFFE53935),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}
