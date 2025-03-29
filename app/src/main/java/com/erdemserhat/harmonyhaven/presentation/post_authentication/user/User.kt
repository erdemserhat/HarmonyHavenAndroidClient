package com.erdemserhat.harmonyhaven.presentation.post_authentication.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.erdemserhat.harmonyhaven.presentation.feature.google_auth.TestScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.test.EnneagramIntroScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.test.QuestionScreenWithProgressBar
import com.erdemserhat.harmonyhaven.presentation.post_authentication.test.ReminderScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.test.ResultScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileImageUrl: String ="https://erdemserhat.com/wp-content/uploads/2024/06/photo_2024-06-20_14-25-45.jpg",
    userName: String ="Serhat Erdem",
    bio: String="A software engineer",
    characterType: String ="type A"
) {
    Scaffold(

    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ){
            val questions = listOf(
                "Do you like programming?",
                "Do you use Jetpack Compose?",
                "Have you ever worked with Ktor?",
                "Are you interested in backend development?",
                "Do you enjoy solving complex problems?"
            )
            var shouldShowRemainder by rememberSaveable {
                mutableStateOf(false)
            }

            var showIntro by remember { mutableStateOf(true) }

            if (showIntro) {
                EnneagramIntroScreen(
                    onStartClick = {


                    }
                )
            }else if (shouldShowRemainder){
                ReminderScreen(
                    onStartTest = {
                        shouldShowRemainder = false

                    }
                )
            } else {
                // Test ekranını burada çağırabilirsiniz
                //QuestionScreenWithProgressBar(questions)
                ResultScreen(
                    mainType = 5,
                    wingType = 6,
                    description = "Kişisel alanını; kendini rahat ve güvende hissettiğin bir kale olarak görürsün...",
                    onRetakeTest = { /* Testi başlatma işlemi */ }
                )
            }

        }
    }
}
