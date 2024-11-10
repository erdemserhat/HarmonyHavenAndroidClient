package com.erdemserhat.harmonyhaven.presentation.prev_authentication.login


import android.app.Activity
import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.DisableContentCapture
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.credentials.GetCredentialResponse
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.common.appcomponents.HarmonyHavenGreetingText
import com.erdemserhat.harmonyhaven.presentation.common.appcomponents.HarmonyHavenGreetingTitle
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components.GoogleSignInButton
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components.LoginScreenEmailTextField
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components.LoginScreenForgotPasswordTextButton
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components.LoginScreenLoginButton
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components.LoginScreenPasswordTextField
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components.LoginScreenWarningText
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait

@Composable
fun LoginScreenContent(
    params: LoginScreenParams,
) {


    //Navigation control
    if (params.canNavigateToDashBoard) {
        params.onCanNavigateToDashBoard()

    }

    var onGoogleWidgetLoading by rememberSaveable {
        mutableStateOf(false)
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
            .imePadding()
            .background(Color.White),
        // Automatically adds padding for the IME (keyboard)
        // .navigationBarsPadding(),
        contentAlignment = Alignment.Center

    ) {
        if (onGoogleWidgetLoading) {
            Box(
                modifier = Modifier
                    .zIndex(2f)
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(32.dp),
                    color = harmonyHavenGreen,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )


            }
        }


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
                .imePadding()
                .verticalScroll(rememberScrollState()),

            verticalArrangement = Arrangement.SpaceBetween,


            ) {


            //Greeting Elements
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.harmonyhaven_icon),
                    contentDescription = null,
                    Modifier
                        .padding(top = 30.dp)
                        .size(130.dp)

                )
                HarmonyHavenGreetingTitle(
                    modifier = Modifier
                        .padding(top = 5.dp), text = "Zaten bir hesabın varmı?"
                )
                HarmonyHavenGreetingTitle(
                    modifier = Modifier
                        .padding(top = 5.dp),
                    text = "Giriş Yap",
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
                            isError = params.isPasswordValid,
                            modifier = Modifier.imePadding()

                        )

                    }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        // LoginScreenRememberCredentialsCheckbox(
                        //   isChecked = params.isCheckedRememberCredentials,
                        //   onCheckedChange = params.onRememberCredentialsStateChanged
                        // )
                        LoginScreenForgotPasswordTextButton(
                            onClick = params.onForgotPasswordClicked,
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

                        if (params.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(32.dp),
                                color = harmonyHavenGreen,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )

                        } else {
                            //Buttons
                            LoginScreenLoginButton(
                                onClick = params.onLoginButtonClicked
                            )

                            Spacer(modifier = Modifier.size(25.dp))
                            GoogleSignInButton(
                                buttonText =
                                "Google ile Giriş Yap",
                                signInHandler = {
                                    onGoogleWidgetLoading = false
                                    params.signInGoogleHandler(it)

                                },
                                onLoadingReady = {
                                    onGoogleWidgetLoading = !it
                                }
                            )

                            // Spacer(modifier = Modifier.size(10.dp))
                            //   LoginScreenGoogleSignInButton(
                            //     onClick = {params.onLoginViaGoogleClicked}
                            //  )

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
    viewModel: LoginViewModel = hiltViewModel(),

) {
    var email by rememberSaveable {
        mutableStateOf(viewModel.getEmailIfExist())
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }


    var isCheckedRememberCredentials by rememberSaveable {
        mutableStateOf(false)
    }



    //val shouldOpenAlertDialog = remember { mutableStateOf(false) }

    val loginParams = LoginScreenParams(
        onLoginButtonClicked = { viewModel.onLoginClicked(email, password) },
        onLoginViaGoogleClicked = {
            //

        },
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
        onCanNavigateToDashBoard = {
            navController.navigate(Screen.Main.route)


        },
        isLoading = viewModel.loginState.value.isLoading,
        isEmailValid = !viewModel.loginState.value.validationState.isEmailValid,
        isPasswordValid = !viewModel.loginState.value.validationState.isPasswordValid,
        signInGoogleHandler = { viewModel.handleSignInWithGoogle(it) }
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
    val onLoginViaGoogleClicked: @Composable () -> Unit,
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
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val signInGoogleHandler: (GetCredentialResponse) -> Unit
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
    isPasswordValid = true,
    signInGoogleHandler = {}


)


