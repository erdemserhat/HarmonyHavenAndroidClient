package com.erdemserhat.harmonyhaven.presentation.post_authentication.chat

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.usecase.ChatUseCase
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase
): ViewModel(){
    private val _chatState = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState


    fun sendMessage(message: String) {

        viewModelScope.launch {
            _chatState.value = _chatState.value.copy(isLoading = true)
            Log.d("chatapiservice",chatState.value.toString())

            try {
                _chatState.value = _chatState.value.copy(
                    messages = _chatState.value.messages + ChatMessage.User(message),
                )
                Log.d("chatapiservice",chatState.value.toString())

                val response = chatUseCase.sendMessage(message)
                _chatState.value = _chatState.value.copy(
                    messages = _chatState.value.messages + ChatMessage.Bot(response),
                    isLoading = false
                )
                Log.d("chatapiservice",chatState.value.toString())


            } catch (e: Exception) {
                _chatState.value = _chatState.value.copy(error = e.localizedMessage, isLoading = false)
            }
        }
    }

}
