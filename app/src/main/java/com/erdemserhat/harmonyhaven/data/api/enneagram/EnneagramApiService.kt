package com.erdemserhat.harmonyhaven.data.api.enneagram

import android.os.Parcelable
import com.erdemserhat.harmonyhaven.domain.model.rest.Comment
import com.erdemserhat.harmonyhaven.dto.requests.FcmSetupRequest
import kotlinx.parcelize.Parcelize
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
    val fullDescriptionCode: Int,
    val famousPeople: List<EnneagramFamousPeople>,
    val chartUrl:EnneagramUrl

)

@Serializable
data class EnneagramScore(
    val type: Int = 0,
    var score: Int = 1
)

@Serializable
data class EnneagramWingTypes(
    val pointBasedWingType:Int,
    val enneagramBasedWingType:Int,
)


@Serializable
data class EnneagramTestResult(
    val typeScores: List<EnneagramScore>,
    val dominantType: EnneagramScore,
    val wingType: EnneagramWingTypes,
)

@Serializable
@Parcelize
data class EnneagramFamousPeople(
    val name: String,
    val imageUrl: String,
    val desc: String
) : Parcelable

@Serializable
data class CheckingTestResultDto(
    val detailedResult: EnneagramTestResultDetailedDto?,
    val isTestTakenBefore: Boolean
)

@Serializable
data class EnneagramUrl(
    val chartUrl:String,
    val personalityImageUrl:String,

    )


