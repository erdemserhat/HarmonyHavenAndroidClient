package com.erdemserhat.harmonyhaven.presentation.login.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun LoginScreenForgotPasswordTextButton(
    navController: NavController,
    buttonText: String = stringResource(R.string.forgot_password),

) {

    androidx.compose.material3.TextButton(
        onClick = { navController.navigate(Screen.PasswordReset.route) }) {
        Text(text = buttonText, color = harmonyHavenGreen)
    }

}