package com.erdemserhat.harmonyhaven.domain.usecase.users

import com.erdemserhat.harmonyhaven.data.network.UserApiService
import com.erdemserhat.harmonyhaven.domain.model.ForgotPasswordAuthClientModel
import com.erdemserhat.harmonyhaven.domain.model.ForgotPasswordMailerClientModel
import com.erdemserhat.harmonyhaven.domain.model.ForgotPasswordResetClientModel
import com.erdemserhat.harmonyhaven.domain.model.RequestResultClient
import com.erdemserhat.harmonyhaven.domain.model.RequestResultUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * This class represents the user reset password progress validation progress
 * @param userApiService: Hilt is the provider of this class, this class handles the
 * remote database operation of corresponding endpoint
 */
class ResetPasswordUserDev @Inject constructor(
    private val userApiService: UserApiService
) {

    /**
     * This function is the last connection point of send mail service you should use this
     * service in your view-models
     */

    suspend fun sendMail(email: String): Flow<RequestResultClient> = flow {
        //Whenever request is started this emit will be given the corresponding flow...

        emit(
            RequestResultClient(
                result = false,
                message = "Loading...",
                isLoading = true,
            )
        )

        val response =
            userApiService.requestResetPasswordMail(ForgotPasswordMailerClientModel(email))

        val responseResult = response.body()?.result ?: false
        val responseMessage = response.body()?.message ?: "Internal Server Error...."

        emit(

            RequestResultClient(
                result = responseResult, message = responseMessage, isLoading = false
            )

        )
    }


    /**
     * To use this function you must firstly create a send mail session, without send mail session
     * this service is not functional
     */
    suspend fun authenticateRequest(authModel: ForgotPasswordAuthClientModel): Flow<RequestResultUUID> =
        flow {
            emit(
                RequestResultUUID(
                    result = false,
                    message = "Loading....",
                    isLoading = true
                )

            )

            val serverResponse = userApiService.requestResetPasswordAuth(authModel)

            val responseResult = serverResponse.body()?.result ?: false
            val responseMessage = serverResponse.body()?.message ?: "Internal server error"
            val responseUUID= serverResponse.body()?.uuid ?:"N/A"

            emit(
                RequestResultUUID(
                    result = responseResult,
                    message = responseMessage,
                    isLoading = false,
                    uuid = responseUUID


                )
            )

        }


    /**
     * This service is the last layer of password reset process after the this step your
     * session will expire there are some conditions
     */
    suspend fun resetPassword(resetModel: ForgotPasswordResetClientModel): Flow<RequestResultClient> =
        flow {
            emit(
                RequestResultClient(
                    result = false,
                    message = "Loading....",
                    isLoading = true
                )
            )

            val serverResponse = userApiService.requestPasswordReset(resetModel)

            val responseMessage = serverResponse.body()?.message ?:  "Internal Server Error...."
            val responseResult = serverResponse.body()?.result ?: false

            emit(
                RequestResultClient(
                    result = responseResult,
                    message = responseMessage,
                    isLoading = false
                )

            )
        }

}