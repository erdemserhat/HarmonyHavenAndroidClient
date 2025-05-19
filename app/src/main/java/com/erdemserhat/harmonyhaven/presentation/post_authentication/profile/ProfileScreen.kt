package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // Profile Header with Image
            ProfileHeader(userName = "Serhat")
            
            // User stats card
            UserStatsCard()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Settings section
            SectionTitle(title = "Ayarlar")
            
            SettingsItem(
                icon = Icons.Default.AccountCircle,
                title = "Hesap Detayları",
                onClick = { /* Navigate to account details */ }
            )
            
            SettingsItem(
                icon = Icons.Default.Notifications,
                title = "Bildirim ve Hatırlatıcılar",
                onClick = { 
                    navController.navigate(Screen.NotificationScheduler.route)
                }
            )
            
            SettingsItem(
                icon = Icons.Default.Favorite,
                title = "Favoriler",
                onClick = { /* Navigate to favorites */ }
            )
            
            SettingsItem(
                icon = Icons.Default.Star,
                title = "Dili Değiştir",
                onClick = { /* Open language selection */ }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Ratings section
            SectionTitle(title = "Bizi Değerlendir")
            
            SettingsItem(
                icon = Icons.Default.Star,
                title = "Arkadaşlarını Davet Et",
                onClick = { /* Open invite dialog */ }
            )
            
            SettingsItem(
                icon = Icons.Default.Star,
                title = "Bizi Takip Et",
                onClick = { /* Open social media links */ }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Feedback section
            SectionTitle(title = "Geri Bildirim")
            
            SettingsItem(
                icon = Icons.Default.Star,
                title = "Puanla",
                onClick = { /* Open rating dialog */ }
            )
            
            SettingsItem(
                icon = Icons.Default.Star,
                title = "Yardım",
                onClick = { /* Navigate to help */ }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Version info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Versiyon 3.6.4 995",
                    fontFamily = ptSansFont,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Harmony Haven 2023 - All rights reserved",
                    fontFamily = ptSansFont,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(80.dp)) // Space for bottom navigation
        }
    }
}

@Composable
fun ProfileHeader(userName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        // Background Image - using AsyncImage for remote URL loading
        AsyncImage(
            model = "https://images.pexels.com/photos/2469122/pexels-photo-2469122.jpeg", // Starry sky image
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
        
        // Overlay gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.5f)
                        )
                    )
                )
        )
        
        // Greeting Text
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Merhaba,",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = ptSansFont
            )
            
            Text(
                text = userName,
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ptSansFont
            )
        }
    }
}

@Composable
fun UserStatsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        // User stats with equal width columns
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatColumn(
                value = "247",
                label = "Aktif Gün",
                modifier = Modifier.weight(1f)
            )
            
            // Vertical divider
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
                    .background(Color.LightGray)
            )
            
            StatColumn(
                value = "2",
                label = "Favori Mesajların",
                modifier = Modifier.weight(1f)
            )
            
            // Vertical divider
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
                    .background(Color.LightGray)
            )
            
            StatColumn(
                value = "0",
                label = "Özel Mesajların",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatColumn(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontFamily = ptSansFont,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Text(
            text = label,
            fontFamily = ptSansFont,
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp),
        fontFamily = ptSansFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circular icon background
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = title,
            fontFamily = ptSansFont,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
    
    // Divider
    Divider(
        modifier = Modifier.padding(start = 72.dp, end = 16.dp),
        color = Color.LightGray.copy(alpha = 0.5f)
    )
}