package com.erdemserhat.harmonyhaven.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.rest.client.UserLogin
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCases: UserUseCases) : ViewModel() {


        //Encapsulation principle
    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState


    fun onLoginClicked(email: String, password: String) {

        viewModelScope.launch(Dispatchers.IO) {

            userUseCases.loginUser(UserLogin(email, password)).collect {
                _loginState.value = _loginState.value.copy(
                    isLoading = it.isLoading,
                    canNavigateToDashBoard = it.result,
                    loginWarning = it.message
                )
            }


        }
    }
}

