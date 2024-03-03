package com.erdemserhat.harmonyhaven.example

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.UserLogin
import com.erdemserhat.harmonyhaven.network.UserApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val engine: Engine,
    val userApiService: UserApiService
) : ViewModel() {
    fun A() {
        engine.A()
    }


    fun postUser() {
        val mockUserModel = UserLogin("ali@ex2ample.com", "ali123")
        viewModelScope.launch {

            try {
                val a = userApiService.login(mockUserModel)

                if (a.isSuccessful) {
                    Log.d("erdem3451", a.body()!!.email)
                } else {
                    Log.d("erdem3451", a.message())
                }


            } catch (e: Exception) {
                Log.d("erdem3451", e.message.toString())
            }


        }



    }

}