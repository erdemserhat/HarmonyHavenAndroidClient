package com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun HarmonyHavenTextField(
    text: String,
    onValueChanged: (String) -> Unit,
    placeHolderText: String,
    isEnabled:Boolean = true,
    isError:Boolean = false,
    keyBoardOptions: KeyboardOptions =  KeyboardOptions.Default,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(0.75f),
        value = text,
        onValueChange = onValueChanged,
        placeholder = { Text(text = placeHolderText) },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Gray,
            cursorColor = harmonyHavenGreen,
            focusedLabelColor = Color.Gray,
            selectionColors = TextSelectionColors(
                handleColor = harmonyHavenGreen,

                backgroundColor = harmonyHavenGreen.copy(alpha = 0.5f)
            )
        ),
        singleLine = true,
        enabled = isEnabled,
        isError = isError,
        keyboardOptions = keyBoardOptions,


    )
}