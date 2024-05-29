package com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components


import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
fun LoginScreenPasswordTextField(
    label: String = stringResource(id = R.string.password),
    onValueChanged:(String)->Unit,
    password:String,
    isPasswordHidden:Boolean,
    onClickIcon:()->Unit,
    isError:Boolean
) {


    val visibilityIcon = if (isPasswordHidden) {
        painterResource(id = R.drawable.visibility_eye_icon)
    } else {
        painterResource(id = R.drawable.visibility_off_eye_icon)
    }

    OutlinedTextField(
        modifier = Modifier
            .size(width = 370.dp, height = 55.dp),
        value = password,
        onValueChange = {
            onValueChanged(it)

        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Black,
            focusedLabelColor = Color.Black
        ),
        singleLine = true,
        label = { Text(text = label) },
        visualTransformation =
        if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = {onClickIcon()}) {
                val description =
                    if (isPasswordHidden) stringResource(id = R.string.show_password) else stringResource(
                        id = R.string.hide_password
                    )
                Icon(painter = visibilityIcon, contentDescription = description)
            }
        },
        isError=isError
    )
}