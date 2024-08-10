package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.erdemserhat.dto.responses.NotificationDto
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.erdemserhat.dto.responses.UserInformationDto
import com.erdemserhat.harmonyhaven.data.api.user.UserInformationApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.User
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class PasswordChangeResponseModel(
    val isCurrentPasswordCorrect:Boolean = true,
    val isNewPasswordAppropriate:Boolean = true,
    val isSuccessfullyChangedPassword:Boolean = false,
    val errorMessage:String="",
    val isNewPasswordsMatch:Boolean = true,
    val isLoading:Boolean = false,
    val isCurrentPasswordShort:Boolean=false
)

@HiltViewModel
class AccountInformationViewModel @Inject constructor(
    val userUseCases: UserUseCases
) : ViewModel() {
    private val _userInfo = mutableStateOf(UserInformationDto())
    val userInfo: State<UserInformationDto> = _userInfo

    private val _passwordChangeResponseModel = mutableStateOf(PasswordChangeResponseModel())
    val passwordChangeResponseModel: State<PasswordChangeResponseModel> = _passwordChangeResponseModel


    private fun getUserInfo(){
        // ViewModel içinde API çağrısını yapmak
        viewModelScope.launch {
            _userInfo.value = userUseCases.getUserInformation.executeRequest()

        }
    }

    init {
        getUserInfo()

    }


    fun changePassword(newPassword: String,currentPassword:String,confirmNewPassword:String) {

        if (currentPassword.length<8){
            _passwordChangeResponseModel.value = _passwordChangeResponseModel.value.copy(
                isCurrentPasswordShort = true,
            )
            return
        }


        if (newPassword!=confirmNewPassword){
            _passwordChangeResponseModel.value = _passwordChangeResponseModel.value.copy(
                isNewPasswordsMatch = false,
            )
            return
        }

        if (newPassword.length<8){
            _passwordChangeResponseModel.value = _passwordChangeResponseModel.value.copy(
                isNewPasswordAppropriate = false,
            )
            return
        }



        _passwordChangeResponseModel.value = PasswordChangeResponseModel(isNewPasswordsMatch = true, isLoading = true)

        viewModelScope.launch {
            delay(1000)
            val result = userUseCases.updateUserInformation.updatePassword(newPassword, currentPassword)
            if(!result.isValid){
                if(result.errorCode==0)
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

            }else{
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
            val result = userUseCases.updateUserInformation.updateName(newName)
            getUserInfo()
        }
    }

    fun resetPasswordResetStates(){
        _passwordChangeResponseModel.value = PasswordChangeResponseModel()
    }
}