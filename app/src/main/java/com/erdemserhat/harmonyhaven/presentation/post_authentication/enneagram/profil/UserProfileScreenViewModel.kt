package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.EnneagramUseCase
import com.erdemserhat.harmonyhaven.domain.usecase.user.GetUserInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserProfileScreenViewModel @Inject constructor(
    private val enneagramUseCase: EnneagramUseCase,
    private val userInformation: GetUserInformation
) : ViewModel() {

    private val _state = mutableStateOf(UserProfileState())
    val state: State<UserProfileState> = _state

    private var userProfile
        get() = _state.value
        set(value) {
            _state.value = value
        }

    init {
        checkTestResult()
    }



    fun checkTestResult() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userProfile.isLoading = true
                val testResultDeferred = async {
                    enneagramUseCase.checkTestResult()
                }

                val userInfoDeferred = async {
                    userInformation.executeRequest()
                }
                val testResult = testResultDeferred.await()
                val userInfo = userInfoDeferred.await()

                userProfile =  UserProfileState(
                    isLoading = false,
                    result = testResult,
                    username = userInfo.name,
                    userProfilePicturePath = userInfo.profilePhotoPath

                )



            } catch (e: Exception) {

            }


        }

    }

}