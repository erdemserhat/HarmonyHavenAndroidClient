package com.erdemserhat.harmonyhaven.presentation.prev_authentication.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.state.LoginState
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.state.LoginValidationState
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.state.getValidationStateByErrorCode
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val jwtRepository: JwtTokenRepository
) : ViewModel() {

    //Encapsulation principle
    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState


    fun onLoginClicked(email: String, password: String) {
        _loginState.value = _loginState.value.copy(
            isLoading = true,
        )

        authenticateUser(email, password)
    }

    init {
        onLoginClicked("example@gmail.com","ExamplePassword.010.")
    }

    private fun authenticateUser(email: String, password: String) {
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
                    loginWarning =  when (errorCode) {
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

            jwtRepository.saveJwtToken(response.jwt!!)
            Log.d("AuthenticationTests",response.toString())
            Log.d("AuthenticationTests", "jwt saved as :" + jwtRepository.getJwtToken())
            ///Redirect the user main page...

            _loginState.value = _loginState.value.copy(
                isLoading = false,
                canNavigateToDashBoard = true,
                loginWarning = "Hoş Geldin  ${userUseCases.getUserInformation.executeRequest().name} :)"
            )
            //if login is successfully then save the fcm id

            getToken()



        }
    }

    private fun getToken() {
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

    }


}

