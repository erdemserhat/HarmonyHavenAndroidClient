package com.erdemserhat.harmonyhaven.presentation.prev_authentication.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import com.erdemserhat.harmonyhaven.domain.ErrorTraceFlags
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.dto.requests.GoogleAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.state.LoginState
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.state.LoginValidationState
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.state.getValidationStateByErrorCode
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val jwtRepository: JwtTokenRepository,
    @ApplicationContext private val context: Context,
    @Named("FirstInstallingExperience")
    private val firstInstallingExperiencePreferences: SharedPreferences
) : ViewModel() {

    //Encapsulation principle
    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState
    val sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)


    fun onLoginClicked(email: String, password: String) {
        _loginState.value = _loginState.value.copy(
            isLoading = true,
        )

        authenticateUser(email, password)
    }


    private fun authenticateUser(email: String, password: String) {
        val startedTime = Date().time

        try {
            viewModelScope.launch(Dispatchers.IO) {
                delay(400)
                val request = async {
                    userUseCases.authenticateUser.executeRequest(
                        UserAuthenticationRequest(
                            email = email,
                            password = password
                        )
                    )
                }

                val response = request.await()

                if (response == null) {
                    //network error

                    Log.d("AuthenticationTests", "dsd")
                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        canNavigateToDashBoard = false,
                        loginWarning = "Bağlantı Hatası :("
                    )

                    return@launch
                }

                if (!response.formValidationResult.isValid) {
                    //form not valid
                    val errorMessage = response.formValidationResult.errorMessage
                    val errorCode = response.formValidationResult.errorCode

                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        canNavigateToDashBoard = false,
                        loginWarning = when (errorCode) {
                            101 -> "Hmmmm, bu bir e-posta formatı değil gibi..."
                            102, 103 -> "Şifre en az 8 karakter uzunluğunda olmalı ve en az bir büyük harf, bir küçük harf ve bir rakam içermelidir."
                            104 -> "Bu e-posta adresi ile ilişkilendirilmiş bir hesap bulunmuyor. Kaydolmayı deneyebilirsiniz."
                            105 -> "Şifreniz yanlış gibi görünüyor..."
                            else -> "Bilinmeyen bir hata oluştu."
                        },
                        validationState = LoginValidationState().getValidationStateByErrorCode(errorCode)
                    )


                    Log.d("AuthenticationTests", "Form Invalid")
                    return@launch
                }

                if (!response.credentialsValidationResult!!.isValid) {
                    //credentials are not valid
                    val errorMessage = response.credentialsValidationResult.errorMessage
                    val errorCode = response.credentialsValidationResult.errorCode

                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        canNavigateToDashBoard = false,
                        loginWarning = when (errorCode) {
                            101 -> "Hmmmm, bu bir e-posta formatı değil gibi..."
                            102, 103 -> "Şifre en az 8 karakter uzunluğunda olmalı ve en az bir büyük harf, bir küçük harf ve bir rakam içermelidir."
                            104 -> "Bu e-posta adresi ile ilişkilendirilmiş bir hesap bulunmuyor. Kaydolmayı deneyebilirsiniz."
                            105 -> "Şifreniz yanlış gibi görünüyor..."
                            else -> "Bilinmeyen bir hata oluştu."
                        },
                        validationState = LoginValidationState().getValidationStateByErrorCode(errorCode)
                    )

                    Log.d("AuthenticationTests", "Credentials Invalid")

                    return@launch
                }

                if (!response.isAuthenticated) {
                    //user banned
                    Log.d("AuthenticationTests", "User Banned")
                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        canNavigateToDashBoard = false,
                        loginWarning = "Your registration is banned"
                    )



                    return@launch
                }
                Log.d("AuthenticationTests", "Everything is nice")


                sharedPrefs.edit().putString("email", email).apply()

                jwtRepository.saveJwtToken(response.jwt!!)
                Log.d("AuthenticationTests", response.toString())
                Log.d("AuthenticationTests", "jwt saved as :" + jwtRepository.getJwtToken())
                ///Redirect the user main page...


                _loginState.value = _loginState.value.copy(
                    isLoading = false,
                    canNavigateToDashBoard = true,
                    loginWarning = "✅"
                )
                //if login is successfully then save the fcm id

                getToken()
                val sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPrefs.edit()
                editor.putBoolean("isLoggedInBefore", true)
                editor.apply()

                firstInstallingExperiencePreferences.edit().putBoolean("isJwtExists", true).apply()


            }
        }catch (e:Exception){
            Log.d(ErrorTraceFlags.LOGIN_TRACE.flagName,"error while authenticating user ${e.message}")
            Toast.makeText(context, "Giriş yapma işlemi ile ilgili bir hata oluştu", Toast.LENGTH_SHORT).show()

        }finally {
            Log.d("mstestharmony","total lasted:${Date().time-startedTime}")

        }
    }


    fun handleSignInWithGoogle(result: GetCredentialResponse) {
        val startedTime = Date().time
        _loginState.value = _loginState.value.copy(
            isLoading = true,
        )
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

                        _loginState.value = _loginState.value.copy(
                            isLoading = true,
                        )
                        viewModelScope.launch {
                            val response = userUseCases.authenticateUserViaGoogle.executeRequest(
                                GoogleAuthenticationRequest(
                                    googleIdToken = googleIdTokenCredential.idToken
                                )
                            )


                            jwtRepository.saveJwtToken(response?.jwt!!)
                            getToken()
                            val sharedPrefs =
                                context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPrefs.edit()
                            editor.putBoolean("isLoggedInBefore", true)
                            editor.apply()
                            firstInstallingExperiencePreferences.edit().putBoolean("isJwtExists", true).apply()

                            _loginState.value = _loginState.value.copy(
                                isLoading = false,
                                canNavigateToDashBoard = true,
                                loginWarning = "✅"
                            )


                        }


                    } catch (e: GoogleIdTokenParsingException) {
                        Log.d(ErrorTraceFlags.GOOGLE_SIGN_IN_TRACE.flagName,"Received an invalid google id token response: ${e.message}")

                    } finally {
                        Log.d("mstestharmony","total lasted:${Date().time-startedTime}")

                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.d(ErrorTraceFlags.GOOGLE_SIGN_IN_TRACE.flagName,"Unexpected type of credential")

                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.d(ErrorTraceFlags.GOOGLE_SIGN_IN_TRACE.flagName,"Unexpected type of credential")
            }
        }
    }


    private fun getToken() {
        try{
            viewModelScope.launch(Dispatchers.IO) {
                FirebaseMessaging.getInstance().subscribeToTopic("everyone")
                    .addOnCompleteListener { task: Task<Void?> ->
                        Log.d("erdem", task.result.toString())
                        if (task.isSuccessful) {
                            Log.d("spec1", "Successfully subscribed to topic")
                        } else {
                            Log.e("spec1", "Failed to subscribe to topic", task.exception)
                        }
                    }
                val localToken = Firebase.messaging.token.await()
                //send your fcm id to server
                Log.d("erdem1212", localToken.toString())
                val fcmToken = localToken.toString()

                val response = userUseCases.fcmEnrolment.executeRequest(fcmToken)

                Log.d("fcmtestResults", response.message)


            }

        }catch (e:Exception){
            Log.d(ErrorTraceFlags.CLOUD_MESSAGE_TRACE.flagName,"error while subscribing to topic cloud message ${e.message}")
        }

    }


}

