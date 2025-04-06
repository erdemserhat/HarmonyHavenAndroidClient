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
        Log.d("ChatViewModel", "Sending message: $message")
        viewModelScope.launch {
            // Set loading to true and add user message
            _chatState.value = _chatState.value.copy(
                isLoading = true,
                messages = _chatState.value.messages + ChatMessage.User(message)
            )

            try {
                var receivedPartialMessage = false
                var completeResponse = ""
                
                sseClient.connectToSSE(
                    prompt = message,
                    onMessageReceived = { part ->
                        if (part.isNotEmpty()) {
                            // SSE formatında "data:" ile başlayan satırlar vardır
                            // "data:" sadece ise, bu satır başlangıcını temsil eder
                            // "data:içerik" şeklindeyse, içeriği alırız
                            
                            if (part == "data:" || part.trim() == "data:") {
                                // Sadece "data:" ise bu yeni bir satır başlangıcıdır
                                completeResponse += "\n"
                            } else if (part.startsWith("data:")) {
                                // "data:" ile başlayan bir içerik varsa, "data:" prefixi kaldırılır
                                val content = part.substring(6)
                                completeResponse += content
                            } else {
                                // Normal metin - olduğu gibi ekle
                                completeResponse += part
                            }
                            
                            // First partial message received, stop showing loading indicator
                            if (!receivedPartialMessage) {
                                receivedPartialMessage = true
                                _chatState.value = _chatState.value.copy(isLoading = false)
                            }
                            
                            // Update the current message being built
                            _chatState.value = _chatState.value.copy(
                                currentMessage = completeResponse
                            )
                        }
                    },
                    onError = { error ->
                        Log.e("ChatViewModel", "SSE Error: $error")
                        _chatState.value = _chatState.value.copy(
                            error = "Mesaj alınırken bir hata oluştu: $error",
                            isLoading = false
                        )
                    },
                    onComplete = {
                        // When SSE connection is complete, add the final bot message to chat history
                        if (completeResponse.isNotEmpty()) {
                            _chatState.value = _chatState.value.copy(
                                messages = _chatState.value.messages + ChatMessage.Bot(completeResponse),
                                currentMessage = "" // Reset current message
                            )
                        }
                    }
                )
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Exception: ${e.message}", e)
                _chatState.value = _chatState.value.copy(
                    error = e.localizedMessage,
                    isLoading = false
                )
            }
        }
    }
}

