package com.erdemserhat.harmonyhaven.domain.usecase

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramAnswersDto
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramApiService
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramQuestionDto
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramTestResultDetailedDto
import javax.inject.Inject

class EnneagramUseCase @Inject constructor(
    private val enneagramApiService: EnneagramApiService
) {

    suspend fun getQuestions (): List<EnneagramQuestionDto>? {
        try {
            val result = enneagramApiService.getQuestions()

            if(result.isSuccessful){
                Log.d("ENNEAGRAM_USECASE","api request is successfully")

                val questions = result.body()!!
                return questions
            }else{
                Log.d("ENNEAGRAM_USECASE","api request is unsuccessfully")

                val errorBodyJson = result.errorBody()?.string()
                throw EnneagramApiException(errorBodyJson ?: "error message was null")

            }
        }catch (e:Exception){
            Log.d("ENNEAGRAM_USECASE",e.message?:"Error message was null")
        }

        return null

    }


    suspend fun sendAnswers(answersDto: List<EnneagramAnswersDto>): EnneagramTestResultDetailedDto? {
        try {
            Log.d("ENNEAGRAM_USECASE","api request is successfully")
            val result = enneagramApiService.saveAnswers(answersDto)

            if(result.isSuccessful){
                val resultData = result.body()
                Log.d("ENNEAGRAM_USECASE","$resultData")


                return  resultData
            }else{
                Log.d("ENNEAGRAM_USECASE","api request is unsuccessfully")

                val errorBody = result.errorBody()?.string()
                throw EnneagramApiException(errorBody ?: "error message was null")

            }

        }catch (e:Exception){
            Log.d("ENNEAGRAM_USECASE",e.message?:"Error message was null")
           return null


        }

    }


}






class EnneagramApiException(override val message:String):Exception(message)