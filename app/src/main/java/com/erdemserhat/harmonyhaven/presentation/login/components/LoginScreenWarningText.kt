package com.erdemserhat.harmonyhaven.presentation.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LoginScreenWarningText(
    warningText:String = "Warning Text"
) {
    Text(
        text = warningText,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()


    )

}