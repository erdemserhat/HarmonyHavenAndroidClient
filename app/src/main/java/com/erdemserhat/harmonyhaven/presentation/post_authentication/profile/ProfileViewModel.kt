package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.notification.NotificationUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.quote.QuoteUseCases
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val notificationUseCases: NotificationUseCases,
    private val quoteUseCases: QuoteUseCases,
    private val userUseCases: UserUseCases
):ViewModel() {
    private val _profileState = MutableStateFlow(ProfileState())
    val profileState:StateFlow<ProfileState> = _profileState



    fun gatherCounts(){
        viewModelScope.launch {
            val activeDays = userUseCases.getUserInformation.executeRequest().activeDays
            val likedQuoteCount =quoteUseCases.getLikedQuotes.executeRequest().size
            val messageCount = notificationUseCases.getNotificationCount.executeRequest()
            _profileState.value =_profileState.value.copy(
                likedCount = likedQuoteCount,
                messageCount = messageCount,
                activeDays = activeDays
            )

        }
    }
}