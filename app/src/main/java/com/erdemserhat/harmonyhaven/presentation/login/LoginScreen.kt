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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.appcomponents.ButtonWithIcon
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingButton
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingLogo
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingText
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingTitle
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenTextButton
import com.erdemserhat.harmonyhaven.presentation.appcomponents.InputPasswordText
import com.erdemserhat.harmonyhaven.presentation.appcomponents.InputText
import com.erdemserhat.harmonyhaven.presentation.appcomponents.RememberCheckBox
import com.erdemserhat.harmonyhaven.presentation.appcomponents.ScreenWithBackground
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun LoginScreenContent(navController: NavController) {
    //Content of Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.SpaceBetween,


        ) {
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
                    .padding(10.dp)
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
                InputText(placeholderText = stringResource(R.string.e_mail))
                InputPasswordText(label = stringResource(id = R.string.password))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                RememberCheckBox(stringResource(R.string.remember_me))
                Spacer(modifier = Modifier.size(60.dp))
                HarmonyHavenTextButton(stringResource(R.string.forgot_password), onClick = {navController.navigate(Screen.PasswordReset.route)})
            }
            Spacer(modifier = Modifier.size(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HarmonyHavenGreetingButton(stringResource(id = R.string.sign_in),{navController.navigate(Screen.Dashboard.route)})
                Spacer(modifier = Modifier.size(10.dp))
                ButtonWithIcon(
                    R.drawable.google_sign_in_icon,
                    stringResource(R.string.sign_in_via_google)
                )


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
fun LoginScreen(navController: NavController) {
    ScreenWithBackground(
        content = { LoginScreenContent(navController = navController) },
        backgroundImageId = R.drawable.login_register_background
    )

}

@Preview
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}

