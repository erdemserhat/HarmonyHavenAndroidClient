package com.erdemserhat.harmonyhaven.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articleUseCases: ArticleUseCases

) : ViewModel() {
    private val _homeState = mutableStateOf(HomeState())

    val homeState: State<HomeState> = _homeState

    init{
        viewModelScope.launch(Dispatchers.IO) {
            articleUseCases.categories.invoke().collect {

                try{
                    _homeState.value = _homeState.value.copy(
                        isCategoryReady = it.isReady,
                        categories = it.categories
                    )

                }catch (_:Exception){

                }




            }
        }
    }
}