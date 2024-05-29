package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite

@Composable
fun ContentShimmy(

) {
    val showShimmer = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp)
            .background(
                shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value,
                    gradiantVariantFirst = harmonyHavenComponentWhite,
                    gradiantVariantSecond = harmonyHavenComponentWhite
                ),
                shape = RoundedCornerShape(25.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, radius = 75.dp)
            ) { }, contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


        }

    }

}

