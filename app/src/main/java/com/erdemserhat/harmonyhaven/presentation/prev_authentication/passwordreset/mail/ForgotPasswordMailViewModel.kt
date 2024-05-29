package com.erdemserhat.harmonyhaven.presentation.prev_authentication.passwordreset.mail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.domain.validation.isEmailValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgotPasswordMailViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _mailState = mutableStateOf(ForgotPasswordMailState())
    val mailState: State<ForgotPasswordMailState> = _mailState

    fun sendMail(email: String) {
        _mailState.value = _mailState.value.copy(
            isLoading = true,
            mailWarning = "Loading...."

        )
        viewModelScope.launch(Dispatchers.IO) {
            if (!isEmailValid(email)) {
                _mailState.value = _mailState.value.copy(
                    isLoading = false,
                    mailWarning = "Invalid E-mail Format",
                    canNavigateTo = false,
                    isError = true

                )
                return@launch
            }


            val responseDeferred= async { userUseCases.sendPasswordResetMail.executeRequest(email) }
            val response = responseDeferred.await()

            if (response == null) {
                _mailState.value = _mailState.value.copy(
                    isLoading = false,
                    mailWarning = "Network Error",
                    canNavigateTo = false,

                    )
                return@launch


            }

            if (!response.result) {
                _mailState.value = _mailState.value.copy(
                    isLoading = false,
                    mailWarning = response.message,
                    canNavigateTo = false,
                    isError = true

                )
                return@launch

            }

            _mailState.value = _mailState.value.copy(
                isLoading = false,
                mailWarning = response.message,
                canNavigateTo = true,
                isError = false

            )


        }

    }


}