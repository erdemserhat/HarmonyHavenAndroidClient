package com.erdemserhat.harmonyhaven.presentation.register.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun HarmonyHavenProgressIndicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .width(32.dp),
        color = harmonyHavenGreen,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )

}