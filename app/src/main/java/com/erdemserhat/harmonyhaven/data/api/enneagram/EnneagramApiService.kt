package com.erdemserhat.harmonyhaven.data.api.enneagram

import com.erdemserhat.harmonyhaven.domain.model.rest.Comment
import com.erdemserhat.harmonyhaven.dto.requests.FcmSetupRequest
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EnneagramApiService {

    @GET("v1/enneagram/questions")
    suspend fun getQuestions(
    ): Response<List<EnneagramQuestionDto>>

    @POST("v1/enneagram/answers")
    suspend fun saveAnswers(
        @Body answers: List<EnneagramAnswersDto> // The request body containing the FCM token details
    ): Response<Void>


}


@Serializable
data class EnneagramQuestionDto(
    val id: Int,
    val personalityNumber: Int,
    val content: String
)

@Serializable
data class EnneagramAnswersDto(
    val questionId:Int,
    val score:Int,
){
    init {
        require(score in 0..3) { "Score must be between 0 and 3. Given: $score" }
    }
}
