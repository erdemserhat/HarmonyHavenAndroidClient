package com.erdemserhat.harmonyhaven.example

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.User
import com.erdemserhat.harmonyhaven.domain.model.UserLogin
import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExampleViewModel @Inject constructor(

    private val userUseCases: UserUseCases
) : ViewModel() {



    fun loginUser() {
        val mockUserModel = UserLogin("me.serhaterdem@gmail.com", "123A.sadasd3fsdA")
        Log.d("erdem3451", mockUserModel.toString())



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
        Log.d("erdem3451", mockRegisterUser.toString())

    }

}