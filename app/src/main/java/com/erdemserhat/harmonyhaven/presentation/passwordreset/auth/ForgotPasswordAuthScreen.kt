package com.erdemserhat.harmonyhaven.presentation.passwordreset.auth

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingLogo
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.passwordreset.mail.ArrowBackButtonDev
import com.erdemserhat.harmonyhaven.presentation.passwordreset.reset.ForgotPasswordResetScreenContent
import com.erdemserhat.harmonyhaven.presentation.register.components.HarmonyHavenButton
import com.erdemserhat.harmonyhaven.presentation.register.components.HarmonyHavenProgressIndicator
import com.erdemserhat.harmonyhaven.presentation.register.components.HarmonyHavenTextField


@Composable
fun ForgotPasswordAuthScreen(
    email:String,
    navController: NavController,
    authViewModel:ForgotPasswordAuthViewModel = hiltViewModel()
) {
    var code by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var confirmPassword by rememberSaveable {
        mutableStateOf("")
    }

    if(authViewModel.authModel.value.canNavigateTo){
        Log.d("erdem3451",authViewModel.authModel.value.uuid)
        ForgotPasswordResetScreenContent(authViewModel.authModel.value.uuid,navController)
    }else{
        ForgotPasswordAuthScreenContent(
            code = code,
            onCodeValueChanged = { code = it },
            onArrowBackButtonClicked = { navController.navigate(Screen.Login.route) },
            isLoading = authViewModel.authModel.value.isLoading,
            warningText = authViewModel.authModel.value.authWarning,
            onSendCodeClicked = { authViewModel.authRequest(email,code) },
            shouldNavigateTo = authViewModel.authModel.value.canNavigateTo,
            onShouldNavigateTo = {},
            isError = authViewModel.authModel.value.isError


        )

    }





}




@Preview
@Composable
fun ForgotPasswordAuthScreenPreview() {
    ForgotPasswordAuthScreenContent(
        code = "",
        onCodeValueChanged = {},
        onArrowBackButtonClicked = {},
        isLoading = false,
        warningText = "",
        onSendCodeClicked = {},
        shouldNavigateTo = false,
        onShouldNavigateTo = {}

        )
}

@Composable
fun ForgotPasswordAuthScreenContent(
    code: String,
    onCodeValueChanged: (String) -> Unit,
    onArrowBackButtonClicked: () -> Unit,
    isLoading: Boolean,
    warningText: String,
    onSendCodeClicked: () -> Unit,
    shouldNavigateTo: Boolean,
    onShouldNavigateTo: () -> Unit,
    isError:Boolean=false

) {
    if (shouldNavigateTo) {
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
                .verticalScroll(rememberScrollState()),
        ) {
            ArrowBackButtonDev(
                modifier = Modifier
                    .padding(10.dp),
                onClick = onArrowBackButtonClicked
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

                HarmonyHavenTextField(
                    text = code,
                    onValueChanged = onCodeValueChanged,
                    placeHolderText = "6-Digit Code",
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
                        buttonText = "Confirm",
                        onClick = onSendCodeClicked,
                        modifier = Modifier,
                        isEnabled = !isLoading

                    )
                }


            }


        }

    }


}




