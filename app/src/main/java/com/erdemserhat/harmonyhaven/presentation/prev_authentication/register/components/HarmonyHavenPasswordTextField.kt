package com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
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
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun HarmonyHavenPasswordTextField(
    placeHolderText: String,
    password:String,
    onValueChanged:(String)->Unit,
    shouldExistVisibilityIcon:Boolean = true,
    isError:Boolean = false,
    modifier: Modifier = Modifier

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
        modifier = modifier
            .fillMaxWidth(0.75f),
        value = password,
        onValueChange =onValueChanged,
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
        placeholder = { Text(text = placeHolderText) },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            if(shouldExistVisibilityIcon){
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val description =
                        if (passwordHidden) stringResource(id = R.string.show_password) else stringResource(
                            id = R.string.hide_password
                        )
                    Icon(painter = visibilityIcon, contentDescription = description)
                }

            }


        },
        isError = isError
    )
}