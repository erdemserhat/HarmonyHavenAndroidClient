package com.erdemserhat.harmonyhaven.domain.usecase

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.ChatApiService
import com.erdemserhat.harmonyhaven.data.api.ChatDto
import com.erdemserhat.harmonyhaven.data.api.SSEClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ChatUseCase @Inject constructor(
    private val sseClient: SSEClient
) {

    suspend fun sendMessage(text:String): Flow<String> = flow{
        val scope = CoroutineScope(Dispatchers.IO)
        try {
            sseClient.connectToSSE(
                prompt = text,
                onMessageReceived = {message->
                    scope.launch {
                        emit(message)

                    }
                },
                onError = {
                    scope.launch {
                        emit("error")

                    }
                }
            )

        }catch (e:Exception){
            Log.d("chatapiservice","?")
            Log.d("chatapiservice",e.localizedMessage)

        }

    }
}