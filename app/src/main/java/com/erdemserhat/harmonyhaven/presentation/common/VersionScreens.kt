package com.erdemserhat.harmonyhaven.presentation.common

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import kotlinx.coroutines.delay

@Composable
fun VersionCheckScreen() {
    var showLoadingText by remember { mutableStateOf(false) }
    var showAppName by remember { mutableStateOf(false) }
    var showTagline by remember { mutableStateOf(false) }
    
    val infiniteTransition = rememberInfiniteTransition(label = "")
    
    // Animation for the loading circle
    val rotationAnim = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    
    // Scale animation for the logo
    val scale = infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    // Fade in animations
    LaunchedEffect(Unit) {
        delay(300)
        showAppName = true
        delay(600)
        showLoadingText = true
        delay(900)
        showTagline = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF92BABF)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // App Logo with animation
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .scale(scale.value)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.harmonyhaven_icon),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
                
                // Rotating circle around the logo
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationZ = rotationAnim.value
                        }
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                        color = harmonyHavenGreen,
                        strokeWidth = 3.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            // App Name with animation
            AnimatedVisibility(
                visible = showAppName,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = "Harmony Haven",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Tagline with animation
            AnimatedVisibility(
                visible = showTagline,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {

            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Loading text with animation
            AnimatedVisibility(
                visible = showLoadingText,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Güncellemeler kontrol ediliyor...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun NetworkErrorScreen(onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF92BABF)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
            ,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nointernet),
                    contentDescription = "Bağlantı Hatası",
                    modifier = Modifier
                        .size(120.dp)
                        .scale(1.2f)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Ups! Bağlantı Kesildi",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Lütfen internet bağlantınızı kontrol edip tekrar deneyin",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = onRetry,
                    colors = ButtonColors(
                        contentColor = harmonyHavenGreen,
                        containerColor = harmonyHavenGreen,
                        disabledContentColor = harmonyHavenGreen,
                        disabledContainerColor = harmonyHavenGreen

                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Tekrar Dene",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAvailableScreen(navController:NavController) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(

            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Güncelleme Mevcut!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.refresh),
                        contentDescription = "Güncelleme Mevcut",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Harmony Haven'ın yeni bir sürümü Play Store'da mevcut. En son özellikler ve iyileştirmeler için şimdi güncelleyin!",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        contentColor = harmonyHavenGreen,
                        containerColor = harmonyHavenGreen,
                        disabledContentColor = harmonyHavenGreen,
                        disabledContainerColor = harmonyHavenGreen

                    ),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("market://details?id=${context.packageName}")
                        }
                        context.startActivity(intent)
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Şimdi Güncelle", color = Color.White)
                }
            },

        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff92BABF)),
        contentAlignment = Alignment.Center
    ) {

    }
} 