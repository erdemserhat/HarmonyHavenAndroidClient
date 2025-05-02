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
    ): Response<EnneagramTestResultDetailedDto>


    @GET("v1/enneagram/check-test-status")
    suspend fun checkTestResult(
    ): Response<CheckingTestResultDto>


}


@Serializable
data class EnneagramQuestionDto(
    val id: String,
    val personalityNumber: Int,
    val content: String
)

@Serializable
data class EnneagramAnswersDto(
    val questionId:String,
    val score:Int,
){
    init {
        require(score in 0..3) { "Score must be between 0 and 3. Given: $score" }
    }
}

@Serializable
data class EnneagramTestResultDetailedDto(
    val result: EnneagramTestResult,
    val description: String,
    val famousPeople: List<EnneagramFamousPeople>,
    val chartUrl:String

)

@Serializable
data class EnneagramScore(
    val type: Int = 0,
    var score: Int = 1
)

@Serializable
data class EnneagramTestResult(
    val typeScores: List<EnneagramScore>,
    val dominantType: EnneagramScore,
    val wingType: EnneagramScore,
)

@Serializable
data class EnneagramFamousPeople(
    val name: String,
    val imageUrl: String,
    val desc: String
)

@Serializable
data class CheckingTestResultDto(
    val detailedResult: EnneagramTestResultDetailedDto?,
    val isTestTakenBefore: Boolean
)

