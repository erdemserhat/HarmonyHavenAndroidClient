package com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.GetQuotesUseCase
import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import com.erdemserhat.harmonyhaven.domain.usecase.notification.NotificationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val getQuotesUseCase: GetQuotesUseCase,
) : ViewModel() {
    private val _quotes =
        MutableStateFlow<List<com.erdemserhat.harmonyhaven.dto.responses.Quote>>(emptyList())
    val quotes: StateFlow<List<com.erdemserhat.harmonyhaven.dto.responses.Quote>> = _quotes

    init {
        loadQuotes()
    }

    //load notification via offset
    private fun loadQuotes() {
        viewModelScope.launch {
            try {
                _quotes.value =
                    getQuotesUseCase.executeRequest()

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

            }
        }
    }




}