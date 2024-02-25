package com.erdemserhat.harmonyhaven.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingLogo
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingText
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingTitle
import com.erdemserhat.harmonyhaven.presentation.appcomponents.ScreenWithBackground
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenEmailTextField
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenForgotPasswordTextButton
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenGoogleSignInButton
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenLoginButton
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenPasswordTextField
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenRememberCredentialsCheckbox
import com.erdemserhat.harmonyhaven.presentation.login.components.LoginScreenWarningText
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun LoginScreenContent(navController: NavController, viewModel: LoginViewModel) {
    //Content of Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.SpaceBetween,


        ) {
        var email by rememberSaveable {
            mutableStateOf("")
        }

        var password by rememberSaveable {
            mutableStateOf("")
        }

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HarmonyHavenGreetingLogo(
                modifier = Modifier
                    .padding(top = 30.dp)
            )
            HarmonyHavenGreetingTitle(
                modifier = Modifier
                    .padding(top = 5.dp)
            )

            HarmonyHavenGreetingText(
                modifier = Modifier
                    .padding(20.dp)
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

                LoginScreenEmailTextField(
                    email = email,
                    onValueChanged = {
                        email = it
                    }

                )
                LoginScreenPasswordTextField(
                    onValueChanged = {
                        password = it

                    },
                    password = password
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                LoginScreenRememberCredentialsCheckbox(loginViewModel = viewModel)
                Spacer(modifier = Modifier.size(60.dp))
                LoginScreenForgotPasswordTextButton(navController)
            }
            Spacer(modifier = Modifier.size(20.dp))
            LoginScreenWarningText(loginViewModel = viewModel)

            Spacer(modifier = Modifier.size(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginScreenLoginButton(
                    modifier = Modifier,
                    onClick = {
                        viewModel.onLoginClicked(email, password)
                        if(viewModel.state.value.canNavigateToDashBoard){
                            navController.navigate(Screen.Home.route)
                        }

                    },
                    canNavigateToDashboard = false,
                    navController = navController,


                    )
                Spacer(modifier = Modifier.size(10.dp))
                LoginScreenGoogleSignInButton(Modifier, viewModel, navController)


                Spacer(modifier = Modifier.size(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.don_t_have_account))
                    TextButton(
                        onClick = { navController.navigate(Screen.Register.route) }
                    ) {
                        Text(stringResource(R.string.sign_up), color = harmonyHavenGreen)
                    }
                }

            }
        }


    }

}

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

) {
    ScreenWithBackground(
        content = { LoginScreenContent(navController = navController, viewModel = viewModel) },
        backgroundImageId = R.drawable.login_register_background
    )

}

@Preview
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}

