package com.erdemserhat.harmonyhaven.presentation.passwordreset.reset

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.ForgotPasswordResetClientModel
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordResetViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {


    private val _resetModel = mutableStateOf(ForgotPasswordResetState())
    val resetState: State<ForgotPasswordResetState> = _resetModel

    fun resetPassword(newPassword: String, uuid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userUseCases.resetPasswordUser.resetPassword(
                ForgotPasswordResetClientModel(
                    uuid,
                    newPassword
                )
            ).collect {
               _resetModel.value = _resetModel.value.copy(
                   isLoading = it.isLoading,
                   resetWarning = it.message,
                   canNavigateTo = it.result
               )
                Log.d("erdem3451",_resetModel.toString())
            }
        }
    }
}