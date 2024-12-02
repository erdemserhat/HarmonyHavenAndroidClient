package com.erdemserhat.harmonyhaven.di.network

import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor for adding a JWT token to outgoing HTTP requests.
 *
 * This class implements the [Interceptor] interface and is used to add a JWT (JSON Web Token) to the
 * `Authorization` header of HTTP requests. The token is retrieved from the [JwtTokenRepository].
 * This interceptor ensures that all requests made through the [OkHttpClient] have the necessary
 * authentication token included in the headers.
 *
 * @property jwtTokenRepository The repository for accessing the JWT token.
 */
class JwtInterceptor @Inject constructor(
    private val jwtTokenRepository: JwtTokenRepository
) : Interceptor {

    /**
     * Intercepts an HTTP request to add the JWT token to the `Authorization` header.
     *
     * This method retrieves the JWT token from the [JwtTokenRepository] in a coroutine context,
     * adds the token to the `Authorization` header of the request, and then proceeds with the request.
     *
     * @param chain The [Interceptor.Chain] providing the request and response.
     * @return The [Response] from the network call after adding the JWT token to the request.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            // Retrieve the JWT token in a coroutine context to avoid blocking the main thread
            val jwtToken = withContext(Dispatchers.IO) {
                jwtTokenRepository.getJwtToken()
            }

            // Build the new request with the JWT token added to the Authorization header
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $jwtToken")
                .addHeader("harmonyhavenapikey", BuildConfig.API_KEY)
                .build()

            // Proceed with the request and return the response
            chain.proceed(request)
        }
    }
}
