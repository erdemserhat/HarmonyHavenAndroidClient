package com.erdemserhat.harmonyhaven.example

import android.util.Log
import androidx.lifecycle.ViewModel
import com.erdemserhat.harmonyhaven.domain.model.User
import com.erdemserhat.harmonyhaven.domain.model.UserLogin
import com.erdemserhat.harmonyhaven.domain.model.UserUpdateModel
import com.erdemserhat.harmonyhaven.domain.usecase.users.LoginUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.UpdateUser
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis


@HiltViewModel
class ExampleViewModel @Inject constructor(

    private val userUseCases: UserUseCases
) : ViewModel() {



    @OptIn(DelicateCoroutinesApi::class)
    fun loginUser() {
        val mockUserModel = UserLogin("1", "1")

        GlobalScope.launch(Dispatchers.IO) {
            val elapsedTime = measureTimeMillis {
                val result = userUseCases.loginUser(mockUserModel)
                Log.d("erdem3451", result.toString())
            }
            Log.d("erdem3451", "Process Finished, Consumed Time: $elapsedTime ms")
        }







    }

    @OptIn(DelicateCoroutinesApi::class)
    fun registerUser() {
        val mockRegisterUser = User(
            id = 0,
            name = "Jack",
            surname = "Gabirella",
            password = "ADASDcc.3451.",
            email = "gabriellad23@d555555555gmal.com",
            gender = "Male",
            profilePhotoPath = "-"


        )
        GlobalScope.launch(Dispatchers.IO){
            val ellapsedTime = measureTimeMillis {
                val a =userUseCases.registerUser.invoke(mockRegisterUser)
                Log.d("erdem3451",a.toString())
            }
            Log.d("erdem3451","Consumed Time: $ellapsedTime")


        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun updateUser() {
       val userlogin = UserLogin(
           email = "me.serhaterdem@gmail.com",
           password = "123A.sadasd3fsdA"
       )

        val updatedUser=User(
            name = "Updated Name",
            surname="Updated Surname",
            password = "UpdatedPasswordéé,1212.!",
            email = "me.serhaterdem@gmail.com",
            profilePhotoPath = "1212",
            gender = "X",
            id = 1
        )

        val userUpdateModel = UserUpdateModel(
            userLogin = userlogin,
            updatedUserData = updatedUser
        )
        GlobalScope.launch(Dispatchers.IO){
            val ellapsedTime = measureTimeMillis {
                val a =userUseCases.updateUser.invoke(userUpdateModel)
                Log.d("erdem3451",a.toString())
            }
            Log.d("erdem3451","Consumed Time: $ellapsedTime")


        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteUser() {
        val userlogin = UserLogin(
            email = "me.serhaterdem@gmail.com",
            password = "UpdatedPasswordéé,1212.!"
        )


        GlobalScope.launch(Dispatchers.IO){
            val ellapsedTime = measureTimeMillis {
                val a =userUseCases.deleteUSer.invoke(userlogin)
                Log.d("erdem3451",a.toString())
            }
            Log.d("erdem3451","Consumed Time: $ellapsedTime")


        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun sendMail(){
        val email = "me.serhaterdem@gmail.com"
        try {
            GlobalScope.launch(Dispatchers.IO) {

                val consumedTime = measureTimeMillis {
                    val response = userUseCases.resetPasswordUser.sendMail(email).collect{
                        Log.d("erdem3451","Result->"+it.result.toString())
                        Log.d("erdem3451","Result Message->"+it.message.toString())
                    }

                }
                Log.d("erdem3451", "Consumed Time->$consumedTime")
            }

        }catch (e:Exception){
            Log.d("erdem3451","Error Message->${e.message.toString()}")
        }

    }



}