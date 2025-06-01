package com.erdemserhat.harmonyhaven.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import kotlinx.coroutines.delay

object NetworkUtils {
    @SuppressLint("ServiceCast")
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnected == true
    }

    suspend fun <T> retryWithBackoff(
        times: Int = 3,
        initialDelay: Long = 500,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                return block()
            } catch (e: Exception) {
                Log.w("Retry", "Hata oluştu, tekrar denenecek: ${e.message}")
                delay(currentDelay)
                currentDelay = (currentDelay * factor).toLong()
            }
        }
        return block() // son deneme
    }

}