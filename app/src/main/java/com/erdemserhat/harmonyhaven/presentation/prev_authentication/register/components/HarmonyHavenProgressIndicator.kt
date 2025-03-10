package com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun HarmonyHavenProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .width(32.dp),
        color = harmonyHavenGreen,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )

}
