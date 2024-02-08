package com.erdemserhat.harmonyhaven.presentation.passwordreset

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingButton
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingLogo
import com.erdemserhat.harmonyhaven.presentation.appcomponents.InputText
import com.erdemserhat.harmonyhaven.presentation.appcomponents.ScreenWithBackground

@Composable
fun PasswordResetScreen(navController: NavController) {

    ScreenWithBackground(
        content = { PasswordResetScreenContent(navController) },
        backgroundImageId = R.drawable.login_register_background
    )

}

@Composable
fun PasswordResetScreenContent(navController: NavController) {

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ArrowBackButton(
            modifier =Modifier
                .padding(10.dp),
            onClick = {navController.navigate(Screen.Login.route)}
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            HarmonyHavenGreetingLogo(
                modifier = Modifier
            )
            Spacer(modifier = Modifier.size(35.dp))
            InputText(placeholderText = stringResource(id = R.string.e_mail))
            Spacer(modifier = Modifier.size(20.dp))
            HarmonyHavenGreetingButton(stringResource(R.string.send_e_mail))

        }


    }


}

@Preview
@Composable
fun PasswordResetScreenPreview() {
    var navController:NavController = rememberNavController()
    PasswordResetScreen(navController)

}

@Composable
fun ArrowBackButton(modifier: Modifier,onClick:()->Unit) {
    Image(
        painter = painterResource(id = R.drawable.arrow_back),
        contentDescription = null,
        modifier= modifier
            .size(32.dp)
            .clickable { onClick() }
    )

}
