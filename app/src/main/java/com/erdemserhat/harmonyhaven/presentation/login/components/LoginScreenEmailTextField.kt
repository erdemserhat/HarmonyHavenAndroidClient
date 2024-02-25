package com.erdemserhat.harmonyhaven.presentation.login.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.login.LoginViewModel

@Composable
fun LoginScreenEmailTextField(
    placeholderText: String = stringResource(R.string.e_mail),
    email:String,
    onValueChanged:(String)->Unit
) {

    OutlinedTextField(
        modifier = Modifier
            .size(width = 370.dp, height = 60.dp),
        value = email,
        onValueChange = {onValueChanged(it)},
        label = { Text(text = placeholderText) },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Black,
            focusedLabelColor = Color.Black

        ),
        singleLine = true
    )
}