package com.erdemserhat.harmonyhaven.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.RegisterFormModel
import com.erdemserhat.harmonyhaven.domain.model.User
import com.erdemserhat.harmonyhaven.domain.model.toUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import com.erdemserhat.harmonyhaven.domain.validation.validateRegisterForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _registerState = mutableStateOf(RegisterState()) //--->ViewModel Specific
    val registerState : State<RegisterState> = _registerState //--->View Specific

    fun onRegisterClicked(formModel: RegisterFormModel){


        viewModelScope.launch(Dispatchers.IO) {

            try {
                validateRegisterForm(formModel)
            }catch (e:Exception){
                _registerState.value = _registerState.value.copy(
                    loginWarning = e.message.toString()

                )

                return@launch

            }

            userUseCases.registerUser(formModel.toUser()).collect{
                _registerState.value = _registerState.value.copy(
                    canNavigateTo = it.result,
                    loginWarning = it.message,
                    isLoading = it.isLoading
                )
            }
        }

    }
}