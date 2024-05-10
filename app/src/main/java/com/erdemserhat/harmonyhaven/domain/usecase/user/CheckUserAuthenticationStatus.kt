package com.erdemserhat.harmonyhaven.domain.usecase.user

import android.util.Log
import com.erdemserhat.harmonyhaven.data.api.user.AuthenticationStatusCheckerApiService
import com.erdemserhat.harmonyhaven.dto.requests.UserAuthenticationRequest
import com.erdemserhat.harmonyhaven.dto.responses.AuthenticationResponse
import com.google.gson.Gson
import kotlinx.coroutines.time.withTimeoutOrNull
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class CheckUserAuthenticationStatus @Inject constructor(
    private val checker: AuthenticationStatusCheckerApiService
) {
    suspend fun executeRequest(): Int {
        return withTimeoutOrNull(3000) {
            try {
                val response = checker.checkAuthenticationStatus()
                if (response.isSuccessful) {
                    1 // Başarılı yanıt
                } else {
                    2 // Yetkisiz yanıt
                }
            } catch (e: Exception) {
                0 // İstek hatası
            }
        } ?: 3 // Timeout durumu
    }
}