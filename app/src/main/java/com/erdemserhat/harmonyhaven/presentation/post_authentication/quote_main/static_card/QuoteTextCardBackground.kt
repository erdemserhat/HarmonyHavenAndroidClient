package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.static_card

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun QuoteTextCardBackground(imageUrl: String, modifier: Modifier, shouldAnimate:Boolean = true) {
    var isLoading by remember { mutableStateOf(true) } // Track loading state
    var scale by remember { mutableStateOf(1f) } // For zoom-in effect
    val scaleAnimation by animateFloatAsState(
        targetValue = if (isLoading) 1.1f else 1f, // Zoom in while loading
        animationSpec = tween(
            durationMillis = 20000, // Animation duration
            easing = FastOutSlowInEasing
        )
    )

    AsyncImage(
        model = imageUrl,
        contentDescription = "Full screen image",
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer(
                alpha = if (isLoading) 0.7f else 1f
            )
            .placeholder(
                visible = isLoading,
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.White.copy(0.08f),
                    animationSpec = InfiniteRepeatableSpec(
                        animation = tween(durationMillis = 4000),
                        repeatMode = RepeatMode.Restart
                    )
                ),
                color = Color.Black
            ),




        contentScale = ContentScale.Crop,
        onSuccess = { isLoading = false }, // Stop animations on success
        onError = { isLoading = false } // Stop animations on error
    )
}

