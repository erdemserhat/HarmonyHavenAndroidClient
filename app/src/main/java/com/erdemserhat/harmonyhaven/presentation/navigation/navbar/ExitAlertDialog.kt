package com.erdemserhat.harmonyhaven.presentation.navigation.navbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ExitAlertDialog(
    onExit:()->Unit,
    onDismissRequest:()->Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        AlertDialog(modifier = Modifier.align(Alignment.Center),
            onDismissRequest =onDismissRequest,
            title = {
                androidx.compose.material.Text(text = "Çıkmak Üzeresiniz")
            },
            text = {
                androidx.compose.material.Text(text = "Uygulamadan çıkmak istediğinizden emin misiniz?")
            },
            confirmButton = {
                TextButton(onClick = {
                    onExit()
                }) {
                    androidx.compose.material.Text(text = "Evet", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    androidx.compose.material.Text(text = "Vazgeç", color = Color.White)
                }
            })

    }

}