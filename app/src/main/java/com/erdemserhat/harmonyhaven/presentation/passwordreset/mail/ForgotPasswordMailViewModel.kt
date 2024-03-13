package com.erdemserhat.harmonyhaven.presentation.passwordreset.mail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import com.erdemserhat.harmonyhaven.domain.validation.isEmailValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgotPasswordMailViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _mailState = mutableStateOf(ForgotPasswordMailState())
    val mailState: State<ForgotPasswordMailState> = _mailState

    fun sendMail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if(!isEmailValid(email)){
                _mailState.value = _mailState.value.copy(
                    isLoading = false,
                    mailWarning = "Invalid E-mail Format",
                    canNavigateTo = false

                )
                return@launch
            }


            userUseCases.resetPasswordUser.sendMail(email).collect {
                _mailState.value = _mailState.value.copy(
                    isLoading = it.isLoading,
                    mailWarning = it.message,
                    canNavigateTo = it.result

                )
            }

        }

    }


}