package com.erdemserhat.harmonyhaven.example

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.network.CategoryApiService
import com.erdemserhat.harmonyhaven.domain.model.rest.User
import com.erdemserhat.harmonyhaven.domain.model.rest.client.UserLogin
import com.erdemserhat.harmonyhaven.domain.model.rest.client.UserUpdateModel
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.users.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis


@HiltViewModel
class ExampleViewModel @Inject constructor(

    private val userUseCases: UserUseCases,
    private val art: CategoryApiService,
    private val articleUseCases: ArticleUseCases
) : ViewModel() {
    fun getArticlesByCategory(i: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            articleUseCases.getArticlesByCategory(i)


        }


    }


}