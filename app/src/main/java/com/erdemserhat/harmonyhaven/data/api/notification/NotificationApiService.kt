package com.erdemserhat.harmonyhaven.data.api.notification

import android.os.Build
import androidx.annotation.RequiresApi
import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

interface NotificationApiService {

    /**
     * Fetches a paginated list of notifications for the user.
     *
     * @param page The page number to retrieve (starting from 0).
     * @param size The number of notifications to retrieve per page.
     * @return A [Response] containing a list of [NotificationDto] objects.
     */
    @GET("v1/user/get-notifications")
    suspend fun getNotifications(
        @Query("page") page: Int,  // The page number of notifications to retrieve.
        @Query("size") size: Int   // The number of notifications per page.
    ): Response<List<NotificationDto>>


    //schedulers
    @GET("v1/notification/schedulers")
    suspend fun getSchedulers(
    ): Response<List<NotificationSchedulerDto>>

    @DELETE("v1/schedule-notification/{id}")
    suspend fun deleteScheduler(
        @Path("id") id: String
    ): Response<Void>


    @POST("v1/schedule-notification")
    suspend fun schedule(
        @Body scheduler:NotificationSchedulerDto
    ): Response<Void>





}


@Serializable
data class NotificationSchedulerDto(
    @Serializable(with = ObjectIdSerializer::class)
    val id: String? = null,
    val definedType: NotificationDefinedType,
    val type: NotificationType,
    val customSubject:String? = null,
    val predefinedMessageSubject: PredefinedMessageSubject? = null,
    val predefinedReminderSubject: PredefinedReminderSubject? = null,
    @Serializable(with = LocalTimeSerializer::class)
    val preferredTime: String,
    val daysOfWeek: List<DayOfWeek>
)


@Serializable
enum class PredefinedMessageSubject{
    GOOD_MORNING,
    GOOD_EVENING,
    MOTIVATION,
}
@Serializable
enum class PredefinedReminderSubject(
){
    WATER_DRINK,
    SLEEP_TIME,
    EXERCISE
}

@Serializable
enum class NotificationDefinedType{
    CUSTOM,
    DEFAULT
}

@Serializable
enum class NotificationType{
    REMINDER,
    MESSAGE
}

object LocalTimeSerializer : KSerializer<LocalTime> {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_LOCAL_TIME

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun serialize(encoder: Encoder, value: LocalTime) {
        encoder.encodeString(value.format(formatter))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(decoder: Decoder): LocalTime {
        return LocalTime.parse(decoder.decodeString(), formatter)
    }
}

@Serializer(forClass = String::class)
object ObjectIdSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ObjectId", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }

    override fun deserialize(decoder: Decoder): String {
        val value = decoder.decodeString()
        return value
    }
}


