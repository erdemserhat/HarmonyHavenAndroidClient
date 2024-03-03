package com.erdemserhat.harmonyhaven.example

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.UserLogin
import com.erdemserhat.harmonyhaven.network.UserApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ExampleViewModel @Inject constructor(private val engine: Engine):ViewModel() {
    fun A() {
        engine.A()
    }


    fun postUser() {
        val mockUserModel = UserLogin("me.serhaterdem@gmail.com","admin")
        viewModelScope.launch {
            try{
                val response =UserApi.retrofitService.authenticateUser(mockUserModel)
                Log.d("erdem3451","succcess")
                Log.d("erdem3451", response.body().toString())
            }catch (e:Exception){
                Log.d("erdem3451",e.message.toString())
            }





        }



    }

}