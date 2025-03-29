package com.erdemserhat.harmonyhaven.domain.usecase

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.ChatApiService
import com.erdemserhat.harmonyhaven.data.api.ChatDto
import javax.inject.Inject


class ChatUseCase @Inject constructor(
    private val chatApiService: ChatApiService
) {

    suspend fun sendMessage(text:String):String{
        try {
            val response = chatApiService.sendMessage(ChatDto(text))


            if(response.isSuccessful){
                Log.d("chatapiservice","+")

                return response.body()!!.text
            }else{
                Log.d("chatapiservice","-")
                return response.body()!!.text
            }

        }catch (e:Exception){
            Log.d("chatapiservice","?")

            Log.d("chatapiservice",e.localizedMessage)
            return "Bir hata oluştu"

        }

    }
}