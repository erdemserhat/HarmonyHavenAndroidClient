package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.GetQuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteMainViewModel @Inject constructor(
    private val getQuotesUseCase: GetQuotesUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _quotes =
        MutableStateFlow<List<com.erdemserhat.harmonyhaven.dto.responses.Quote>>(emptyList())
    val quotes: StateFlow<List<com.erdemserhat.harmonyhaven.dto.responses.Quote>> = _quotes

    // StateFlow ile UX Dialog gösterecek mi
    private val _shouldShowUxDialog1 = MutableStateFlow(true)
    val shouldShowUxDialog1: StateFlow<Boolean> = _shouldShowUxDialog1

    init {
        loadQuotes()
        initializeUxDialogState()
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


    private fun initializeUxDialogState() {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val showDialog = sharedPreferences.getBoolean("shouldShowUxDialog1", true)
        _shouldShowUxDialog1.value = showDialog
    }

    fun setShouldShowUxDialog1(show: Boolean) {
        _shouldShowUxDialog1.value = show
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("shouldShowUxDialog1", show).apply()
        initializeUxDialogState()
    }


}