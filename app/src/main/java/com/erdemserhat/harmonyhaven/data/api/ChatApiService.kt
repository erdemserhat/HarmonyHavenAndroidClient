package com.erdemserhat.harmonyhaven.data.api

import com.erdemserhat.harmonyhaven.dto.requests.UserInformationSchema
import com.erdemserhat.harmonyhaven.dto.responses.RegistrationResponse
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApiService {
    @POST("v1/chat")
    suspend fun sendMessage(@Body chat: ChatDto): Response<ChatDto>

}

@Serializable
data class ChatDto(val text: String)
