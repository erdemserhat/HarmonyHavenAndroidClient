package com.erdemserhat.harmonyhaven.presentation.register.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.R

@Composable
fun HarmonyHavenPasswordTextField(
    placeHolderText: String,
    password:String,
    onValueChanged:(String)->Unit



) {

    var passwordHidden by rememberSaveable {
        mutableStateOf(true)
    }

    val visibilityIcon = if (passwordHidden) {
        painterResource(id = R.drawable.visibility_eye_icon)
    } else {
        painterResource(id = R.drawable.visibility_off_eye_icon)
    }

    OutlinedTextField(
        modifier = Modifier
            .size(width = 370.dp, height = 60.dp),
        value = password,
        onValueChange =onValueChanged,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Black,
            focusedLabelColor = Color.Black
        ),
        singleLine = true,
        label = { Text(text = placeHolderText) },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val description =
                    if (passwordHidden) stringResource(id = R.string.show_password) else stringResource(
                        id = R.string.hide_password
                    )
                Icon(painter = visibilityIcon, contentDescription = description)
            }
        }
    )
}