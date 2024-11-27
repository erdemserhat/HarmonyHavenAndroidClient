package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.animated_items

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import kotlinx.coroutines.launch

@Composable
fun AnimatedLikeBottomControlButton(
    isQuoteLiked: Boolean,
    onLikeClicked: (Boolean) -> Unit,
    shouldAnimate: Boolean = false,
    onAnimationEnd: () -> Unit = {}
) {
    val scale = remember { Animatable(1f) } // Başlangıç ölçeği 1 (normal boyut)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isQuoteLiked) {

        coroutineScope.launch {
            if (shouldAnimate) {
                scale.snapTo(1f)
                scale.animateTo(
                    targetValue = 1.2f,
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = FastOutSlowInEasing
                    )
                )
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = FastOutSlowInEasing
                    )
                )

                onAnimationEnd()

            }

        }
    }

    Column(
        modifier = Modifier.padding(bottom = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(28.dp)
                .scale(scale.value)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, // Etkileşim kaynağını özelleştiriyoruz
                    indication = null

                ) {


                    onLikeClicked(!isQuoteLiked)
                },
            painter = if (isQuoteLiked) painterResource(R.drawable.likedredfilled) else painterResource(
                R.drawable.likedwhiteunfilled
            ),
            contentDescription = null
        )
        Text("Beğen...", color = Color.White, fontSize = 10.sp)
    }
}
