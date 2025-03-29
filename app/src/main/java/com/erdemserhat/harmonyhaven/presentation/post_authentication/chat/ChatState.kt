package com.erdemserhat.harmonyhaven.presentation.post_authentication.chat

import androidx.compose.runtime.mutableStateOf


data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class ChatMessage {
    data class User(val text: String) : ChatMessage()
    data class Bot(val text: String) : ChatMessage()
}