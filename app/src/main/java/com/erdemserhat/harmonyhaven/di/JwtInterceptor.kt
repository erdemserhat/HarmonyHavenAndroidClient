package com.erdemserhat.harmonyhaven.di

import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class JwtInterceptor @Inject constructor(
    private val jwtTokenRepository: JwtTokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            // Arka planda çalışacak olan işlemi withContext içinde gerçekleştirin
            val jwtToken = withContext(Dispatchers.IO) {
                jwtTokenRepository.getJwtToken()
            }

            // JWT token'ı alınca isteği oluşturun ve devam edin
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $jwtToken")
                .build()

            chain.proceed(request)
        }
    }
}
