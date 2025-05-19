package com.erdemserhat.harmonyhaven.presentation.post_authentication.journal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun JournalEditorScreen(
    navController: NavController,
    journalId: String? = null
) {
    var title by remember { mutableStateOf(if (journalId != null) "ok" else "") }
    var content by remember { mutableStateOf(if (journalId != null) "Bugün muhteşem bir gündü..." else "") }
    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    var isMoodSelectorVisible by remember { mutableStateOf(false) }
    
    // Get current date
    val currentDate = remember {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("tr"))
        dateFormat.format(java.util.Date())
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (journalId != null) "Günlük Özeti" else "Yeni Günlük",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (title.isNotEmpty() || content.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                // Save journal entry
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Save",
                                tint = harmonyHavenGreen
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header with date and prompt
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F8FF)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = currentDate,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4A6572)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Bugün nasıl hissediyorsun?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E3C59)
                    )
                    
                    // Mood selector
                    if (selectedMood != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(selectedMood!!.color.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = selectedMood!!.emoji,
                                    fontSize = 18.sp
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = selectedMood!!.description,
                                fontSize = 14.sp,
                                color = selectedMood!!.color
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Button(
                        onClick = { isMoodSelectorVisible = !isMoodSelectorVisible },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5B7C99).copy(alpha = 0.15f),
                            contentColor = Color(0xFF5B7C99)
                        ),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Mood"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (selectedMood == null) "Duygu Ekle" else "Duygu Değiştir"
                        )
                    }
                    
                    // Mood selector grid
                    if (isMoodSelectorVisible) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            maxItemsInEachRow = 5
                        ) {
                            Mood.values().forEach { mood ->
                                MoodButton(
                                    mood = mood,
                                    isSelected = selectedMood == mood,
                                    onClick = { 
                                        selectedMood = mood
                                        isMoodSelectorVisible = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Title field with enhanced styling
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { 
                    Text(
                        "Başlık",
                        color = Color.Gray.copy(alpha = 0.7f)
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = harmonyHavenGreen,
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = harmonyHavenGreen
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Content field with enhanced styling
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { 
                    Text(
                        "Bugününü anlatmak ister misin? Neler yaşadın, neler hissettin?",
                        color = Color.Gray.copy(alpha = 0.7f)
                    ) 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = harmonyHavenGreen,
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = harmonyHavenGreen
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Media attachment option
            Button(
                onClick = { /* Handle image attachment */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEEF2F6),
                    contentColor = Color(0xFF5B7C99)
                ),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Add Image"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Görsel Ekle"
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Save button with gradient background
            Button(
                onClick = {
                    // Save journal entry
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = harmonyHavenGreen
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = title.isNotEmpty() || content.isNotEmpty()
            ) {
                Text(
                    text = "Değişiklikleri Kaydet",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun MoodButton(
    mood: Mood,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) mood.color.copy(alpha = 0.2f) else Color.Transparent,
        animationSpec = tween(durationMillis = 300),
        label = "background"
    )
    
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(48.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = mood.emoji,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}

enum class Mood(val emoji: String, val description: String, val color: Color) {
    HAPPY("😊", "Mutlu", Color(0xFF4CAF50)),
    EXCITED("🤩", "Heyecanlı", Color(0xFFFF9800)),
    CALM("😌", "Sakin", Color(0xFF2196F3)),
    TIRED("😴", "Yorgun", Color(0xFF9C27B0)),
    SAD("😔", "Üzgün", Color(0xFF607D8B)),
    STRESSED("😫", "Stresli", Color(0xFFE91E63)),
    NEUTRAL("😐", "Normal", Color(0xFF757575)),
    GRATEFUL("🙏", "Minnettar", Color(0xFF009688)),
    INSPIRED("💡", "İlham dolu", Color(0xFFFFEB3B)),
    ANXIOUS("😰", "Endişeli", Color(0xFFFF5722))
} 