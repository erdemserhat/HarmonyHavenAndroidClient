package com.erdemserhat.harmonyhaven.example

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.network.CategoryApiService
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class ExampleViewModel @Inject constructor(

    private val userUseCases: UserUseCases,
    private val art: CategoryApiService,
    private val articleUseCases: ArticleUseCases
) : ViewModel() {
    fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseMessaging.getInstance().subscribeToTopic("everyone")
                .addOnCompleteListener { task: Task<Void?> ->
                    Log.d("erdem",task.result.toString())
                    if (task.isSuccessful) {
                        Log.d("spec1", "Successfully subscribed to topic")
                    } else {
                        Log.e("spec1", "Failed to subscribe to topic", task.exception)
                    }
                }
            val localToken = Firebase.messaging.token.await()
            Log.d("erdem1212",localToken.toString())



        }


    }


}