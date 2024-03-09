package com.erdemserhat.harmonyhaven.example

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.User
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


    fun loginUser() {
        val mockUserModel = UserLogin("me.serhaterdem@gmail.com", "123A.sadasd3fsdA")
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

    fun registerUser() {
        val mockRegisterUser = User(
            id = 0,
            name = "Jack",
            surname = "Gabirella",
            password = "ADASDcc.3451.",
            email = "gabriellad23@gmal.com",
            gender = "Male",
            profilePhotoPath = "-"


        )
        viewModelScope.launch {

            try {
                val a = userApiService.register(mockRegisterUser)
                //Log.d("erdem3451", a.body()?.message.toString())
                if (a.isSuccessful) {
                    Log.d("erdem3451", a.body()?.message.toString())
                } else {

                    Log.d("erdem3451", "Erdem2")
                }


            } catch (e: Exception) {
                Log.d("erdem3451", e.message.toString())
            }


        }



    }

}