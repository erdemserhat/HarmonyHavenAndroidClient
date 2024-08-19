package com.erdemserhat.harmonyhaven.presentation.post_authentication.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun E() {
    Column(modifier = Modifier.fillMaxSize()
        .background(Color.White)) {
        Column(
            modifier = Modifier.
            fillMaxHeight(0.25f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Serhat Erdem")
        }

        
    }
    
}

@Preview
@Composable
private fun Ep() {
    E()
    
}