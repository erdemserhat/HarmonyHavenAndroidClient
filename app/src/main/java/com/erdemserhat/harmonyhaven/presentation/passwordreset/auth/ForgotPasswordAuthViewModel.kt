package com.erdemserhat.harmonyhaven.presentation.passwordreset.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.dto.requests.password_reset.PasswordResetAuthenticateRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgotPasswordAuthViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _authModel = mutableStateOf(ForgotPasswordAuthState())
    val authModel: State<ForgotPasswordAuthState> = _authModel

    fun authRequest(email: String, code: String) {
        _authModel.value = _authModel.value.copy(
            canNavigateTo = false,
            authWarning = "Loading...",
            isLoading = true
        )

        viewModelScope.launch(Dispatchers.IO) {

            val response = userUseCases.authenticatePasswordResetAttempt.executeRequest(
                PasswordResetAuthenticateRequest(
                    email = email,
                    code = code
                )
            )

            if (response == null) {
                _authModel.value = _authModel.value.copy(
                    canNavigateTo = false,
                    authWarning = "Network Error...",
                    isLoading = false
                )
                return@launch
            }

            if (!(response.result)) {
                _authModel.value = _authModel.value.copy(
                    canNavigateTo = false,
                    authWarning = response.message,
                    isLoading = false,
                    isError = true
                )
                return@launch

            }

            _authModel.value = _authModel.value.copy(
                canNavigateTo = true,
                authWarning = response.message,
                isLoading = false,
                isError = false,
                uuid = response.uuid
            )

            Log.d("erdem0001",response.message)


        }


    }
}