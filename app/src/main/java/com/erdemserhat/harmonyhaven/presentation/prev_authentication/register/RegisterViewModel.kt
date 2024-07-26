package com.erdemserhat.harmonyhaven.presentation.prev_authentication.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.RegisterFormModel
import com.erdemserhat.harmonyhaven.domain.model.toUserInformationSchema
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import com.erdemserhat.harmonyhaven.domain.validation.areStringsEqual
import com.erdemserhat.harmonyhaven.dto.requests.UserInformationSchema
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.state.RegisterState
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.state.RegisterValidationState
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.state.getValidationStateByErrorCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _registerState = mutableStateOf(RegisterState()) //--->ViewModel Specific
    val registerState: State<RegisterState> = _registerState //--->View Specific

    fun onRegisterClicked(formModel: RegisterFormModel) {
        _registerState.value = _registerState.value.copy(
            isLoading = true
        )
        if (!areStringsEqual(formModel.password, formModel.confirmPassword)) {
            _registerState.value = _registerState.value.copy(
                registerValidationState = RegisterValidationState(isPasswordValid = false),
                registerWarning = "Passwords don't match",
                isLoading = false
            )
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            registerUser(formModel.toUserInformationSchema())

        }

    }

    private suspend fun registerUser(userInformationSchema: UserInformationSchema) {
        viewModelScope.launch(Dispatchers.IO) {
            val responseDeferred =  async { userUseCases.registerUser.executeRequest(userInformationSchema) }
            val response = responseDeferred.await()

            if (response == null) {
                //network error
                _registerState.value = _registerState.value.copy(
                    registerWarning = "Network Error...",
                    isLoading = false
                )

                return@launch
            }


            if (!response.formValidationResult.isValid) {
                //form not valid
                val errorCode = response.formValidationResult.errorCode
                _registerState.value = _registerState.value.copy(
                    registerValidationState = RegisterValidationState().getValidationStateByErrorCode(errorCode),
                    registerWarning = response.formValidationResult.errorMessage.toString(),
                    isLoading = false
                )
                return@launch
            }

            if(!response.isRegistered){
                _registerState.value = _registerState.value.copy(
                    registerWarning = "An error occurred about your registration",
                    isLoading = false
                )
                return@launch
            }

            //user's information's are valid
            //passwords are match
            //there is no problem in this line

            _registerState.value = _registerState.value.copy(
                canNavigateTo = true
            )


        }

    }
}