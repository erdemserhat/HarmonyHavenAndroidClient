package com.erdemserhat.harmonyhaven.data.network

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@AndroidEntryPoint
class HarmonyHavenFirebaseMessagingService():FirebaseMessagingService() {
    @ApplicationContext
    @Inject
    lateinit var context: Context
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("erde11",token)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // UI thread üzerinde çalıştırmak için Handler kullanarak gösterim yapılır
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, "Bildirim alındı", Toast.LENGTH_SHORT).show()
        }


    }
}
