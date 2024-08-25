package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.dto.responses.UserInformationDto
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PasswordChangeResponseModel(
    val isCurrentPasswordCorrect: Boolean = true,
    val isNewPasswordAppropriate: Boolean = true,
    val isSuccessfullyChangedPassword: Boolean = false,
    val errorMessage: String = "",
    val isNewPasswordsMatch: Boolean = true,
    val isLoading: Boolean = false,
    val isCurrentPasswordShort: Boolean = false
)

data class NameChangeState(
    val isNameCorrect: Boolean = true,
    val isNameAppropriate: Boolean = true,
    val result:Boolean = false
)

@HiltViewModel
class AccountInformationViewModel @Inject constructor(
    val userUseCases: UserUseCases
) : ViewModel() {
    private val _userInfo = mutableStateOf(UserInformationDto())
    val userInfo: State<UserInformationDto> = _userInfo

    private val _passwordChangeResponseModel = mutableStateOf(PasswordChangeResponseModel())
    val passwordChangeResponseModel: State<PasswordChangeResponseModel> =
        _passwordChangeResponseModel

    private val _nameChangeState = mutableStateOf(NameChangeState())
    val nameChangeState: State<NameChangeState> =
        _nameChangeState


    private fun getUserInfo() {
        // ViewModel içinde API çağrısını yapmak
        viewModelScope.launch {
            _userInfo.value = userUseCases.getUserInformation.executeRequest()

        }
    }

    init {
        getUserInfo()

    }


    fun changePassword(newPassword: String, currentPassword: String, confirmNewPassword: String) {

        if (currentPassword.length < 8) {
            _passwordChangeResponseModel.value = _passwordChangeResponseModel.value.copy(
                isCurrentPasswordShort = true,
            )
            return
        }


        if (newPassword != confirmNewPassword) {
            _passwordChangeResponseModel.value = _passwordChangeResponseModel.value.copy(
                isNewPasswordsMatch = false,
            )
            return
        }

        if (newPassword.length < 8) {
            _passwordChangeResponseModel.value = _passwordChangeResponseModel.value.copy(
                isNewPasswordAppropriate = false,
            )
            return
        }



        _passwordChangeResponseModel.value =
            PasswordChangeResponseModel(isNewPasswordsMatch = true, isLoading = true)

        viewModelScope.launch {
            delay(1000)
            val result =
                userUseCases.updateUserInformation.updatePassword(newPassword, currentPassword)
            if (!result.isValid) {
                if (result.errorCode == 0)
                    _passwordChangeResponseModel.value = _passwordChangeResponseModel.value.copy(
                        isCurrentPasswordCorrect = false,
                        errorMessage = result.errorMessage,
                        isLoading = false

                    )
                else
                    _passwordChangeResponseModel.value = _passwordChangeResponseModel.value.copy(
                        isNewPasswordAppropriate = false,
                        errorMessage = result.errorMessage,
                        isLoading = false

                    )

            } else {
                _passwordChangeResponseModel.value = _passwordChangeResponseModel.value.copy(
                    isSuccessfullyChangedPassword = true,
                    errorMessage = "",
                    isLoading = false

                )


            }


        }


    }

    fun changeName(newName: String) {
        viewModelScope.launch {
            if(newName.length<2){
                _nameChangeState.value = _nameChangeState.value.copy(
                    isNameAppropriate = false
                )
                return@launch

            }
            val result = userUseCases.updateUserInformation.updateName(newName)
            if(result)
                _nameChangeState.value = NameChangeState(result = true)
            else
                _nameChangeState.value = NameChangeState()

            getUserInfo()
        }
    }

    fun resetPasswordResetStates() {
        _passwordChangeResponseModel.value = PasswordChangeResponseModel()
    }

    fun resetNameState(){
        _nameChangeState.value = NameChangeState()
    }
}