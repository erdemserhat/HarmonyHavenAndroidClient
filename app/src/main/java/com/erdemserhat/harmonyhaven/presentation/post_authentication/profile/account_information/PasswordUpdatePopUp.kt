package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
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
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.util.DefaultAppFont
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordUpdatePopup(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onSaveButtonClicked: (newPassword: String, confirmNewPassword: String, currentPassword: String) -> Unit,
    isCurrentPasswordCorrect: Boolean = true,
    isNewPasswordAppropriate: Boolean = true,
    isNewPasswordsMatch: Boolean = true,
    isLoading: Boolean = false,
    isCurrentPasswordShort:Boolean=false

) {
    Log.d("RecomposablePasswordUpdatePopUptest",isCurrentPasswordShort.toString())
    var showCurrentPasswordError by remember { mutableStateOf(false) }
    var showNewPasswordError by remember { mutableStateOf(false) }
    val context = LocalContext.current


    var tempVar2 = isNewPasswordsMatch

    LaunchedEffect(tempVar2) {
        if (!tempVar2) {
            Toast.makeText(context, "Şifreler Eşleşmiyor...", Toast.LENGTH_SHORT).show()
            showNewPasswordError = true
            delay(1000)
            showNewPasswordError = false
        }

        tempVar2 = !tempVar2
    }

    var tempVar1 = isNewPasswordAppropriate

    LaunchedEffect(tempVar1) {
        if (!isNewPasswordAppropriate) {
            Toast.makeText(
                context,
                "Yeni şifre en az 8 karakter olmalı ve en az bir büyük harf, bir küçük harf ve bir rakam içermelidir.",
                Toast.LENGTH_SHORT
            ).show()
            showNewPasswordError = true
            delay(1000)
            showNewPasswordError = false
        }
        tempVar1 = !tempVar1
    }

    var tempVar = isCurrentPasswordCorrect

    LaunchedEffect(tempVar) {
        if (!isCurrentPasswordCorrect) {
            Toast.makeText(context, "Mevcut şifreniz yanlış gibi görünüyor.", Toast.LENGTH_SHORT).show()
            showCurrentPasswordError = true
            delay(1000)
            showCurrentPasswordError = false
        }
        tempVar = !tempVar

    }

    var tempVar0 = isCurrentPasswordShort

    LaunchedEffect(tempVar0) {
        if (isCurrentPasswordShort) {
            Toast.makeText(context, "Mevcut şifreniz çok kısa, lütfen tekrar kontrol edin.", Toast.LENGTH_SHORT).show()
            showCurrentPasswordError = true
            delay(1000)
            showCurrentPasswordError = false
            tempVar0 = !tempVar0
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
                text = "Mevcut Şifreniz",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.SemiBold,
            )

            TextField(
                isError = showCurrentPasswordError,
                enabled = !isLoading,
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
                    errorIndicatorColor = Color.Red, // Color for the error indicator
                    cursorColor = Color.Black, // Color for the cursor
                    errorCursorColor = Color.Red,
                    focusedIndicatorColor = Color.Black, // Color for the focused indicator
                    unfocusedIndicatorColor = Color.Gray, // Color for the unfocused indicator
                    disabledIndicatorColor = Color.Gray, // Color for the indicator when the TextField is disabled
                    disabledPlaceholderColor = Color.Gray // Color for the placehol

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
                text = "Yeni Şifreniz",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.SemiBold
            )

            TextField(
                isError = showNewPasswordError,
                value = newPassword.value,
                enabled = !isLoading,
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
                    errorIndicatorColor = Color.Red, // Color for the error indicator
                    cursorColor = Color.Black, // Color for the cursor
                    errorCursorColor = Color.Red,
                    focusedIndicatorColor = Color.Black, // Color for the focused indicator
                    unfocusedIndicatorColor = Color.Gray, // Color for the unfocused indicator
                    disabledIndicatorColor = Color.Gray, // Color for the indicator when the TextField is disabled
                    disabledPlaceholderColor = Color.Gray // Color for the placehol

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
                text = "Yeni Şifrenizi Onaylayın",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = DefaultAppFont,
                fontWeight = FontWeight.SemiBold
            )
            TextField(
                isError = showNewPasswordError,
                enabled = !isLoading,
                value = confirmNewPassword.value,
                maxLines = 1,

                visualTransformation = if (isNewPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),

                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    placeholderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    errorIndicatorColor = Color.Red,
                    disabledIndicatorColor = Color.Black,
                    errorCursorColor = Color.Red,
                    leadingIconColor = Color.Black,
                    trailingIconColor = Color.Black,
                    disabledTextColor = Color.Black,
                    disabledLeadingIconColor = Color.Black,
                    disabledTrailingIconColor = Color.Black,
                    errorLeadingIconColor = Color.Black,
                    errorLabelColor = Color.Black,
                    disabledPlaceholderColor = Color.Black,
                    errorTrailingIconColor = Color.Black,
                    disabledLabelColor = Color.Black,




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
                    },
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        backgroundColor = Color.Black
                    )
                ) {
                    Text(text = "Kaydet")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        onDismissRequest()
                    },
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        backgroundColor = Color.Black
                    )
                ) {
                    Text(text = "Vazgeç")
                }
            }
        }
    }
}