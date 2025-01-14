package com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.R

@Composable
fun LoginScreenEmailTextField(
    placeholderText: String = stringResource(R.string.e_mail),
    email: String,
    onValueChanged: (String) -> Unit,
    isError: Boolean = false
) {
    OutlinedTextField(
        singleLine = true,
        modifier = Modifier
            .size(width = 370.dp, height = 60.dp),
        value = email,
        onValueChange = { onValueChanged(it) },
        placeholder = {
            Text(
                text = placeholderText,
                color = Color.Gray
            )
        }, // Use placeholder instead of label
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Gray,
            focusedLabelColor = Color.Gray
        ),
        isError = isError
    )
}
