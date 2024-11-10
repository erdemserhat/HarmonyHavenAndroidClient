package com.erdemserhat.harmonyhaven.presentation.prev_authentication.passwordreset.mail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.common.appcomponents.HarmonyHavenGreetingLogo
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.passwordreset.auth.ForgotPasswordAuthScreen
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenButton
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenProgressIndicator
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenTextField


@Composable
fun ForgotPasswordMailScreenContent(
    email: String,
    onEmailValueChanged: (String) -> Unit,
    onArrowBackButtonClicked: () -> Unit,
    isLoading: Boolean,
    warningText: String,
    onSendMailClicked: () -> Unit,
    shouldNavigateTo:Boolean,
    onShouldNavigateTo:()->Unit,
    isError:Boolean

) {

    if (shouldNavigateTo)
    {
        onShouldNavigateTo()
    }

    //screen content
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        //Background
        Image(
            painter = painterResource(R.drawable.login_register_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        //Rest of the contents
        Column(
            Modifier
                .fillMaxSize()
                .padding(top=5.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            ArrowBackButtonDev(
                modifier = Modifier
                    .padding(5.dp),
                onClick = onArrowBackButtonClicked
            )

            if (shouldNavigateTo){


            }

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
                HarmonyHavenTextField(
                    text = email,
                    onValueChanged = onEmailValueChanged,
                    placeHolderText = stringResource(R.string.e_mail),
                    isEnabled = !isLoading,
                    isError = isError


                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = warningText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()


                )



                if (isLoading) {
                    Spacer(modifier = Modifier.size(20.dp))
                    HarmonyHavenProgressIndicator()
                } else {
                    Spacer(modifier = Modifier.size(20.dp))
                    HarmonyHavenButton(
                        buttonText = "Talep Gönder",
                        onClick = onSendMailClicked,
                        modifier = Modifier,
                        isEnabled = !isLoading

                    )
                }

            }

        }


    }


}

@Preview
@Composable
fun ForgotPasswordMailScreenPreview() {
    var navController: NavController = rememberNavController()
    ForgotPasswordMailScreenContent(
        email = "",
        onEmailValueChanged = {},
        onArrowBackButtonClicked = { /*TODO*/ },
        isLoading = true,
        warningText = "Loading...",
        onSendMailClicked = {},
        shouldNavigateTo = false,
        onShouldNavigateTo = {},
        isError = true
    )

}

@Composable
fun ArrowBackButtonDev(modifier: Modifier, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.arrow_back),
        contentDescription = null,
        modifier = modifier
            .size(32.dp)
            .clickable { onClick() }
    )

}

@Composable
fun ForgotPasswordMailScreen(
    navController: NavController,
    mailViewModel: ForgotPasswordMailViewModel = hiltViewModel(),

    ) {
    var email by rememberSaveable {
        mutableStateOf("")
    }

    if (mailViewModel.mailState.value.canNavigateTo) {

        ForgotPasswordAuthScreen(email, navController)
    } else {
        ForgotPasswordMailScreenContent(
            email = email,
            onEmailValueChanged = { email = it },
            onArrowBackButtonClicked = { navController.navigate(Screen.Login.route) },
            isLoading = mailViewModel.mailState.value.isLoading,
            warningText = mailViewModel.mailState.value.mailWarning,
            onSendMailClicked = { mailViewModel.sendMail(email) },
            shouldNavigateTo = mailViewModel.mailState.value.canNavigateTo,
            onShouldNavigateTo = { },
            isError = mailViewModel.mailState.value.isError


        )


    }


}
