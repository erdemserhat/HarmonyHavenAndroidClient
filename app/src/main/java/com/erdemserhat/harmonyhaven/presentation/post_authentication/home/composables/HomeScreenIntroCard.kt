package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.domain.model.rest.Mood
import com.erdemserhat.harmonyhaven.presentation.post_authentication.mood.MoodViewModel
import com.erdemserhat.harmonyhaven.ui.theme.DefaultAppFont
import com.erdemserhat.harmonyhaven.ui.theme.customFontInter
import com.erdemserhat.harmonyhaven.ui.theme.customFontKumbhSans
import com.erdemserhat.harmonyhaven.ui.theme.georgiaFont
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont
import kotlinx.coroutines.launch

data class MoodOption(
    val id: Int,
    val name: String,
    val imageUrl: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenIntroCard(
    userName: String = "",
    quoteText: String = "Tekrar iyi hissetmekten bir düşünce uzaktayız.",
    onNotificationClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var showMoodSelector by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val halfScreenHeight = screenHeight / (1.95f)
    
    // Remember selected mood to pass back to the selector
    var selectedMoodId by remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(halfScreenHeight)
    ) {
        // Background image
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),

            /// do not change this
            model = "https://www.harmonyhavenapp.com/sources/bg.png",
            contentDescription = null
        )

        // Top row with greeting and notifications
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Left side - Greeting and mood selector
            Column {
                Text(
                    "Merhaba, $userName!",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = ptSansFont
                )
                Row(
                    modifier = Modifier.clickable {
                        showMoodSelector = true
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Modunu Seç",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp,
                        fontFamily = ptSansFont
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            // Right side - Notification icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.3f))
                    .clickable { onNotificationClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Bildirimler",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Center quote
        Text(
            text = quoteText,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            fontFamily = ptSansFont
        )
    }

    if (showMoodSelector) {
        ModalBottomSheet(
            onDismissRequest = { showMoodSelector = false },
            sheetState = sheetState
        ) {
            MoodSelectorContent(
                selectedMoodId = selectedMoodId,
                onMoodSelected = { moodId ->
                    selectedMoodId = moodId
                    scope.launch {
                        sheetState.hide()
                        showMoodSelector = false
                    }
                },
                name = userName
            )
        }
    }
}

@Composable
fun MoodSelectorContent(
    selectedMoodId: Int? = null,
    onMoodSelected: (Int) -> Unit,
    name: String,
    moodViewModel: MoodViewModel = hiltViewModel()
) {
    val uiState by moodViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Seni merak ettim, $name",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            fontFamily = ptSansFont,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        )
        
        Text(
            text = "Bugün aşağıdakilerden hangisi seni yansıtıyor?",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontFamily = ptSansFont,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = harmonyHavenGreen)
                }
            }
            uiState.error != null -> {
                Text(
                    text = "Ruh halleri yüklenirken bir hata oluştu",
                    fontSize = 16.sp,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    fontFamily = ptSansFont,
                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    items(uiState.moods) { mood ->
                        MoodItemNew(
                            mood = mood,
                            isSelected = mood.id == uiState.currentMoodId,
                            onMoodSelected = { 
                                moodViewModel.updateUserMood(mood.id)
                                onMoodSelected(mood.id.toIntOrNull() ?: 0)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodItem(
    mood: MoodOption, 
    isSelected: Boolean,
    onMoodSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .padding(2.dp),
        onClick = { onMoodSelected() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        border = if (isSelected) BorderStroke(2.dp, harmonyHavenGreen) else null,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Image covering entire card
            AsyncImage(
                model = mood.imageUrl,
                contentDescription = mood.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            
            // Dark gradient overlay for better text visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            ),
                            startY = 0f,
                            endY = 500f
                        )
                    )
            )
            
            // Mood name at the bottom
            Text(
                text = mood.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = ptSansFont,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            )
            
            // Checkmark for selected mood
            if (isSelected) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp),
                    shape = CircleShape,
                    color = harmonyHavenGreen
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodItemNew(
    mood: Mood, 
    isSelected: Boolean,
    onMoodSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .padding(2.dp),
        onClick = { onMoodSelected() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        border = if (isSelected) BorderStroke(2.dp, harmonyHavenGreen) else null,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Image covering entire card
            AsyncImage(
                model = mood.imageUrl,
                contentDescription = mood.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            
            // Dark gradient overlay for better text visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            ),
                            startY = 0f,
                            endY = 500f
                        )
                    )
            )
            
            // Mood name at the bottom
            Text(
                text = mood.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = ptSansFont,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            )
            
            // Checkmark for selected mood
            if (isSelected) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp),
                    shape = CircleShape,
                    color = harmonyHavenGreen
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}