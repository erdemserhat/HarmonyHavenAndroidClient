package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.static_card

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
            durationMillis = 20_000, // Animation duration
            easing = FastOutSlowInEasing
        )
    )

    AsyncImage(
        model = imageUrl,
        contentDescription = "Full screen image",
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer(
                scaleX = if(shouldAnimate)scaleAnimation else 1f,
                alpha = if (isLoading) 0.7f else 1f // Fade effect while loading
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

