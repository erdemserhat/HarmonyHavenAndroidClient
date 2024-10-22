package com.erdemserhat.harmonyhaven.presentation.test.google_auth

import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.dto.requests.GoogleAuthenticationRequest
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val userUseCase: UserUseCases,
    private val jwtRepository: JwtTokenRepository,
):ViewModel() {

    fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {

            // Passkey credential
            is PublicKeyCredential -> {
                // Share responseJson such as a GetCredentialResponse on your server to
                // validate and authenticate
            }

            // Password credential
            is PasswordCredential -> {
                // Send ID and password to your server to validate and authenticate.

            }

            // GoogleIdToken credential
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract the ID to validate and
                        // authenticate on your server.

                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        viewModelScope.launch {
                            val response = userUseCase.authenticateUserViaGoogle.executeRequest(
                                GoogleAuthenticationRequest(
                                    googleIdToken = googleIdTokenCredential.idToken
                                )
                            )




                        }


                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("Google Sign-In", "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("Google Sign-In", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("google-sign-in", "Unexpected type of credential")
            }
        }
    }

}