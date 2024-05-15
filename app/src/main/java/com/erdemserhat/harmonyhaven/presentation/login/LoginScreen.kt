package com.erdemserhat.harmonyhaven.presentation.login


import AlertDialogHarmonyHaven
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingLogo
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingText
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingTitle
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenEmailTextField
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenForgotPasswordTextButton
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenGoogleSignInButton
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenLoginButton
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenPasswordTextField
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenRememberCredentialsCheckbox
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenWarningText
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun LoginScreenContent(
    params: LoginScreenParams
) {
    //Navigation control
    if (params.canNavigateToDashBoard) {
        params.onCanNavigateToDashBoard()

    }
    //val shouldOpenAlertDialog = remember { mutableStateOf(false) }

    //Dialog Control

    //Basic states which not need to hoist

    var isPasswordHidden by rememberSaveable {
        mutableStateOf(true)
    }

    //Parent Layout of Screen
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        contentAlignment = Alignment.Center

    ) {

        //Screen Background
        Image(
            painter = painterResource(R.drawable.login_register_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )




        //Screen Content
            Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),

            verticalArrangement = Arrangement.SpaceBetween,


            ) {


            //Greeting Elements
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HarmonyHavenGreetingLogo(
                    modifier = Modifier
                        .padding(top = 35.dp)
                )
                HarmonyHavenGreetingTitle(
                    modifier = Modifier
                        .padding(top = 5.dp)
                )

                HarmonyHavenGreetingText(
                    modifier = Modifier
                        .padding(20.dp)
                )
                //////
                //Input Fields
                Column(
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {


                        LoginScreenEmailTextField(
                            email = params.email,
                            onValueChanged = params.onEmailValueChanged,
                            isError = params.isEmailValid

                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        LoginScreenPasswordTextField(
                            onValueChanged = params.onPasswordValueChanged,
                            password = params.password,
                            isPasswordHidden = isPasswordHidden,
                            onClickIcon = { isPasswordHidden = !isPasswordHidden },
                            isError = params.isPasswordValid

                        )

                    }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LoginScreenRememberCredentialsCheckbox(
                            isChecked = params.isCheckedRememberCredentials,
                            onCheckedChange = params.onRememberCredentialsStateChanged
                        )
                        Spacer(modifier = Modifier.size(60.dp))
                        LoginScreenForgotPasswordTextButton(
                            onClick = params.onForgotPasswordClicked
                        )
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    LoginScreenWarningText(
                        warningText = params.warningText

                    )

                    Spacer(modifier = Modifier.size(20.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if(params.isLoading){
                            CircularProgressIndicator(
                                modifier = Modifier.width(32.dp),
                                color = harmonyHavenGreen,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )

                        }else{
                            //Buttons
                            LoginScreenLoginButton(
                                onClick = params.onLoginButtonClicked
                            )

                            Spacer(modifier = Modifier.size(10.dp))
                            LoginScreenGoogleSignInButton(
                                onClick = params.onLoginViaGoogleClicked
                            )

                        }






                        Spacer(modifier = Modifier.size(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(R.string.don_t_have_account))
                            TextButton(
                                onClick = params.onSignUpClicked
                            ) {
                                Text(stringResource(R.string.sign_up), color = harmonyHavenGreen)
                            }
                        }


                    }

                }


            }





        }


    }


}

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()

) {
    var email by rememberSaveable {
        mutableStateOf("me.serhaterdem@gmail.com")
    }

    var password by rememberSaveable {
        mutableStateOf("Erdem.3451.")
    }


    var isCheckedRememberCredentials by rememberSaveable {
        mutableStateOf(false)
    }

    //val shouldOpenAlertDialog = remember { mutableStateOf(false) }

    val loginParams = LoginScreenParams(
        onLoginButtonClicked = { viewModel.onLoginClicked(email, password) },
        onLoginViaGoogleClicked = {},
        onSignUpClicked = { navController.navigate(Screen.Register.route) },
        onForgotPasswordClicked = { navController.navigate(Screen.ForgotPasswordMail.route) },
        onRememberCredentialsStateChanged = {
            isCheckedRememberCredentials = !isCheckedRememberCredentials
        },
        isCheckedRememberCredentials = isCheckedRememberCredentials,
        email = email,
        onEmailValueChanged = { email = it },
        password = password,
        onPasswordValueChanged = { password = it },
        warningText = viewModel.loginState.value.loginWarning,
        canNavigateToDashBoard = viewModel.loginState.value.canNavigateToDashBoard,
        onCanNavigateToDashBoard = { navController.navigate(Screen.Home.route) },
        isLoading = viewModel.loginState.value.isLoading,
        isEmailValid = !viewModel.loginState.value.validationState.isEmailValid,
        isPasswordValid = !viewModel.loginState.value.validationState.isPasswordValid
    )

    LoginScreenContent(params = loginParams)


}

@Preview
@Composable
fun LoginScreenPreview() {
LoginScreenContent(params = defaultLoginParams)

}

data class LoginScreenParams(
    val onLoginButtonClicked: () -> Unit,
    val onLoginViaGoogleClicked: () -> Unit,
    val onSignUpClicked: () -> Unit,
    val onForgotPasswordClicked: () -> Unit,
    val onRememberCredentialsStateChanged: () -> Unit,
    val isCheckedRememberCredentials: Boolean,
    val email: String,
    val onEmailValueChanged: (String) -> Unit,
    val password: String,
    val onPasswordValueChanged: (String) -> Unit,
    val warningText: String,
    val canNavigateToDashBoard: Boolean,
    val onCanNavigateToDashBoard: () -> Unit,
    val isLoading: Boolean,
    val isEmailValid:Boolean = true,
    val isPasswordValid:Boolean = true
)

val defaultLoginParams = LoginScreenParams(
    onLoginButtonClicked = {},
    onLoginViaGoogleClicked = {},
    onSignUpClicked = {},
    onForgotPasswordClicked = {},
    onRememberCredentialsStateChanged = {},
    isCheckedRememberCredentials = false,
    email = "testuser@example.com",
    onEmailValueChanged = {},
    password = "!Harmony12345",
    onPasswordValueChanged = {},
    warningText = "",
    canNavigateToDashBoard = false,
    onCanNavigateToDashBoard = {},
    isLoading = false,
    isEmailValid = true,
    isPasswordValid = true
)


