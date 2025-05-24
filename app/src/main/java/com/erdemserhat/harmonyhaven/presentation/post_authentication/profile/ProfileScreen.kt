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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Web
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
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import android.content.Context
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont
import coil.compose.AsyncImage
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.TextButton
import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import androidx.compose.foundation.combinedClickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil.UserProfileScreenViewModel

// Function to start Google Play In-App review flow
private fun startInAppReview(activity: Activity) {
    val manager = ReviewManagerFactory.create(activity)
    val request = manager.requestReviewFlow()
    request.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            // We got the ReviewInfo object
            val reviewInfo = task.result
            val flow = manager.launchReviewFlow(activity, reviewInfo)
            flow.addOnCompleteListener { _ ->
                // The flow has finished. The API does not indicate whether the user
                // reviewed or not, or even whether the review dialog was shown.
            }
        } else {
            // There was some problem, open the Play Store listing instead
            openPlayStoreForRating(activity)
        }
    }
}

// Function to open Play Store for rating (fallback)
private fun openPlayStoreForRating(context: Context) {
    val appPackageName = "com.erdemserhat.harmonyhaven"
    try {
        // Try to open in Play Store app
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
    } catch (e: Exception) {
        // If Play Store app is not available, open in browser
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, userViewModel : UserProfileScreenViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current
    val activity = context as? Activity
    val window = activity?.window!!


    LaunchedEffect(Unit) {
        window.let {
            WindowCompat.setDecorFitsSystemWindows(
                it,
                false
            ) // content fill the system navbar- status bar
            val insetsController = WindowCompat.getInsetsController(it, it.decorView)

            insetsController.isAppearanceLightStatusBars =false


        }
    }



    
    // State variables
    var showLanguageDialog by remember { mutableStateOf(false) }
    
    // Function to open Instagram
    fun openInstagram(context: Context) {
        val instagramUsername = "harmonyinhaven"
        val instagramUri = Uri.parse("https://www.instagram.com/$instagramUsername")
        
        // Try to open in Instagram app first
        val instagramIntent = Intent(Intent.ACTION_VIEW, Uri.parse("instagram://user?username=$instagramUsername"))
        
        try {
            context.startActivity(instagramIntent)
        } catch (e: Exception) {
            // If Instagram app is not installed, open in browser
            val browserIntent = Intent(Intent.ACTION_VIEW, instagramUri)
            context.startActivity(browserIntent)
        }
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Profile Header with Image
            ProfileHeader(userName = userViewModel.state.value.username?:"")
            
            // User stats card

            
            Spacer(modifier = Modifier.height(70.dp))
            
            // Settings section
            SectionTitle(title = "Ayarlar")
            
            SettingsItem(
                icon = Icons.Default.AccountCircle,
                title = "Hesap Detayları",
                onClick = { 
                    navController.navigate(Screen.Settings.route)
                }
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
                icon = Icons.Default.Language,
                title = "Dili Değiştir",
                onClick = { showLanguageDialog = true }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Ratings section
            SectionTitle(title = "Bizi Değerlendir")
            
            SettingsItem(
                icon = Icons.Default.Group,
                title = "Arkadaşlarını Davet Et",
                onClick = { 
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        val shareMessage = "Harmony Haven'a katıl ve her gün en iyi versiyonuna bir adım daha yaklaş! " +
                                "https://play.google.com/store/apps/details?id=com.erdemserhat.harmonyhaven"
                        putExtra(Intent.EXTRA_TEXT, shareMessage)
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Arkadaşlarını Davet Et"))
                }
            )
            
            SettingsItem(
                icon = Icons.Default.Share,
                title = "Bizi Takip Et",
                onClick = { openInstagram(context) }
            )
            
            SettingsItem(
                icon = Icons.Default.Web,
                title = "Websitesini Ziyaret Et",
                onClick = { 
                    val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.harmonyhavenapp.com/"))
                    context.startActivity(websiteIntent)
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Feedback section
            SectionTitle(title = "Geri Bildirim")
            
            SettingsItem(
                icon = Icons.Default.StarRate,
                title = "Puanla",
                onClick = { 
                    // Önce in-app review'u deneyelim
                    startInAppReview(context as Activity)
                    
                    // Not: Eğer in-app review gösterilmediyse, kullanıcı doğrudan Play Store'a gidebilir
                    // İpucu olarak bunu Toast ile gösterebiliriz
                    android.widget.Toast.makeText(
                        context,
                        "Uygulama içi değerlendirme şu anda kullanılamıyor olabilir. Uzun basarak Play Store'da değerlendirebilirsiniz.",
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                },
                onLongClick = {
                    // Doğrudan Play Store'a git
                    openPlayStoreForRating(context)
                }
            )
            
            SettingsItem(
                icon = Icons.Default.Help,
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
                    text = "Harmony Haven 2025 - All rights reserved",
                    fontFamily = ptSansFont,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(80.dp)) // Space for bottom navigation
        }
    }
    
    // Language selection dialog (pop-up)
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(
                    text = "Dil Seçimi / Language Selection",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = harmonyHavenGreen,
                    fontFamily = ptSansFont,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Information icon
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(harmonyHavenGreen.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Information",
                            tint = harmonyHavenGreen,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Message in Turkish
                    Text(
                        text = "Şu anda sadece Türkçe dil desteği sunulmaktadır.",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = ptSansFont,
                        textAlign = TextAlign.Center
                    )
                    
                    Text(
                        text = "İngilizce dil desteği yakında eklenecektir.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = harmonyHavenGreen,
                        fontFamily = ptSansFont,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = harmonyHavenGreen.copy(alpha = 0.2f),
                        thickness = 1.dp
                    )
                    
                    // Message in English
                    Text(
                        text = "Currently, only Turkish language is supported.",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = ptSansFont,
                        textAlign = TextAlign.Center
                    )
                    
                    Text(
                        text = "English language support will be added soon.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = harmonyHavenGreen,
                        fontFamily = ptSansFont,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showLanguageDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = harmonyHavenGreen
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Tamam / OK",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = ptSansFont
                    )
                }
            }
        )
    }
}

@Composable
fun ProfileHeader(userName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background Image - using AsyncImage for remote URL loading

        AsyncImage(
            model = "https://harmonyhavenapp.com/sources/set-bg.png", // Starry sky image
            contentDescription = null,
            modifier = Modifier.fillMaxSize().padding(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,

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

        UserStatsCard(modifier = Modifier.align(Alignment.BottomCenter).onSizeChanged {

        }.offset(y = 55.dp))
    }
}

@Composable
fun UserStatsCard(modifier: Modifier) {
    Card(
        modifier = modifier
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(
                if (onLongClick != null) {
                    Modifier.combinedClickable(
                        onClick = { onClick() },
                        onLongClick = { onLongClick() }
                    )
                } else {
                    Modifier.clickable { onClick() }
                }
            ),
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
    HorizontalDivider(
        modifier = Modifier.padding(start = 72.dp, end = 16.dp),
        color = Color.LightGray.copy(alpha = 0.5f)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingsItem(
    drawableRes: Int,
    title: String,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(
                if (onLongClick != null) {
                    Modifier.combinedClickable(
                        onClick = { onClick() },
                        onLongClick = { onLongClick() }
                    )
                } else {
                    Modifier.clickable { onClick() }
                }
            ),
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
            Image(
                painter = painterResource(id = drawableRes),
                contentDescription = null,
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
    HorizontalDivider(
        modifier = Modifier.padding(start = 72.dp, end = 16.dp),
        color = Color.LightGray.copy(alpha = 0.5f)
    )
}