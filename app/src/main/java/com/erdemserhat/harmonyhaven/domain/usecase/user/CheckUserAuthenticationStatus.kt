package com.erdemserhat.harmonyhaven.domain.usecase.user

import com.erdemserhat.harmonyhaven.data.api.user.AuthenticationStatusCheckerApiService
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