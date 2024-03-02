package com.erdemserhat.harmonyhaven.example

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.model.user.LoginModel
import com.erdemserhat.network.retrofit.UserApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExampleViewModel @Inject constructor(private val engine: Engine):ViewModel() {
    fun A() {
        engine.A()
    }


    fun postUser() {
        viewModelScope.launch {
            val userList:List<LoginModel> = try {
                UserApi.retrofitService.getUser()
                //Log.d("erdem3451","başarı")


            }catch (e:Exception){
                listOf(LoginModel("errorr",""))
                //Log.d("erdem3451",e.message.toString())

            }

            Log.d("erdem3451",userList[0].email)


        }



    }

}