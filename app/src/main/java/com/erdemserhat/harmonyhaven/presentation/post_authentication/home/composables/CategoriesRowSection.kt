package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards.CategoryCard
import com.erdemserhat.harmonyhaven.ui.theme.DefaultAppFont
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun CategoryRow(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    selectedCategoryId: Int,
    isReady: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.93f),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kategoriler",
                fontFamily = DefaultAppFont,
                color = Color.Black.copy(alpha = 0.85f),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
            
            // Optional: Add a "See All" button or other action
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Categories List
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isReady) {
                items(categories) { category ->
                    CategoryCard(
                        category = category,
                        isSelected = selectedCategoryId == category.id,
                        onItemClick = {
                            onCategoryClick(category)
                        }
                    )
                }
            } else {
                items(5) {
                    ShimmerCategoryCard()
                }
            }
        }
    }
}

@Composable
fun ShimmerCategoryCard(onItemClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .size(width = 90.dp, height = 45.dp)
            .clip(RoundedCornerShape(12.dp))
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
            .clickable { onItemClick() }
            .placeholder(
                visible = true,
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(12.dp),
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.White.copy(alpha = 0.8f)
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF5F5F5),
                            Color(0xFFF8F8F8)
                        )
                    )
                )
        )
    }
}