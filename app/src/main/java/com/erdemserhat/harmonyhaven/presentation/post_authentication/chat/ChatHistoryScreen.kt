package com.erdemserhat.harmonyhaven.presentation.post_authentication.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryScreen(navController: NavController, viewModel: ChatViewModel = hiltViewModel()) {
    // Search text state
    var searchText by remember { mutableStateOf("") }
    
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF5F7F9)
                    )
                )
            ),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent),
                title = {
                    Text(
                        text = "Sohbet Geçmişi",
                        fontFamily = ptSansFont
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Geri",
                            tint = harmonyHavenDarkGreenColor
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background( Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF5F7F9)
                    )
                ))
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            // Search bar and New Chat button in same row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                       Color.Transparent
                    )
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Search bar
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Sohbet geçmişinde ara...") },
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search, 
                            contentDescription = "Ara",
                            tint = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true
                )
                
                // New Chat button (smaller)
                IconButton(
                    onClick = { 
                        // Reset chat state to start a new conversation
                        viewModel.resetChat()
                        // Navigate back to chat screen
                        navController.navigateUp()
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Yeni Sohbet",
                        tint = harmonyHavenGreen,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            // Section divider
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.LightGray.copy(alpha = 0.5f),
                thickness = 1.dp
            )
            
            // Chat history list with grouping by date
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White,
                                Color(0xFFF5F7F9)
                            )
                        )
                    )
            ) {
                items(20) { index ->
                    val isToday = index < 3
                    val isYesterday = index in 3..5
                    
                    if (index == 0) {
                        Text(
                            text = "Bugün",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = harmonyHavenDarkGreenColor,
                            fontFamily = ptSansFont,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else if (index == 3) {
                        Text(
                            text = "Dün",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = harmonyHavenDarkGreenColor,
                            fontFamily = ptSansFont,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else if (index == 6) {
                        Text(
                            text = "Geçen Hafta",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = harmonyHavenDarkGreenColor,
                            fontFamily = ptSansFont,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    
                    // Chat history item with more details
                    DetailedChatHistoryItem(
                        title = when {
                            index % 5 == 0 -> "Kişisel Gelişim Tavsiyeleri"
                            index % 4 == 0 -> "Günlük Motivasyon"
                            index % 3 == 0 -> "İlişki Tavsiyeleri"
                            index % 2 == 0 -> "Stres Yönetimi"
                            else -> "Kariyer Gelişimi"
                        },
                        timestamp = when {
                            isToday -> "Bugün, ${14 - index}:${30 + index}"
                            isYesterday -> "Dün, ${20 - index}:${15 + index}"
                            else -> "${index - 4} gün önce"
                        },
                        preview = when {
                            index % 5 == 0 -> "Kendini geliştirmek için yeni bir kitap okumayı deneyebilirsin..."
                            index % 4 == 0 -> "Bugün yapman gereken en önemli şey kendine vakit ayırmak..."
                            index % 3 == 0 -> "İlişkinde iletişim kurma şeklini değiştirmeyi deneyebilirsin..."
                            index % 2 == 0 -> "Stres seviyeni azaltmak için derin nefes alma egzersizleri..."
                            else -> "Kariyer hedeflerine ulaşmak için yapman gereken adımlar..."
                        },
                        messageCount = (index % 5) + 1,
                        onClick = { 
                            // Load sample conversation based on the chat title
                            val chatTitle = when {
                                index % 5 == 0 -> "Kişisel Gelişim Tavsiyeleri"
                                index % 4 == 0 -> "Günlük Motivasyon"
                                index % 3 == 0 -> "İlişki Tavsiyeleri"
                                index % 2 == 0 -> "Stres Yönetimi"
                                else -> "Kariyer Gelişimi"
                            }
                            
                            // Reset current chat and load sample messages
                            viewModel.resetChat()
                            
                            // Load different sample messages based on chat type
                            when (chatTitle) {
                                "Kişisel Gelişim Tavsiyeleri" -> {
                                    viewModel.sendMessage("Kendimi geliştirmek için neler yapabilirim?")
                                }
                                "Günlük Motivasyon" -> {
                                    viewModel.sendMessage("Bugün motivasyonum düşük, bana yardımcı olur musun?")
                                }
                                "İlişki Tavsiyeleri" -> {
                                    viewModel.sendMessage("İlişkimde iletişimi nasıl geliştirebilirim?")
                                }
                                "Stres Yönetimi" -> {
                                    viewModel.sendMessage("Stresle nasıl başa çıkabilirim?")
                                }
                                else -> {
                                    viewModel.sendMessage("Kariyerimde ilerlemek için ne yapmalıyım?")
                                }
                            }
                            
                            // Navigate back to chat screen
                            navController.navigateUp()
                        }
                    )
                    
                    if (index < 19) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = Color.LightGray.copy(alpha = 0.5f),
                            thickness = 0.5.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailedChatHistoryItem(
    title: String,
    timestamp: String,
    preview: String,
    messageCount: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
            .background(Color.White.copy(alpha = 0.5f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side dot indicator
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(harmonyHavenGreen)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Right side content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Title and time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontFamily = ptSansFont
                )
                
                Text(
                    text = timestamp,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = ptSansFont
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Preview text with message count
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = preview,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = ptSansFont,
                    modifier = Modifier.weight(1f)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Message count indicator
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(color = harmonyHavenGreen.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = messageCount.toString(),
                        fontSize = 12.sp,
                        color = harmonyHavenGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
} 