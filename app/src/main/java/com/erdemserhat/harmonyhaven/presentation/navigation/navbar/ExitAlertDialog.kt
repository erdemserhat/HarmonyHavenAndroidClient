package com.erdemserhat.harmonyhaven.presentation.navigation.navbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

/**
 * A dialog that appears when the user tries to exit the application.
 * Uses HarmonyHaven's color scheme with white background and black text.
 *
 * @param onExit Callback for when the user confirms exit
 * @param onDismissRequest Callback for when the user dismisses the dialog
 */
@Composable
fun ExitAlertDialog(
    onExit: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AlertDialog(
            modifier = Modifier.align(Alignment.Center),
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = "Çıkmak Üzeresiniz",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Uygulamadan çıkmak istediğinizden emin misiniz?",
                    color = Color.Black
                )
            },
            confirmButton = {
                Button(
                    onClick = onExit,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "Evet",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = harmonyHavenGreen
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Vazgeç",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            containerColor = Color.White
        )
    }
}