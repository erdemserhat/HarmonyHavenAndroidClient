package com.erdemserhat.harmonyhaven.presentation.passwordreset.code

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.PasswordResetModel
import com.erdemserhat.harmonyhaven.domain.usecase.users.ResetPasswordUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordCodeViewModel @Inject constructor (private val userUseCases: UserUseCases):ViewModel(){
    private val _codeState = mutableStateOf(ForgotPasswordCodeModel())
    val codeState : State<ForgotPasswordCodeModel> = _codeState

    fun sendCode(code:String, password:String){

        viewModelScope.launch(Dispatchers.IO){
            userUseCases.resetPasswordUser.resetPassword(code,password).collect{
                _codeState.value = _codeState.value.copy(
                    isLoading = it.isLoading,
                    codeWarning = it.message,
                    canNavigateTo = it.result
                )
            }



        }


    }

}