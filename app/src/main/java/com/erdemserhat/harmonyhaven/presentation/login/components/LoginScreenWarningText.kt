package com.erdemserhat.harmonyhaven.presentation.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.erdemserhat.harmonyhaven.presentation.login.LoginViewModel

@Composable
fun LoginScreenWarningText(
    loginViewModel: LoginViewModel
) {
    Text(
        text = loginViewModel.state2.value.loginWarning,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()


    )

}