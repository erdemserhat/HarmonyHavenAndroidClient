package com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun LoginScreenForgotPasswordTextButton(
    buttonText: String = stringResource(R.string.forgot_password),
    onClick: () -> Unit = {}


) {

    androidx.compose.material3.TextButton(
        onClick = { onClick() }) {
        Text(text = buttonText, color = harmonyHavenGreen)
    }

}