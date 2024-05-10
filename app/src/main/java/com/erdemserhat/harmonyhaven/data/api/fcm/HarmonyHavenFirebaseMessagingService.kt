package com.erdemserhat.harmonyhaven.data.api.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.erdemserhat.harmonyhaven.MainActivity
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity
import com.erdemserhat.harmonyhaven.data.local.repository.NotificationRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class HarmonyHavenFirebaseMessagingService() : FirebaseMessagingService() {
    @ApplicationContext
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var notificationRepository: NotificationRepository


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("erde11", token)

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data
        val title = data["specializedTitle"] ?: "Varsayılan Başlık"
        val body = data["specializedBody"] ?: "Varsayılan İçerik"


        runBlocking {
            notificationRepository.insert(
                NotificationEntity(
                    title = title,
                    body = body,
                    date = System.currentTimeMillis()
                )
            )
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "my_channel_id"
        val channelName = "My Channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        // Expandable bildirim oluştur
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(body) // Büyük içerik,

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            this,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )


        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.harmony_haven_icon)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setStyle(bigTextStyle) // Expandable bildirim stilini ayarla
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Bildirim görünürlüğünü ayarla

        val notificationId = (Math.random() * 1000).toInt()
        notificationManager.notify(notificationId, notificationBuilder.build())


    }


}


