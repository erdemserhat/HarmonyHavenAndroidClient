package com.erdemserhat.harmonyhaven.presentation.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.erdemserhat.harmonyhaven.R

@Composable
fun HarmonyHavenBackground() {
    Image(
        painter = painterResource(R.drawable.login_register_background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

}