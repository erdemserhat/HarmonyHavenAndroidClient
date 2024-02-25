package com.erdemserhat.harmonyhaven.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.erdemserhat.harmonyhaven.presentation.login.state.LoginState
import com.erdemserhat.harmonyhaven.presentation.login.util.LoginValidationError
import com.erdemserhat.harmonyhaven.presentation.login.util.validateLoginFormant


class LoginViewModel() : ViewModel() {
    var state = mutableStateOf(LoginState())
        private set

    fun onLoginClicked(email: String, password: String) {
        //input validation
        val validationResult = validateLoginFormant(email,password)
        if(validationResult==LoginValidationError.NoError){
            //controlling the validated inputs
            if (email == "serhaterdem@gmail.com" && password == "erdem3451") {
                state.value = state.value.copy(canNavigateToDashBoard = true)
            } else {
                state.value = state.value.copy(canNavigateToDashBoard = false)
            }



        }

        state.value=state.value.copy(loginWarning = validationResult.errorMessage)


    }

}

//Updating states
