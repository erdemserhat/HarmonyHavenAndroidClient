package com.erdemserhat.harmonyhaven.data.api

import com.erdemserhat.harmonyhaven.BuildConfig
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


import javax.inject.Inject


class SSEClient @Inject constructor() {

    @Inject
    lateinit var client: OkHttpClient
    
    private var activeCall: Call? = null

    suspend fun connectToSSE(
        prompt: String, 
        onMessageReceived: (String) -> Unit, 
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        val url = "${BuildConfig.SERVER_URL}/v1/chat/$prompt"

        // Bu URL ile SSE bağlantısı kuruyoruz
        val request = Request.Builder().url(url).build()

        // Bir HTTP isteği üzerinden veri akışını sürekli olarak dinlemek için
        activeCall = client.newCall(request).apply {
            enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        // Gelen veri akışını sürekli olarak oku
                        response.body?.let { body ->
                            try {
                                val reader = body.charStream()
                                reader.forEachLine { line ->
                                    onMessageReceived(line)  // Sunucudan gelen mesajı al
                                }
                                // Stream tamamlandığında onComplete çağrılır
                                onComplete()
                            } catch (e: Exception) {
                                onError(e)
                                onComplete()
                            } finally {
                                response.close()
                            }
                        } ?: run {
                            onError(Throwable("Empty response body"))
                            onComplete()
                        }
                    } else {
                        onError(Throwable("SSE connection failed with code: ${response.code}"))
                        onComplete()
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    onError(e)  // Hata durumunda çağrılır
                    onComplete()
                }
            })
        }
    }

    fun close() {
        activeCall?.cancel()
        client.dispatcher.executorService.shutdown()
    }
}
