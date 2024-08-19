package com.erdemserhat.harmonyhaven.data.api.fcm

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.erdemserhat.harmonyhaven.MainActivity
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.data.local.repository.NotificationRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
@AndroidEntryPoint
class HarmonyHavenFirebaseMessagingService : FirebaseMessagingService() {

    // Context provided by Hilt dependency injection
    @ApplicationContext
    @Inject
    lateinit var context: Context

    // Notification repository injected to handle notification data storage
    @Inject
    lateinit var notificationRepository: NotificationRepository


    /**
     * Called when a message is received from Firebase Cloud Messaging.
     * Handles the creation and display of notifications based on the message data.
     */
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("FCM", "Message data payload: ${message.data}")

        // Extracting data from the FCM message payload
        val title = message.data["title"] ?: "Default Title"
        val body = message.data["body"] ?: "Default Body"
        val image = message.data["image"] ?: ""
        val screen = message.data["screen"] ?: ""

        // Storing the notification in the local database using the repository (Disabled)




        // Get the system's NotificationManager to handle notification creation

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "my_channel_id"
        val channelName = "My Channel"

        // Create a notification channel for devices running Android Oreo (API 26) or higher

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Determine the style of the notification based on whether an image is provided
        val bigStyle = if (image.isNotEmpty()) {
            val bitmap = getBitmapFromUrl(image)
            NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)

        } else {
            NotificationCompat.BigTextStyle()
                .bigText(body)
        }

        // Intent to launch the main activity when the notification is tapped
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("data", screen)
        }

        // PendingIntent to wrap the intent, ensuring it only opens the specified activity
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Building the notification with all the specified properties
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.harmony_haven_icon)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setStyle(bigStyle)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        // Generate a unique notification ID and display the notification
        val notificationId = (Math.random() * 1000).toInt()
        notificationManager.notify(notificationId, notificationBuilder.build())
    }


    /**
     * Loads a bitmap image from a URL.
     *
     * @param urlString The URL of the image to be loaded.
     * @return The bitmap image, or null if there was an error.
     */
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


