package com.erdemserhat.harmonyhaven.presentation.register.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HarmonyHavenTextField(
    text: String,
    onValueChanged: (String) -> Unit,
    placeHolderText: String,
    isEnabled:Boolean = true,
    isError:Boolean = false
) {

    OutlinedTextField(
        modifier = Modifier
            .size(width = 370.dp, height = 60.dp),
        value = text,
        onValueChange = onValueChanged,
        label = { Text(text = placeHolderText) },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Black,
            focusedLabelColor = Color.Black

        ),
        singleLine = true,
        enabled = isEnabled,
        isError = isError
    )
}