package com.erdemserhat.harmonyhaven.data.api.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.erdemserhat.harmonyhaven.MainActivity
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity
import com.erdemserhat.harmonyhaven.data.local.repository.NotificationRepository
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection
import java.net.URL
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
        // Token güncelleme işlemleri burada yapılabilir
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("FCM", "Message data payload: ${message.data}")

        val title = message.data["title"] ?: "Default Title"
        val body = message.data["body"] ?: "Default Body"
        val image = message.data["image"] ?: ""
        val screen = message.data["screen"] ?: ""

        runBlocking {
            notificationRepository.insert(
                NotificationEntity(
                    title = title,
                    body = body,
                    date = System.currentTimeMillis()
                )
            )
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "my_channel_id"
        val channelName = "My Channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val bigStyle = if (image.isNotEmpty()) {
            val bitmap = getBitmapFromUrl(image)
            NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)

        } else {
            NotificationCompat.BigTextStyle()
                .bigText(body)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("data", screen)  // Hedef ekran için ekstra bilgi
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.harmony_haven_icon)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setStyle(bigStyle) // Resimli bildirim stilini ayarla
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Bildirim görünürlüğünü ayarla

        val notificationId = (Math.random() * 1000).toInt()
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun getBitmapFromUrl(urlString: String): Bitmap? {
        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.apply {
                doInput = true
                connect()
            }
            BitmapFactory.decodeStream(connection.inputStream)
        } catch (e: Exception) {
            Log.e("FCM", "Error loading image", e)
            null
        }
    }
}


