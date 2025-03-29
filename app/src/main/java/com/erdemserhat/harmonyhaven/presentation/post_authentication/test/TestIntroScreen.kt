package com.erdemserhat.harmonyhaven.presentation.post_authentication.test

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EnneagramIntroScreen(onStartClick: () -> Unit) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8F9FA),
                        Color(0xFFE9ECEF)
                    )
                )
            )
    ) {

        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Profile image
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(250)) +
                       slideInVertically(animationSpec = tween(250)) { -it }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.send_icon),
                    contentDescription = "Assistant",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(harmonyHavenGreen.copy(alpha = 0.1f))
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Title
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(700)) + 
                       slideInVertically(animationSpec = tween(700)) { -it }
            ) {
                Text(
                    text = "Harmonia ile Sohbete Başla",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = harmonyHavenGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Description
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(900)) + 
                       slideInVertically(animationSpec = tween(900)) { -it }
            ) {
                Text(
                    text = "Harmonia, hayatınızın her alanında size yardımcı olmak için burada. " +
                           "Sorularınızı yanıtlamak, tavsiyeler vermek ve keyifli sohbetler etmek için hazır.",
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Topic cards
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(1100)) + 
                       slideInVertically(animationSpec = tween(1100)) { -it }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Konuşabileceğiniz Konular:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = harmonyHavenGreen,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    TopicCard(
                        title = "Kişisel Gelişim",
                        description = "Enneagram, kişilik tipleri, motivasyon ve alışkanlıklar hakkında konuşabilirsiniz."
                    )
                    
                    TopicCard(
                        title = "Mindfulness ve Meditasyon",
                        description = "Stres yönetimi, meditasyon teknikleri ve bilinçli farkındalık hakkında bilgi alabilirsiniz."
                    )
                    
                    TopicCard(
                        title = "İlişkiler ve Sosyal Yaşam",
                        description = "İlişki dinamikleri, iletişim becerileri ve sosyal etkileşimler hakkında yardım alabilirsiniz."
                    )
                    
                    TopicCard(
                        title = "Günlük Tavsiyeler",
                        description = "Günlük yaşam, rutin, hobiler ve genel konularda sohbet edebilirsiniz."
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Start button
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(1300)) + 
                       slideInVertically(animationSpec = tween(1300)) { -it }
            ) {
                Button(
                    onClick = onStartClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = harmonyHavenGreen
                    )
                ) {
                    Text(
                        text = "Sohbete Başla",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Start",
                        tint = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun TopicCard(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = harmonyHavenGreen
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
