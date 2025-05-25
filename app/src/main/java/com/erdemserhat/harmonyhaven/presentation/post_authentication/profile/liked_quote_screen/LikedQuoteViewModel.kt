package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.liked_quote_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.quote.QuoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LikedQuoteViewModel @Inject constructor(
    private val quoteUseCases: QuoteUseCases
) : ViewModel() {

    private val _likedQuotesState = MutableStateFlow<LikedQuotesState>(LikedQuotesState())
    val likedQuotesState: StateFlow<LikedQuotesState> = _likedQuotesState



    fun getLikedQuotes() {
        viewModelScope.launch {
            _likedQuotesState.value = _likedQuotesState.value.copy(isLoading = true)
            val likedQuotes = quoteUseCases.getLikedQuotes.executeRequest()
            _likedQuotesState.value =
                _likedQuotesState.value.copy(isLoading = false, likedQuotes = likedQuotes)


        }
    }


}