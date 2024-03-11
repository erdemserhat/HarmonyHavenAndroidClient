package com.erdemserhat.harmonyhaven.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.erdemserhat.harmonyhaven.domain.model.User
import com.erdemserhat.harmonyhaven.domain.model.UserLogin
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val userUseCases: UserUseCases) : ViewModel() {
    var state = mutableStateOf(LoginState())
        private set

    var state2 = mutableStateOf(LoginState2())
        private set

    @OptIn(DelicateCoroutinesApi::class)
    fun onLoginClicked(email: String, password: String) {

        GlobalScope.launch(Dispatchers.IO) {
            val response = userUseCases.loginUser(UserLogin(email, password)).collect {
                state2.value = state2.value.copy(isLoading = it.isLoading)
                state2.value = state2.value.copy(canNavigateToDashBoard = it.result)
                state2.value = state2.value.copy(loginWarning = it.message)

            }


        }


        /**
        val mockUserModel = UserLogin(email, password)

        GlobalScope.launch(Dispatchers.IO) {
        val elapsedTime = measureTimeMillis {
        val result = userUseCases.loginUser(mockUserModel)
        Log.d("erdem3451", result.toString())
        }
        Log.d("erdem3451", "Process Finished, Consumed Time: $elapsedTime ms")
        }







        //input validation
        val validationResult = validateLoginFormant(email, password)
        if (validationResult == LoginValidationError.NoError) {
        //controlling the validated inputs
        loginUser(email, password)


        }

        Log.d("erdem3451", state.value.canNavigateToDashBoard.toString())
        state.value = state.value.copy(loginWarning = validationResult.errorMessage)


         */
    }

    private fun loginUser(email: String, password: String): User? {
        /**
        val userLoginModel = UserLogin(email, password)
        var user:User? = null
        viewModelScope.launch(Dispatchers.IO) {
        try {
        val response = async { userApiService.login(userLoginModel) }

        if (response.await().isSuccessful) {
        //user = response.await().body()
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
         */
        return null
    }
}

