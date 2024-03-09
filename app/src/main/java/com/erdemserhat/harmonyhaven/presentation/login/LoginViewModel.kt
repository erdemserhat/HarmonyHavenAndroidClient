package com.erdemserhat.harmonyhaven.presentation.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.User
import com.erdemserhat.harmonyhaven.domain.model.UserLogin
import com.erdemserhat.harmonyhaven.network.UserApiService
import com.erdemserhat.harmonyhaven.presentation.login.state.LoginState
import com.erdemserhat.harmonyhaven.presentation.login.util.LoginValidationError
import com.erdemserhat.harmonyhaven.presentation.login.util.validateLoginFormant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(val userApiService: UserApiService) : ViewModel() {
    var state = mutableStateOf(LoginState())
        private set

    @OptIn(DelicateCoroutinesApi::class)
    fun onLoginClicked(email: String, password: String) {
        //input validation
        val validationResult = validateLoginFormant(email, password)
        if (validationResult == LoginValidationError.NoError) {
            //controlling the validated inputs
            loginUser(email,password)


        }

        Log.d("erdem3451",state.value.canNavigateToDashBoard.toString())
        state.value = state.value.copy(loginWarning = validationResult.errorMessage)


    }

    private fun loginUser(email: String, password: String): User? {
        val userLoginModel = UserLogin(email, password)
        var user:User? = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = async { userApiService.login(userLoginModel) }

                if (response.await().isSuccessful) {
                    user = response.await().body()
                    user?.email?.let {
                        Log.d("erdem3451", it)
                        state.value = state.value.copy(canNavigateToDashBoard = true)

                    }

                } else {
                    user = null
                    Log.d("erdem3451", "user null")
                }


            } catch (e: Exception) {
                Log.d("erdem3451", e.message.toString())
            }


        }

        return user

    }
}


