package com.erdemserhat.harmonyhaven.presentation.passwordreset.reset

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingLogo
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.passwordreset.mail.ArrowBackButtonDev
import com.erdemserhat.harmonyhaven.presentation.register.components.HarmonyHavenButton
import com.erdemserhat.harmonyhaven.presentation.register.components.HarmonyHavenProgressIndicator

@Composable
fun ForgotPasswordResetScreen() {

}

@Preview
@Composable
fun ForgotPasswordResetScreenPreview() {
    //ForgotPasswordResetScreenContent()

}

@Composable
fun ForgotPasswordResetScreenContent(
    uuid:String,
    navController: NavController,
    viewModel: ForgotPasswordResetViewModel = hiltViewModel()
) {
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }
    val (confirmPassword, setConfirmPassword) = rememberSaveable { mutableStateOf("") }
    val (isPasswordVisible, setPasswordVisible) = rememberSaveable { mutableStateOf(false) }
    if(viewModel.resetState.value.canNavigateTo)
        navController.navigate(Screen.Login.route)


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Arka plan resmi
        BackgroundImage()

        // İçerik
        ColumnContent(
            viewModel = viewModel,
            password = password,
            setPassword = setPassword,
            isPasswordVisible = isPasswordVisible,
            setPasswordVisible = setPasswordVisible,
            confirmPassword = confirmPassword,
            setConfirmPassword = setConfirmPassword,
            onResetPasswordClicked = {viewModel.resetPassword(password,uuid)},
            onArrowBackButtonClicked = {navController.navigate(Screen.Login.route)}
        )
    }
}

@Composable
private fun BackgroundImage() {
    Image(
        painter = painterResource(R.drawable.login_register_background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun ColumnContent(
    viewModel: ForgotPasswordResetViewModel,
    password: String,
    setPassword: (String) -> Unit,
    isPasswordVisible: Boolean,
    setPasswordVisible: (Boolean) -> Unit,
    confirmPassword: String,
    setConfirmPassword: (String) -> Unit,
    onResetPasswordClicked:()->Unit,
    onArrowBackButtonClicked:()->Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        // Geri Dön Butonu





        ArrowBackButtonDev(modifier = Modifier
            .align(Alignment.Start)
            .padding(10.dp)) {
            onArrowBackButtonClicked()
        }

        // Logo
        HarmonyHavenGreetingLogo(modifier = Modifier)

        // Boşluk
        Spacer(modifier = Modifier.size(35.dp))

        // Şifre alanları
        ResetPasswordTextFields(
            isPasswordVisible = isPasswordVisible,
            placeHolder1 = "New Password",
            password = password,
            onPasswordValueChanged = setPassword,
            onVisibilityIconClicked = { setPasswordVisible(!isPasswordVisible) },
            confirmPassword = confirmPassword,
            onConfirmPasswordValueChanged = setConfirmPassword,
            placeHolder2 = "Confirm Password"
        )

        Text(text = viewModel.resetState.value.resetWarning)

        // İşlem durumu
        if (viewModel.resetState.value.isLoading) {
            Spacer(modifier = Modifier.size(20.dp))
            HarmonyHavenProgressIndicator()
        } else {
            Spacer(modifier = Modifier.size(20.dp))
            HarmonyHavenButton(
                buttonText = "Reset Password",
                onClick = onResetPasswordClicked ,
                modifier = Modifier,
                isEnabled = !viewModel.resetState.value.isLoading
            )
        }
    }
}
