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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountInformationViewModel @Inject constructor(
    val userUseCases: UserUseCases
) : ViewModel() {
    private val _userInfo = mutableStateOf(UserInformationDto())
    val userInfo: State<UserInformationDto> = _userInfo


    private fun getUserInfo(){
        // ViewModel içinde API çağrısını yapmak
        viewModelScope.launch {
            _userInfo.value = userUseCases.getUserInformation.executeRequest()

        }
    }

    init {
        getUserInfo()

    }


    fun changePassword(newPassword: String,currentPassword:String) {
        viewModelScope.launch {
            val result = userUseCases.updateUserInformation.updatePassword(newPassword, currentPassword)
        }
    }

    fun changeName(newName: String) {
        viewModelScope.launch {
            val result = userUseCases.updateUserInformation.updateName(newName)
            getUserInfo()
        }
    }
}