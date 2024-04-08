package com.erdemserhat.harmonyhaven.di

import com.erdemserhat.harmonyhaven.data.room.JwtTokenRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class JwtInterceptor @Inject constructor(
    private val jwtTokenRepository: JwtTokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val jwtToken = jwtTokenRepository.getJwtToken()
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $jwtToken")
            .build()
        return chain.proceed(request)
    }
}