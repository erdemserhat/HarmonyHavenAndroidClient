package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

data class MoodOption(
    val id: Int,
    val name: String,
    val imageUrl: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenIntroCard(
    userName: String = "Serhat",
    quoteText: String = "Tekrar iyi hissetmekten bir düşünce uzaktayız.",
    modifier: Modifier = Modifier
) {
    var showMoodSelector by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        // Background image
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            model = "https://images.pexels.com/photos/2469122/pexels-photo-2469122.jpeg",
            contentDescription = null
        )

        // Notification bell
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(28.dp),
            tint = Color.White.copy(alpha = 0.9f)
        )

        // Top greeting
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                "Merhaba, $userName!",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier.clickable {
                    showMoodSelector = true
                }
            ) {
                Text(
                    "Modunu Seç ⌄",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp
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
            textAlign = TextAlign.Center
        )
    }

    if (showMoodSelector) {
        ModalBottomSheet(
            onDismissRequest = { showMoodSelector = false },
            sheetState = sheetState
        ) {
            MoodSelectorContent(
                onMoodSelected = {
                    scope.launch {
                        sheetState.hide()
                        showMoodSelector = false
                    }
                }
            )
        }
    }
}

@Composable
fun MoodSelectorContent(onMoodSelected: () -> Unit) {
    val moods = remember {
        listOf(
            MoodOption(1, "Mutlu", "https://images.unsplash.com/photo-1533738363-b7f9aef128ce?q=80&w=300"),
            MoodOption(2, "Keyifli", "https://images.unsplash.com/photo-1546190255-451a91afc548?q=80&w=300"),
            MoodOption(3, "Sakin", "https://images.unsplash.com/photo-1587300003388-59208cc962cb?q=80&w=300"),
            MoodOption(4, "Öfkeli", "https://images.unsplash.com/photo-1518791841217-8f162f1e1131?q=80&w=300"),
            MoodOption(5, "Tükenmiş", "https://images.unsplash.com/photo-1517849845537-4d257902454a?q=80&w=300"),
            MoodOption(6, "Üzgün", "https://images.unsplash.com/photo-1611003228941-98852ba62227?q=80&w=300"),
            MoodOption(7, "Mutsuz", "https://images.unsplash.com/photo-1573865526739-10659fec78a5?q=80&w=300"),
            MoodOption(8, "Yorgun", "https://images.unsplash.com/photo-1548681528-6a5c45b66b42?q=80&w=300"),
            MoodOption(9, "Heyecanlı", "https://images.unsplash.com/photo-1583511655826-05700442b31b?q=80&w=300")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Seni merak ettim, Serhat",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        )
        
        Text(
            text = "Bugün aşağıdakilerden hangisi seni yansıtıyor?",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            items(moods) { mood ->
                MoodItem(mood = mood, onMoodSelected = onMoodSelected)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodItem(mood: MoodOption, onMoodSelected: () -> Unit) {
    var isSelected by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .padding(4.dp),
        onClick = {
            isSelected = !isSelected
            onMoodSelected()
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        border = if (isSelected) BorderStroke(2.dp, Color.Blue) else null,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = mood.imageUrl,
                contentDescription = mood.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.8f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )
            
            Text(
                text = mood.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .weight(0.2f)
            )
        }
    }
}