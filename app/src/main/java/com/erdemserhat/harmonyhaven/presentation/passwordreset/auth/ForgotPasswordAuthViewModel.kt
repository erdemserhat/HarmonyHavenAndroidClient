package com.erdemserhat.harmonyhaven.presentation.passwordreset.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.ForgotPasswordAuthClientModel
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgotPasswordAuthViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _authModel = mutableStateOf(ForgotPasswordAuthState())
    val authModel: State<ForgotPasswordAuthState> = _authModel

    fun authRequest(email: String, code: String) {
        viewModelScope.launch {
            userUseCases.resetPasswordUser.authenticateRequest(
                ForgotPasswordAuthClientModel(
                    code,
                    email
                )
            ).collect {
                _authModel.value = _authModel.value.copy(
                    canNavigateTo = it.result,
                    authWarning = it.message,
                    isLoading = it.isLoading,
                    uuid = it.uuid
                )
                Log.d("erdem3451",_authModel.value.uuid.toString())
            }


        }




    }


}