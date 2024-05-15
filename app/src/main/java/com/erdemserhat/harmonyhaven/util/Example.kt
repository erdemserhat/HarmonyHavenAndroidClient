package com.erdemserhat.harmonyhaven.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreenContent()
}


@Composable
fun LoginScreenContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Login Giriş Ekranı",


        )
    }

}