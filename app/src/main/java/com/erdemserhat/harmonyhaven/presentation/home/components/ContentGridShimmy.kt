package com.erdemserhat.harmonyhaven.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContentGridShimmy() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        repeat(6) {
            if (it % 2 == 0) {
                Row {
                    ContentShimmy()
                    Spacer(modifier = Modifier.width(8.dp))
                    if (it + 1 < 6) {
                        ContentShimmy()
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

