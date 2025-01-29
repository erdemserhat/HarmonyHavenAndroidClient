package com.erdemserhat.harmonyhaven.presentation.post_authentication.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EnneagramIntroScreen(onStartClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Enneagram Testi", color = Color.White) },
                backgroundColor = Color(0xFF6200EE)
            )
        }
    ) {padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF6F6F6)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enneagram Tipini Öğren",
                fontSize = 24.sp,
                color = Color(0xFF6200EE),
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = """
                    Enneagram Kişilik Testini Çöz
                    Seni ne motive eder? Dikkatinin
                    odaklandığı yönler neler? Kendini nasıl
                    geliştirirsin?
                    
                    Testi çözüp hemen öğren!
                """.trimIndent(),
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text(text = "Hemen Başla", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}
