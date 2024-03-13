package com.erdemserhat.harmonyhaven.presentation.register

import androidx.compose.foundation.Image
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.Gender
import com.erdemserhat.harmonyhaven.domain.model.RegisterFormModel
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenWarningText
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.register.components.AcceptanceOfTermsOfUse
import com.erdemserhat.harmonyhaven.presentation.register.components.GenderSection
import com.erdemserhat.harmonyhaven.presentation.register.components.HarmonyHavenButton
import com.erdemserhat.harmonyhaven.presentation.register.components.HarmonyHavenButtonWithIcon
import com.erdemserhat.harmonyhaven.presentation.register.components.HarmonyHavenPasswordTextField
import com.erdemserhat.harmonyhaven.presentation.register.components.HarmonyHavenProgressIndicator
import com.erdemserhat.harmonyhaven.presentation.register.components.HarmonyHavenTextField
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.util.customFontFamilyJunge

@Composable
fun RegisterScreenContent(
    name: String,
    onNameValueChanged: (String) -> Unit,
    surname: String,
    onSurnameValueChanged: (String) -> Unit,
    email: String,
    onEmailValueChanged: (String) -> Unit,
    password: String,
    onPasswordValueChanged: (String) -> Unit,
    passwordConfirm: String,
    onPasswordPasswordConfirmValueChanged: (String) -> Unit,
    gender: Gender,
    onGenderValueChanged: (Gender) -> Unit,
    onSignUpClicked: () -> Unit,
    onSignUpViaGoogleClicked: () -> Unit,
    isTermsOfUserAccepted: Boolean,
    onTermsOfConditionsAcceptanceStatusChanged: (Boolean) -> Unit,
    onSignInClicked: () -> Unit,
    warningText: String,
    isLoading: Boolean,
    shouldNavigateTo: Boolean,
    onShouldNavigateTo: () -> Unit


) {

    if (shouldNavigateTo) {
        onShouldNavigateTo()
    }

    var isButtonsEnabled by rememberSaveable {
        mutableStateOf(false)
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
            verticalArrangement = Arrangement.SpaceBetween,


            ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.harmony_haven_icon),
                    contentDescription = null,
                    Modifier.padding(top = 30.dp)

                )
                Text(
                    text = "Harmony Haven",
                    fontSize = 40.sp,
                    fontFamily = customFontFamilyJunge

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
                        text = name,
                        onValueChanged = onNameValueChanged,
                        placeHolderText = stringResource(R.string.name)
                    )

                    HarmonyHavenTextField(
                        text = surname,
                        onValueChanged = onSurnameValueChanged,
                        placeHolderText = stringResource(R.string.surname)
                    )

                    HarmonyHavenTextField(
                        text = email,
                        onValueChanged = onEmailValueChanged,
                        placeHolderText = stringResource(R.string.e_mail)
                    )

                    HarmonyHavenPasswordTextField(
                        placeHolderText = stringResource(R.string.password),
                        password = password,
                        onValueChanged = onPasswordValueChanged
                    )

                    HarmonyHavenPasswordTextField(
                        placeHolderText = stringResource(R.string.confirm_password),
                        password = passwordConfirm,
                        onValueChanged = onPasswordPasswordConfirmValueChanged
                    )
                    Spacer(modifier = Modifier.size(10.dp))


                    if(isTermsOfUserAccepted){
                        isButtonsEnabled = true

                    }else{
                        isButtonsEnabled = false

                    }

                    Text(
                        text = warningText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()


                    )

                    


                    Spacer(modifier = Modifier.size(20.dp))
                    GenderSection(
                        gender = gender,
                        onGenderSelected = onGenderValueChanged
                    )




                    Spacer(modifier = Modifier.size(20.dp))

                    if (isLoading) {
                        HarmonyHavenProgressIndicator()

                    } else {


                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            HarmonyHavenButton(
                                buttonText = stringResource(id = R.string.sign_up),
                                onClick = onSignUpClicked,
                                modifier = Modifier,
                                isEnabled = isButtonsEnabled


                            )
                            Spacer(modifier = Modifier.size(10.dp))

                            HarmonyHavenButtonWithIcon(
                                onClick = onSignUpViaGoogleClicked,
                                painterId = R.drawable.google_sign_in_icon,
                                buttonText = stringResource(id = R.string.sign_in_via_google),
                                isEnabled = isButtonsEnabled

                            )
                            Spacer(modifier = Modifier.size(10.dp))

                            AcceptanceOfTermsOfUse(
                                stringResource(R.string.terms_use),
                                onCheckedStateChanged = onTermsOfConditionsAcceptanceStatusChanged,
                                checkedState = isTermsOfUserAccepted

                            )


                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = stringResource(R.string.already_have_an_account))
                            TextButton(
                                onClick = onSignInClicked
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
    navController: NavController

) {


    var name by rememberSaveable {
        mutableStateOf("a")
    }

    var surname by rememberSaveable {
        mutableStateOf("b")
    }

    var email by rememberSaveable {
        mutableStateOf("editkuragma@gmail.com")
    }
    var password by rememberSaveable {
        mutableStateOf("fdsfdAffsf.3451.")
    }

    var passwordConfirm by rememberSaveable {
        mutableStateOf("fdsfdAffsf.3451.")
    }

    var gender by rememberSaveable {
        mutableStateOf(Gender.None)
    }

    var isTermsOfConditionsAccepted by rememberSaveable {
        mutableStateOf(false)
    }


    RegisterScreenContent(
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
            if (isTermsOfConditionsAccepted){
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
            }


        },
        onSignUpViaGoogleClicked = {},
        isTermsOfUserAccepted = isTermsOfConditionsAccepted,
        onTermsOfConditionsAcceptanceStatusChanged = { isTermsOfConditionsAccepted = !isTermsOfConditionsAccepted },
        onSignInClicked = { navController.navigate(Screen.Login.route) },
        warningText = registerViewModel.registerState.value.registerWarning,
        isLoading = registerViewModel.registerState.value.isLoading,
        shouldNavigateTo = registerViewModel.registerState.value.canNavigateTo,
        onShouldNavigateTo = { navController.navigate(Screen.Dashboard.route) }

    )


}

@Preview
@Composable
fun RegisterScreenPreviewDev() {
    RegisterScreenContent(
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
        onShouldNavigateTo = {}

    )


}




