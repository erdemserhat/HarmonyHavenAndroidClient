package com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

@Composable
fun LoginScreenLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonText: String = stringResource(id = R.string.sign_in),


    ) {


    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 20.dp),
        modifier = modifier
            .size(width = 220.dp, 40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = harmonyHavenGreen
        )


    ) {
        Text(text = buttonText)

    }

}


@Preview
@Composable
private fun dsad() {
    GoogleSignInButton()

}

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonText: String = stringResource(id = R.string.sign_in),
    signInHandler: suspend (result: GetCredentialResponse) -> Unit = {},
    onLoadingReady: (Boolean) -> Unit = {}


) {
    var isLoadingGoogleWidgetReady by rememberSaveable {
        mutableStateOf(true)
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        if (!isLoadingGoogleWidgetReady) {
            onLoadingReady(false)
        } else {
            onLoadingReady(true)
        }


        Button(
            onClick = {
                doGoogleSignIn(
                    coroutineScope,
                    context,
                    signInHandler,
                    onLoadingReady = { isLoadingGoogleWidgetReady = it })
            },
            shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 20.dp),
            modifier = modifier
                .size(width = 220.dp, 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = harmonyHavenGreen
            )

        )

        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google_icon), // Your Google icon resource
                    contentDescription = "Google Sign-In",
                    modifier = Modifier.size(20.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = buttonText,
                        modifier = Modifier.align(Alignment.CenterVertically),


                        )

                }


            }


        }

    }


}


private fun doGoogleSignIn(
    coroutineScope: CoroutineScope,
    context: Context,
    signInHandler: suspend (result: GetCredentialResponse) -> Unit,
    onLoadingReady: (Boolean) -> Unit
) {
    coroutineScope.launch {
        onLoadingReady(false)

        //Not that your project's private key's sha1 code (with jks extension) must be placed in google api console
        //otherwise you will get an error in production environment because your development sha1 and production sha1
        //might be different. Learning both of them and adding to api console can be a good approach.
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            //you can learn this value from your google api console.
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(true)
            .build()


        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            //we are adding to googleIdOption reference to build a credential request
            .addCredentialOption(googleIdOption)
            .build()


        val credentialManager = CredentialManager.create(context)

        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )
            // Handle the successfully returned credential. here

            onLoadingReady(true)
            signInHandler(result)
        } catch (e: Exception) {
            onLoadingReady(true)
            Log.e("Google Sign-In Error", "Google Sign-In Failed: ${e.localizedMessage}", e)
            //handleFailure(e) // Optionally handle the error
        }
    }
}

