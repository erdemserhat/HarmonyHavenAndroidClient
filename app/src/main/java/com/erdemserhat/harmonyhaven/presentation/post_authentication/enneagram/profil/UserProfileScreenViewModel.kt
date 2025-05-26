package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erdemserhat.harmonyhaven.domain.usecase.EnneagramUseCase
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.user.GetUserInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserProfileScreenViewModel @Inject constructor(
    private val enneagramUseCase: EnneagramUseCase,
    private val userInformation: GetUserInformation,
    private val articleUseCases: ArticleUseCases
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


    private fun loadArticle(){
        viewModelScope.launch(Dispatchers.IO) {
            val article = articleUseCases.getArticleById.executeRequest(_state.value.result?.detailedResult?.fullDescriptionCode ?: 0)
            _state.value = _state.value.copy(
                article = article
            )
        }

    }


    fun resetScrollState(){
        userProfile.shouldResetScrollState = true

    }

    fun protectScrollState(){
        userProfile.shouldResetScrollState = false

    }



    fun checkTestResult(onCompleted: () -> Unit = {}) {
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

                onCompleted()
                loadArticle()


            } catch (e: Exception) {

            }


        }

    }

    fun refreshTestResults(onCompleted:()->Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val testResultDeferred = async {
                    enneagramUseCase.checkTestResult()
                }

                val userInfoDeferred = async {
                    userInformation.executeRequest()
                }
                val testResult = testResultDeferred.await()
                val userInfo = userInfoDeferred.await()

                userProfile =  UserProfileState(
                    result = testResult,
                    username = userInfo.name,
                    userProfilePicturePath = userInfo.profilePhotoPath

                )
                loadArticle()



            } catch (e: Exception) {

            }finally {
                onCompleted()
            }


        }

    }

}