package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.animated_items

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.erdemserhat.harmonyhaven.R

@Composable
fun AnimatedLike(onAnimationEnd: () -> Unit) {
    // Animation states for scaling and alpha
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    // Launch animations when the composable is rendered
    LaunchedEffect(Unit) {
        scale.snapTo(0f)
        alpha.snapTo(1f)

        // Scaling up animation
        scale.animateTo(
            targetValue = 1.3f,
            animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
        )

        // Scaling down animation
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )

        // Fading out animation
        alpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 200)
        )

        // Notify when animation ends
        onAnimationEnd()
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .zIndex(2f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.likedwhitefilled),
            contentDescription = null,
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
                .size(72.dp)
        )
    }
}
