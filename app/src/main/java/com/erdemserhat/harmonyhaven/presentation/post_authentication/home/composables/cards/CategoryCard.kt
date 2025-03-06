package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenIndicatorColor

@Composable
fun CategoryCard(
    category: Category,
    isSelected: Boolean,
    onItemClick: () -> Unit,
) {

    val targetColor =
        if (isSelected) harmonyHavenGreen.copy(alpha = 0.85f) else Color.LightGray.copy(alpha = 0.4f)


    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 400), label = "" // Adjust duration as needed
    )

    Card(
        modifier = Modifier
            .padding(end = 3.dp)
            .size(width = 90.dp, height = 45.dp)
            .clip(RoundedCornerShape(50.dp))
            .clickable { onItemClick() },

        colors = CardDefaults.cardColors(
            containerColor = animatedColor
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                fontSize = 13.sp,
                text = category.name,
                style = MaterialTheme.typography.labelLarge,
                color = if (isSelected) Color.White else Color.Black

            )
        }
    }
}
