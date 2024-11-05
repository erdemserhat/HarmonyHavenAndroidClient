package com.erdemserhat.harmonyhaven.presentation.prev_authentication.register

import android.app.Activity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.credentials.GetCredentialResponse
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.RegisterFormModel
import com.erdemserhat.harmonyhaven.presentation.common.appcomponents.HarmonyHavenGreetingTitle
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.LoginViewModel
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components.GoogleSignInButton
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.AcceptanceOfTermsOfUse
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenButton
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenPasswordTextField
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenProgressIndicator
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenTextField
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.welcome.Gender
import com.erdemserhat.harmonyhaven.ui.theme.customFontFamilyJunge
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun RegisterScreenContent(
    params: RegisterScreenParams
) {

    if (params.shouldNavigateTo) {
        params.onShouldNavigateTo()
    }

    var isButtonsEnabled by rememberSaveable {
        mutableStateOf(false)
    }

    var onGoogleWidgetLoading by rememberSaveable {
        mutableStateOf(false)
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
        ,
        contentAlignment = Alignment.Center
    ) {

        if (onGoogleWidgetLoading){
            Box(modifier = Modifier
                .zIndex(2f)
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center){
                CircularProgressIndicator(
                    modifier = Modifier.width(32.dp),
                    color = harmonyHavenGreen,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )


            }
        }




        Image(
            painter = painterResource(R.drawable.login_register_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        //screen content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),


            //horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.SpaceBetween,


            ) {
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
                        .padding(top = 5.dp)
                    , text = "Hadi bir hesap oluşturalım!"
                )
                HarmonyHavenGreetingTitle(
                    modifier = Modifier
                        .padding(top = 5.dp)
                    , text = "Kayıt Ol"
                )


                Text(
                    text = stringResource(R.string.login_register_greeting_text),
                    modifier = Modifier
                        .padding(10.dp),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center


                )


            }

            Column(
                modifier = Modifier
                    .padding(bottom = 40.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    HarmonyHavenTextField(
                        text = params.name,
                        onValueChanged = params.onNameValueChanged,
                        placeHolderText = stringResource(R.string.name),
                        isError = params.isNameValid
                    )

                   // HarmonyHavenTextField(
                     //   text = params.surname,
                     //   onValueChanged = params.onSurnameValueChanged,
                      //  placeHolderText = stringResource(R.string.surname),
                     //   isError = params.isSurnameValid
                   // )

                    HarmonyHavenTextField(
                        text = params.email,
                        onValueChanged = params.onEmailValueChanged,
                        placeHolderText = stringResource(R.string.e_mail),
                        isError = params.isEmailValid,

                    )

                    HarmonyHavenPasswordTextField(
                        placeHolderText = stringResource(R.string.password),
                        password = params.password,
                        onValueChanged = params.onPasswordValueChanged,
                        isError = params.isPasswordValid,
                        modifier = Modifier.imePadding()
                    )
                    /*

                    HarmonyHavenPasswordTextField(
                        placeHolderText = stringResource(R.string.confirm_password),
                        password = params.passwordConfirm,
                        onValueChanged = params.onPasswordPasswordConfirmValueChanged,
                        isError = params.isPasswordValid,
                        modifier = Modifier.imePadding()
                    )

                     */
                    Spacer(modifier = Modifier.size(10.dp))


                    isButtonsEnabled = params.isTermsOfUserAccepted

                    Text(
                        text = params.warningText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()


                    )




                   // Spacer(modifier = Modifier.size(20.dp))
                   // GenderSection(
                    //    gender = params.gender,
                    //    onGenderSelected = params.onGenderValueChanged
                   // )




                    Spacer(modifier = Modifier.size(20.dp))

                    if (params.isLoading) {
                        HarmonyHavenProgressIndicator()

                    } else {


                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            HarmonyHavenButton(
                                buttonText = stringResource(id = R.string.sign_up),
                                onClick = params.onSignUpClicked,
                                modifier = Modifier,
                                isEnabled = isButtonsEnabled

                            )
                            Spacer(modifier = Modifier.size(25.dp))

                            GoogleSignInButton(
                                buttonText =
                                "Google ile Giriş Yap",
                                signInHandler ={
                                    onGoogleWidgetLoading = false
                                    params.signInGoogleHandler(it)
                                },
                                onLoadingReady = {
                                    onGoogleWidgetLoading = !it
                                }
                            )




                            Spacer(modifier = Modifier.size(10.dp))

                          //  HarmonyHavenButtonWithIcon(
                              //  onClick = params.onSignUpViaGoogleClicked,
                              //  painterId = R.drawable.google_sign_in_icon,
                              //  buttonText = stringResource(id = R.string.sign_in_via_google),
                              //  isEnabled = isButtonsEnabled


                            Spacer(modifier = Modifier
                                .size(10.dp)
                                .imePadding())
                            /*

                            AcceptanceOfTermsOfUse(
                                stringResource(R.string.terms_use),
                                onCheckedStateChanged = params.onTermsOfConditionsAcceptanceStatusChanged,
                                checkedState = params.isTermsOfUserAccepted

                            )

                             */


                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 30.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = stringResource(R.string.already_have_an_account))
                            TextButton(
                                onClick = params.onSignInClicked
                            ) {
                                Text(stringResource(R.string.sign_in), color = harmonyHavenGreen)
                            }
                        }
                    }
                }
            }

        }


    }

}



@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController

) {
    val context = LocalContext.current
    val activity = context as? Activity
    val window = activity?.window
    SideEffect {

        window?.let {

            WindowCompat.setDecorFitsSystemWindows(it, false)

            it.statusBarColor = Color.Transparent.toArgb()
            it.navigationBarColor = Color.Transparent.toArgb()


            val insetsController = WindowCompat.getInsetsController(it, it.decorView)
            insetsController.isAppearanceLightStatusBars = true
            insetsController.isAppearanceLightNavigationBars = true

        }

    }


    var name by rememberSaveable {
        mutableStateOf("")
    }

    var surname by rememberSaveable {
        mutableStateOf("")
    }

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    var passwordConfirm by rememberSaveable {
        mutableStateOf("")
    }

    var gender by rememberSaveable {
        mutableStateOf(Gender.None)
    }

    var isTermsOfConditionsAccepted by rememberSaveable {
        mutableStateOf(false)
    }

    val registerScreenParams = RegisterScreenParams(
        name = name,
        onNameValueChanged = { name = it },
        surname = surname,
        onSurnameValueChanged = { surname = it },
        email = email,

        onEmailValueChanged = { email = it },
        password = password,
        onPasswordValueChanged = { password = it },
        passwordConfirm = passwordConfirm,
        onPasswordPasswordConfirmValueChanged = { passwordConfirm = it },
        gender = gender,
        onGenderValueChanged = { gender = it },
        onSignUpClicked = {
                registerViewModel.onRegisterClicked(
                    RegisterFormModel(
                        name = name,
                        surname = surname,
                        email = email,
                        password = password,
                        confirmPassword = passwordConfirm,
                        gender = gender
                    )
                )


        },
        onSignUpViaGoogleClicked = {},
        isTermsOfUserAccepted = true,
        onTermsOfConditionsAcceptanceStatusChanged = { isTermsOfConditionsAccepted = !isTermsOfConditionsAccepted },
        onSignInClicked = { navController.navigate(Screen.Login.route) },
        warningText = registerViewModel.registerState.value.registerWarning,
        isLoading = registerViewModel.registerState.value.isLoading,
        shouldNavigateTo = registerViewModel.registerState.value.canNavigateTo,
        onShouldNavigateTo = {
            navController.navigate(Screen.QuoteMain.route)

                             },
        isNameValid = !registerViewModel.registerState.value.registerValidationState.isNameValid,
        isSurnameValid = !registerViewModel.registerState.value.registerValidationState.isSurnameValid,
        isEmailValid = !registerViewModel.registerState.value.registerValidationState.isEmailValid,
        isPasswordValid = !registerViewModel.registerState.value.registerValidationState.isPasswordValid,
        signInGoogleHandler = { registerViewModel.handleSignInWithGoogle(it) }







        )


    RegisterScreenContent(params = registerScreenParams)


}

@Preview
@Composable
fun RegisterScreenPreviewDev() {
    RegisterScreenContent(params = defaultRegisterScreenParams)

}

data class RegisterScreenParams(
    val name: String,
    val onNameValueChanged: (String) -> Unit,
    val surname: String,
    val onSurnameValueChanged: (String) -> Unit,
    val email: String,
    val onEmailValueChanged: (String) -> Unit,
    val password: String,
    val onPasswordValueChanged: (String) -> Unit,
    val passwordConfirm: String,
    val onPasswordPasswordConfirmValueChanged: (String) -> Unit,
    val gender: Gender,
    val onGenderValueChanged: (Gender) -> Unit,
    val onSignUpClicked: () -> Unit,
    val onSignUpViaGoogleClicked: () -> Unit,
    val isTermsOfUserAccepted: Boolean = true,
    val onTermsOfConditionsAcceptanceStatusChanged: (Boolean) -> Unit  ,
    val onSignInClicked: () -> Unit,
    val warningText: String,
    val isLoading: Boolean,
    val shouldNavigateTo: Boolean,
    val onShouldNavigateTo: () -> Unit,
    val isNameValid:Boolean,
    val isSurnameValid:Boolean,
    val isEmailValid:Boolean,
    val isPasswordValid:Boolean,
    val signInGoogleHandler: (GetCredentialResponse) -> Unit
)

private val defaultRegisterScreenParams = RegisterScreenParams(
    name = "",
    onNameValueChanged = {},
    surname = "",
    onSurnameValueChanged = {},
    email = "",
    onEmailValueChanged = {},
    password = "",
    onPasswordValueChanged = {},
    passwordConfirm = "",
    onPasswordPasswordConfirmValueChanged = {},
    gender = Gender.Male,
    onGenderValueChanged = {},
    onSignUpClicked = {},
    onSignUpViaGoogleClicked = {},
    isTermsOfUserAccepted = true,
    onTermsOfConditionsAcceptanceStatusChanged = {},
    onSignInClicked = {},
    warningText = "Loading....",
    isLoading = false,
    shouldNavigateTo = true,
    onShouldNavigateTo = {},
    isNameValid = false,
    isSurnameValid = false,
    isEmailValid = false,
    isPasswordValid = false,
    signInGoogleHandler = {}
)




