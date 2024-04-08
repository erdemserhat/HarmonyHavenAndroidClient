package com.erdemserhat.harmonyhaven

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.network.CategoryApiService
import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.data.room.JwtTokenRepository
import com.erdemserhat.harmonyhaven.domain.model.rest.User
import com.erdemserhat.harmonyhaven.domain.model.rest.client.UserLogin
import com.erdemserhat.harmonyhaven.domain.model.rest.client.UserUpdateModel
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis


@HiltViewModel
class ExampleViewModel @Inject constructor(

    private val userUseCases: UserUseCases,
    private val userApiService: UserApiService,
    private val art: CategoryApiService,
    private val articleUseCases: ArticleUseCases,
    private val jwtRepo: JwtTokenRepository
) : ViewModel() {
    fun getJwt() {
        viewModelScope.launch(Dispatchers.IO) {


        }
    }

    fun authenticateUser(){
        viewModelScope.launch(Dispatchers.IO) {
            val request = async {  userUseCases.authenticateUser.executeRequest(UserAuthenticationRequest(
                email = "me.serhaterdem@gmail.com",
                password = "Erdem.3451."
            ))}

            val response = request.await()

            if(response==null){
                //network error
                Log.d("AuthenticationTests","Network Error")
                return@launch
            }

            if(!response.formValidationResult.isValid){
                //form not valid
                Log.d("AuthenticationTests","Form Invalid")
                return@launch
            }

            if(!response.credentialsValidationResult!!.isValid){
                //credentials are not valid
                Log.d("AuthenticationTests","Credentials Invalid")

                return@launch
            }

            if(!response.isAuthenticated){
                //user banned
                Log.d("AuthenticationTests","User Banned")
                return@launch
            }
            Log.d("AuthenticationTests","Everything is nice")
            jwtRepo.saveJwtToken(response.jwt!!)
            Log.d("AuthenticationTests","jwt saved as :"+jwtRepo.getJwtToken())



        }
    }


}