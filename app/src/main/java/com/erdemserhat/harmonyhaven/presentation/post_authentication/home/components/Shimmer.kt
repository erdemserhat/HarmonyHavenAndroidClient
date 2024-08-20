package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientGreen

@Composable
fun shimmerBrush(
    showShimmer: Boolean = true,
    targetValue:Float = 1000f,
    gradiantVariantFirst: Color,
    gradiantVariantSecond: Color

): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            harmonyHavenGradientGreen.copy(alpha = 0.6f),
            harmonyHavenComponentWhite.copy(alpha = 0.2f),
            harmonyHavenGradientGreen.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(gradiantVariantFirst, gradiantVariantSecond),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}