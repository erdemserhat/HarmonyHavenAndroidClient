package com.erdemserhat.harmonyhaven.presentation.post_authentication.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.rest.Result
import com.erdemserhat.harmonyhaven.domain.usecase.GetDailyQuoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getDailyQuoteUseCase: GetDailyQuoteUseCase
) : ViewModel() {

    private val _dailyQuote = MutableStateFlow<String?>(null)
    val dailyQuote: StateFlow<String?> = _dailyQuote

    init {
        fetchDailyQuote()
    }

    private fun fetchDailyQuote() {
        viewModelScope.launch {
            when (val result = getDailyQuoteUseCase()) {
                is Result.Success -> {
                    _dailyQuote.value = result.data
                }
                is Result.Error -> {
                    Log.d("fsdfsdf",result.message)
                }
            }
        }
    }
} 