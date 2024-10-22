package com.erdemserhat.harmonyhaven.presentation.prev_authentication.register

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import com.erdemserhat.harmonyhaven.domain.model.RegisterFormModel
import com.erdemserhat.harmonyhaven.domain.model.toUserInformationSchema
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.dto.requests.GoogleAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.requests.UserInformationSchema
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.state.LoginValidationState
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.state.getValidationStateByErrorCode
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.state.RegisterState
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.state.RegisterValidationState
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.state.getValidationStateByErrorCode
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
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    @ApplicationContext private val context: Context,
    private val jwtRepository: JwtTokenRepository
) : ViewModel() {

    private val _registerState = mutableStateOf(RegisterState()) //--->ViewModel Specific
    val registerState: State<RegisterState> = _registerState //--->View Specific
    private val sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)


    fun onRegisterClicked(formModel: RegisterFormModel) {
        _registerState.value = _registerState.value.copy(
            isLoading = true
        )
        if (formModel.password != formModel.confirmPassword) {
            _registerState.value = _registerState.value.copy(
                registerValidationState = RegisterValidationState(isPasswordValid = false),
                registerWarning = "Şifreler uyuşmuyor.",
                isLoading = false
            )
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            delay(400)
            registerUser(formModel.toUserInformationSchema())

        }

    }

    private fun registerUser(userInformationSchema: UserInformationSchema) {
        try {

            viewModelScope.launch(Dispatchers.IO) {
                val responseDeferred = async {
                    userUseCases.registerUser.executeRequest(userInformationSchema.apply {
                        userInformationSchema.surname = "default"
                    })
                }
                val response = responseDeferred.await()

                if (response == null) {
                    //network error
                    _registerState.value = _registerState.value.copy(
                        registerWarning = "Bağlantı Hatası.",
                        isLoading = false
                    )

                    return@launch
                }


                if (!response.formValidationResult.isValid) {
                    //form not valid
                    val errorCode = response.formValidationResult.errorCode
                    _registerState.value = _registerState.value.copy(
                        registerValidationState = RegisterValidationState().getValidationStateByErrorCode(
                            errorCode
                        ),
                        registerWarning = when (errorCode) {
                            //TODO : I KNOW I SHOULD NOT DO THIS HARDCODING IS BAD AND LOCALIZATION ETC..
                            //TODO : BUT I DON'T HAVE TIME FOR THAT
                            201 -> "İsim çok kısa. En az 2 karakter uzunluğunda olmalıdır."
                            202 -> "İsim yalnızca harfler içermelidir."
                            203 -> "Soyad çok kısa. En az 2 karakter uzunluğunda olmalıdır."
                            204 -> "Soyad yalnızca harfler içermelidir."
                            205 -> "Geçersiz e-posta formatı."
                            206 -> "Bu e-posta ile zaten kayıtlı bir kullanıcı var."
                            207 -> "Şifre en az 8 karakter uzunluğunda olmalıdır."
                            208 -> "Şifre en az bir büyük harf, bir küçük harf ve bir rakam içermelidir."
                            209 -> "Şifre kullanıcı adını içermemelidir. Lütfen farklı bir şifre seçin."
                            210 -> "Şifre soyadı içermemelidir. Lütfen farklı bir şifre seçin."
                            211 -> "Şifre e-posta adresini içermemelidir. Lütfen farklı bir şifre seçin."
                            else -> "Bilinmeyen bir hata oluştu."
                        },
                        isLoading = false
                    )
                    return@launch
                }

                if (!response.isRegistered) {
                    _registerState.value = _registerState.value.copy(
                        registerWarning = "Bağlantı hatası.",
                        isLoading = false
                    )
                    return@launch
                }

                //user's information's are valid
                //passwords are match
                //there is no problem in this line

                val request = async {
                    userUseCases.authenticateUser.executeRequest(
                        UserAuthenticationRequest(
                            email = userInformationSchema.email,
                            password = userInformationSchema.password
                        )
                    )
                }

                val responseAuth = request.await()
                Log.d("dasdsadsadsa",responseAuth.toString())

                Log.d("AuthenticationTests", "Everything is nice")


                sharedPrefs.edit().putString("email", userInformationSchema.email).apply()

                if (responseAuth != null) {
                    jwtRepository.saveJwtToken(responseAuth.jwt!!)
                }
                Log.d("AuthenticationTests", responseAuth.toString())
                Log.d("AuthenticationTests", "jwt saved as :" + jwtRepository.getJwtToken())
                ///Redirect the user main page...

                getToken()
                val sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPrefs.edit()
                editor.putBoolean("isLoggedInBefore", true)
                editor.apply()

                _registerState.value = _registerState.value.copy(
                    canNavigateTo = true
                )


            }
        } catch (e: Exception) {
            Log.d("testRegisterScreen", e.message!!)
        }

    }


    private fun getToken() {
        try {
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

        } catch (e: Exception) {
            //handle
        }


    }

    fun handleSignInWithGoogle(result: GetCredentialResponse) {
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

                        _registerState.value = _registerState.value.copy(
                            isLoading = true,
                        )
                        viewModelScope.launch {
                            val response = userUseCases.authenticateUserViaGoogle.executeRequest(
                                GoogleAuthenticationRequest(
                                    googleIdToken = googleIdTokenCredential.idToken
                                )
                            )

                            jwtRepository.saveJwtToken(response?.jwt!!)
                            _registerState.value = _registerState.value.copy(
                                isLoading = false,
                                canNavigateTo = true
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