package com.erdemserhat.harmonyhaven.presentation.post_authentication.chat

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.api.SSEClient
import com.erdemserhat.harmonyhaven.domain.usecase.ChatUseCase
import com.erdemserhat.harmonyhaven.domain.usecase.article.ArticleUseCases
import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sseClient: SSEClient
): ViewModel(){
    private val _chatState = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState


    fun sendMessage(message: String) {
        Log.d("dsdsfsd","started")
        viewModelScope.launch {
            _chatState.value = _chatState.value.copy(isLoading = true)
            Log.d("chatapiservice",chatState.value.toString())

            try {
                _chatState.value = _chatState.value.copy(
                    messages = _chatState.value.messages + ChatMessage.User(message),
                )
                Log.d("chatapiservice",chatState.value.toString())


                var currentMessage = ""
                sseClient.connectToSSE(
                    prompt = message,
                    onMessageReceived = {it->
                        if(it.isNotEmpty()){
                            val part = it.substring(5)
                            currentMessage+=part

                            _chatState.value = _chatState.value.copy(
                                currentMessage = currentMessage,
                                isLoading = false
                            )

                        }


                    },
                    onError = {
                        Log.d("dsfs","dsafsd")

                    }
                )

                _chatState.value = _chatState.value.copy(
                    messages = _chatState.value.messages + ChatMessage.Bot(message),
                )


                Log.d("chatapiservice",chatState.value.toString())


            } catch (e: Exception) {
                _chatState.value = _chatState.value.copy(error = e.localizedMessage, isLoading = false)
            }
        }
    }

}

