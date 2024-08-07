package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.util.DefaultAppFont
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordUpdatePopup(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onSaveButtonClicked: (newPassword: String, confirmNewPassword: String, currentPassword: String) -> Unit,
    isCurrentPasswordCorrect:Boolean = true,
    isNewPasswordAppropriate:Boolean= true,
    isNewPasswordsMatch:Boolean = true,

) {
    var showCurrentPasswordError by remember { mutableStateOf(false) }
    var showNewPasswordError by remember { mutableStateOf(false) }

    LaunchedEffect(isNewPasswordsMatch) {
        if (!isNewPasswordsMatch) {
            showNewPasswordError = true
            delay(1000)
            showNewPasswordError = false
        }
    }

    LaunchedEffect(isNewPasswordAppropriate) {
        if (!isNewPasswordAppropriate) {
            showNewPasswordError = true
            delay(1000)
            showNewPasswordError = false
        }
    }

    LaunchedEffect(isCurrentPasswordCorrect) {
        if (!isCurrentPasswordCorrect) {
            showCurrentPasswordError = true
            delay(1000)
            showCurrentPasswordError = false
        }
    }





    val currentPassword = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val newPassword = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val confirmNewPassword = remember {
        mutableStateOf(TextFieldValue(""))
    }


    val isNewPasswordVisible = remember { mutableStateOf(false) }

    val isCurrentPasswordVisible = remember { mutableStateOf(false) }

    val (currentPasswordTfRequester, newPasswordTfRequester, newPasswordConfirmTfRequester) = remember { FocusRequester.createRefs() }


    // FocusRequester ve KeyboardController'ı tanımlayın
    val keyboardController = LocalSoftwareKeyboardController.current

    // LaunchedEffect içindeki işlemler
    LaunchedEffect(Unit) {
        currentPasswordTfRequester.requestFocus()
        keyboardController?.show()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
            .padding(16.dp)
            .focusable(true)

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Current Password",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.SemiBold,
            )

            TextField(
                isError = showCurrentPasswordError,
                value = currentPassword.value,
                onValueChange = { text ->
                    // Metin değiştiğinde, metnin tamamını seçili hale getir
                    currentPassword.value = text
                },

                visualTransformation = if (isCurrentPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        newPasswordTfRequester.requestFocus()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    errorIndicatorColor = Color.Red,
                    errorCursorColor = Color.Red,

                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(currentPasswordTfRequester)
                    .onFocusChanged {
                        keyboardController?.show()  // Klavyeyi açma
                    },

                maxLines = 1,
                trailingIcon = {
                    val image = if (isCurrentPasswordVisible.value) {
                        painterResource(id = R.drawable.visibility_eye_icon)
                    } else {
                        painterResource(id = R.drawable.visibility_off_eye_icon)
                    }

                    // Localized description for accessibility services
                    val description =
                        if (isCurrentPasswordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = {
                        isCurrentPasswordVisible.value = !isCurrentPasswordVisible.value
                    }) {
                        Image(painter = image, contentDescription = description)
                    }
                }
            )



            Spacer(modifier = Modifier.size(10.dp))



            Text(
                text = "New Password",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.SemiBold
            )

            TextField(
                isError = showNewPasswordError,
                value = newPassword.value,
                maxLines = 1,

                onValueChange = { text ->
                    // Metin değiştiğinde, metnin tamamını seçili hale getir
                    newPassword.value = text
                },
                visualTransformation = if (isNewPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        newPasswordConfirmTfRequester.requestFocus() // Optionally hide the keyboard
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    errorIndicatorColor = Color.Red,
                    errorCursorColor = Color.Red,

                    ),

                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(newPasswordTfRequester),
                trailingIcon = {
                    val image = if (isNewPasswordVisible.value) {
                        painterResource(id = R.drawable.visibility_eye_icon)
                    } else {
                        painterResource(id = R.drawable.visibility_off_eye_icon)
                    }

                    // Localized description for accessibility services
                    val description =
                        if (isNewPasswordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = {
                        isNewPasswordVisible.value = !isNewPasswordVisible.value
                    }) {
                        Image(painter = image, contentDescription = description)
                    }
                }
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Confirm New Password",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.SemiBold
            )
            TextField(
                isError = showNewPasswordError,
                value = confirmNewPassword.value,
                maxLines = 1,

                visualTransformation = if (isNewPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),

                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    errorIndicatorColor = Color.Red,
                    errorCursorColor = Color.Red,

                    ),
                onValueChange = { text ->
                    // Metin değiştiğinde, metnin tamamını seçili hale getir
                    confirmNewPassword.value = text
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(newPasswordConfirmTfRequester)
            )




            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                Button(
                    onClick = {
                        onSaveButtonClicked(
                            newPassword.value.text,
                            confirmNewPassword.value.text,
                            currentPassword.value.text
                        )
                    }
                ) {
                    Text(text = "Save")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    onDismissRequest()
                }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}