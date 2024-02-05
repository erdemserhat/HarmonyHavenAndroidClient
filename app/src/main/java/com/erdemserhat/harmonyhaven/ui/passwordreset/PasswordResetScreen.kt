package com.erdemserhat.harmonyhaven.ui.passwordreset

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.isScrollLockOn
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.login.HarmonyHavenGreetingButton
import com.erdemserhat.harmonyhaven.ui.login.HarmonyHavenGreetingLogo
import com.erdemserhat.harmonyhaven.ui.login.InputText
import com.erdemserhat.harmonyhaven.ui.login.ScreenWithBackground
import com.erdemserhat.harmonyhaven.ui.navigation.Screens
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

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
            onClick = {navController.navigate(Screens.Login)}
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
