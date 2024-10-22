package com.erdemserhat.harmonyhaven.presentation.test.google_auth

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TestScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TestViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignInWithGoogleButton(
            signInHandler = {
                viewModel.handleSignIn(it)
            }
        )
    }
}

@Composable
fun SignInWithGoogleButton(
    signInHandler:(result: GetCredentialResponse)->Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    OutlinedButton(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        onClick = { doGoogleSignIn(coroutineScope, context,signInHandler) },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Icon(imageVector = Icons.Filled.MailOutline, contentDescription = "Gmail")
        Text(
            text = "Sign in with Google",
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 14.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun doGoogleSignIn(
    coroutineScope: CoroutineScope,
    context: Context,
    signInHandler: (result: GetCredentialResponse) -> Unit
) {
    coroutineScope.launch {

        //Not that your project's private key's sha1 code (with jks extension) must be placed in google api console
        //otherwise you will get an error in production environment because your development sha1 and production sha1
        //might be different. Learning both of them and adding to api console can be a good approach.
        val googleIdOption: GetGoogleIdOption =GetGoogleIdOption.Builder()
            //you can learn this value from your google api console.
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
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

            signInHandler(result)  // Uncomment this to handle the result
        } catch (e: Exception) {

            Log.e("Google Sign-In Error", "Google Sign-In Failed: ${e.localizedMessage}", e)
            //handleFailure(e) // Optionally handle the error
        }
    }
}





fun getAddGoogleAccountIntent(): Intent {
    return Intent(Settings.ACTION_ADD_ACCOUNT).apply {
        putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
    }
}
