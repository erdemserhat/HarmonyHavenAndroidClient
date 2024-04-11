package com.erdemserhat.harmonyhaven.presentation.passwordreset.reset

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.rest.client.ForgotPasswordResetClientModel
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import com.erdemserhat.harmonyhaven.dto.requests.password_reset.PasswordResetAuthenticateRequest
import com.erdemserhat.harmonyhaven.dto.requests.password_reset.PasswordResetFinalRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordResetViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {


    private val _resetModel = mutableStateOf(ForgotPasswordResetState())
    val resetState: State<ForgotPasswordResetState> = _resetModel

    fun resetPassword(newPassword: String,confirmPassword:String, uuid: String) {


        if(newPassword!=confirmPassword){
            _resetModel.value = _resetModel.value.copy(
                isLoading = false,
                resetWarning = "Passwords don't match",
                canNavigateTo = false,
                isError = true
            )
            return

        }

        viewModelScope.launch(Dispatchers.IO) {

            val responseDeferred = async {   userUseCases.completePasswordResetAttempt.executeRequest(
                PasswordResetFinalRequest(
                    uuid = uuid,
                    password = newPassword
                )
            )}

            val response = responseDeferred.await()

            if (response == null) {
                _resetModel.value = _resetModel.value.copy(
                    isLoading = false,
                    resetWarning = "Network Error",
                    canNavigateTo = false
                )

                return@launch

            }

            if (!response.result) {
                _resetModel.value = _resetModel.value.copy(
                    isLoading = false,
                    resetWarning = response.message,
                    canNavigateTo = false,
                    isError = true
                )

                return@launch
            }

            _resetModel.value = _resetModel.value.copy(
                isLoading = false,
                resetWarning = response.message,
                canNavigateTo = true,
                isError = false
            )

        }
    }
}