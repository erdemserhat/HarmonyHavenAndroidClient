package com.erdemserhat.harmonyhaven.presentation.post_authentication.player

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.erdemserhat.harmonyhaven.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MediaPlayerService : Service() {
    
    private val binder = MediaPlayerBinder()
    private var mediaPlayer: MediaPlayer? = null
    private var mediaSession: MediaSessionCompat? = null
    private var currentMusic: MeditationMusic? = null
    private var isPlaying = false
    private var progressUpdateJob: Job? = null
    private val serviceScope = CoroutineScope(Dispatchers.Main)
    private var isPreparing = false // Flag to track if mediaPlayer is currently preparing
    
    // Timer related properties
    private var timerJob: Job? = null
    private var remainingTimeSeconds: Long? = null
    private var isTimerActive: Boolean = false
    
    // Callback for client to receive updates
    var onProgressChanged: ((Float) -> Unit)? = null
    var onPlayStateChanged: ((Boolean) -> Unit)? = null
    var onErrorOccurred: ((String) -> Unit)? = null
    var onLoadingStateChanged: ((Boolean) -> Unit)? = null // New callback for loading state
    var onTimerChanged: ((Long?) -> Unit)? = null // Timer remaining time callback
    var onTimerActiveChanged: ((Boolean) -> Unit)? = null // Timer active state callback
    
    companion object {
        private const val CHANNEL_ID = "meditation_player_channel"
        private const val NOTIFICATION_ID = 101
        private const val ACTION_PLAY_PAUSE = "com.erdemserhat.harmonyhaven.PLAY_PAUSE"
        private const val ACTION_STOP = "com.erdemserhat.harmonyhaven.STOP"
        private const val ACTION_PREVIOUS = "com.erdemserhat.harmonyhaven.PREVIOUS"
        private const val ACTION_NEXT = "com.erdemserhat.harmonyhaven.NEXT"
        private const val TAG = "MediaPlayerService"
    }
    
    inner class MediaPlayerBinder : Binder() {
        fun getService(): MediaPlayerService = this@MediaPlayerService
    }
    
    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind called")
        return binder
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: Service created")
        createNotificationChannel()
        setupMediaSession()
        
        // Start as foreground service immediately with basic notification
        startForeground(NOTIFICATION_ID, createBasicNotification())
    }
    
    private fun setupMediaSession() {
        Log.d(TAG, "Setting up media session")
        mediaSession = MediaSessionCompat(this, "MeditationPlayerSession").apply {
            setCallback(object : MediaSessionCompat.Callback() {
                override fun onPlay() {
                    play()
                }
                
                override fun onPause() {
                    pause()
                }
                
                override fun onStop() {
                    stopSelf()
                }
                
                override fun onSeekTo(pos: Long) {
                    mediaPlayer?.seekTo(pos.toInt())
                    updateNotification()
                }
            })
            
            setPlaybackState(
                PlaybackStateCompat.Builder()
                    .setActions(
                        PlaybackStateCompat.ACTION_PLAY or
                                PlaybackStateCompat.ACTION_PAUSE or
                                PlaybackStateCompat.ACTION_STOP or
                                PlaybackStateCompat.ACTION_SEEK_TO
                    )
                    .build()
            )
            
            isActive = true
        }
    }
    
    fun initializePlayer(music: MeditationMusic) {
        Log.d(TAG, "initializePlayer: Initializing player with music: ${music.title}")
        
        // Notify that loading has started
        isPreparing = true
        onLoadingStateChanged?.invoke(true)
        
        // Eğer hazırlanma işlemi devam ediyorsa işlemi iptal et
        if (isPreparing && mediaPlayer != null) {
            Log.d(TAG, "Previous preparation in progress, releasing old player first")
            releaseMediaPlayer()
        }
        
        // If the same music is already loaded and playing, do nothing
        if (currentMusic?.id == music.id && isPlaying && mediaPlayer != null && mediaPlayer?.isPlaying == true) {
            Log.d(TAG, "Same music already playing, do nothing")
            isPreparing = false
            onLoadingStateChanged?.invoke(false)
            return
        }
        
        // If the same music is already loaded but paused, just play it
        if (currentMusic?.id == music.id && !isPlaying && mediaPlayer != null) {
            Log.d(TAG, "Same music loaded but paused, resume playing")
            play()
            isPreparing = false
            onLoadingStateChanged?.invoke(false)
            return
        }
        
        // Stop any currently playing music first
        stopProgressTracking()
        
        // Always pause before resetting or releasing
        mediaPlayer?.let {
            if (it.isPlaying) {
                try {
                    it.pause()
                } catch (e: Exception) {
                    Log.e(TAG, "Error pausing before release", e)
                }
            }
        }
        
        // Setting current music needs to happen before we release the current player
        // so that we retain the info for the notification
        currentMusic = music
        
        // Release the existing player
        releaseMediaPlayer()
        
        // Create a new MediaPlayer
        try {
            Log.d(TAG, "Creating new MediaPlayer instance")
            mediaPlayer = MediaPlayer().apply {
                setOnErrorListener { mp, what, extra ->
                    Log.e(TAG, "MediaPlayer error: what=$what, extra=$extra")
                    isPreparing = false
                    onErrorOccurred?.invoke("Media player error: $what")
                     this@MediaPlayerService.isPlaying = false
                    onPlayStateChanged?.invoke(false)
                    onLoadingStateChanged?.invoke(false)
                    true
                }
                
                Log.d(TAG, "Setting data source: ${music.audioUrl}")
                setDataSource(music.audioUrl)
                
                setOnPreparedListener {
                    Log.d(TAG, "Media player prepared successfully")
                    isPreparing = false
                    updateNotification()
                    
                    // Notify that loading is finished
                    onLoadingStateChanged?.invoke(false)
                    
                    // Start playing automatically after prepared
                    this@MediaPlayerService.isPlaying = true
                    start()
                    startProgressTracking()
                    onPlayStateChanged?.invoke(true)
                    Log.d(TAG, "Auto-started playback after preparation")
                }
                
                setOnCompletionListener {
                    Log.d(TAG, "Media playback completed")
                    // Update service state
                    this@MediaPlayerService.isPlaying = false
                    stopProgressTracking()
                    
                    // Reset to beginning
                    try {
                        seekTo(0)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error seeking to start after completion", e)
                    }
                    
                    // Notify UI about state changes
                    onPlayStateChanged?.invoke(false)
                    updateNotification()
                    
                    Log.d(TAG, "Track completed - reset to beginning and paused")
                }
                
                // Start preparing asynchronously
                Log.d(TAG, "Calling prepareAsync")
                prepareAsync()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing media player", e)
            isPreparing = false
            onErrorOccurred?.invoke("Hata: ${e.message}")
            isPlaying = false
            onPlayStateChanged?.invoke(false)
            onLoadingStateChanged?.invoke(false)
        }
    }
    
    private fun releaseMediaPlayer() {
        Log.d(TAG, "Releasing media player")
        try {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                }
                reset()
                release()
            }
            mediaPlayer = null
        } catch (e: Exception) {
            Log.e(TAG, "Error releasing media player", e)
        }
    }
    
    fun play() {
        Log.d(TAG, "play: Attempt to play")
        
        // If we're still preparing, set isPlaying flag but actual playback will 
        // start in the onPrepared callback
        if (isPreparing) {
            Log.d(TAG, "Still preparing, will play when ready")
            isPlaying = true
            onPlayStateChanged?.invoke(true)
            return
        }
        
        mediaPlayer?.let { player ->
            try {
                // Check if we're at the end of the track (completion state)
                val currentPos = player.currentPosition
                val duration = player.duration
                
                if (currentPos >= duration - 100 && duration > 0) {
                    // We're at the end, seek to beginning first
                    Log.d(TAG, "At end of track, seeking to beginning")
                    player.seekTo(0)
                }
                
                if (!player.isPlaying) {
                    Log.d(TAG, "Starting playback")
                    player.start()
                    isPlaying = true
                    updateNotification() // Just update notification, don't start foreground again
                    startProgressTracking()
                    onPlayStateChanged?.invoke(true)
                    Log.d(TAG, "Playback started successfully")
                } else {
                    Log.d(TAG, "Already playing")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error starting playback", e)
                isPlaying = false
                onErrorOccurred?.invoke("Çalma hatası: ${e.message}")
                onPlayStateChanged?.invoke(false)
            }
        } ?: run {
            Log.e(TAG, "MediaPlayer is null, can't play")
            isPlaying = false
            onPlayStateChanged?.invoke(false)
            
            // Eğer MediaPlayer null ise, muhtemelen hazırlama başarısız olmuştur.
            // Bu durumda tekrar başlatmayı deneyelim
            currentMusic?.let { 
                Log.d(TAG, "Attempting to reinitialize player")
                initializePlayer(it) 
            }
        }
    }
    
    fun pause() {
        Log.d(TAG, "pause: Attempt to pause")
        
        if (isPreparing) {
            Log.d(TAG, "Still preparing, canceling pending play")
            isPlaying = false
            onPlayStateChanged?.invoke(false)
            return
        }
        
        mediaPlayer?.let {
            if (it.isPlaying) {
                try {
                    it.pause()
                    isPlaying = false
                    stopProgressTracking()
                    updateNotification()
                    onPlayStateChanged?.invoke(false)
                    Log.d(TAG, "Playback paused successfully")
                } catch (e: Exception) {
                    Log.e(TAG, "Error pausing playback", e)
                }
            } else {
                Log.d(TAG, "Already paused")
            }
        } ?: run {
            Log.d(TAG, "MediaPlayer is null, can't pause")
        }
    }
    
    fun togglePlayPause() {
        Log.d(TAG, "togglePlayPause: isPlaying=$isPlaying")
        if (isPlaying) {
            pause()
        } else {
            play()
        }
    }
    
    fun seekTo(position: Int) {
        if (isPreparing) {
            Log.d(TAG, "Still preparing, can't seek")
            return
        }
        
        try {
            Log.d(TAG, "Seeking to position $position")
            mediaPlayer?.seekTo(position)
            updateNotification()
        } catch (e: Exception) {
            Log.e(TAG, "Error seeking", e)
        }
    }
    
    fun getDuration(): Int {
        return try {
            val duration = mediaPlayer?.duration ?: 0
            Log.v(TAG, "getDuration: $duration")
            duration
        } catch (e: Exception) {
            Log.e(TAG, "Error getting duration", e)
            0
        }
    }
    
    fun getCurrentPosition(): Int {
        return try {
            val position = mediaPlayer?.currentPosition ?: 0
            // Log at verbose level to avoid too much noise
            Log.v(TAG, "getCurrentPosition: $position")
            position
        } catch (e: Exception) {
            Log.e(TAG, "Error getting position", e)
            0
        }
    }
    
    private fun startProgressTracking() {
        stopProgressTracking()
        
        progressUpdateJob = serviceScope.launch {
            Log.d(TAG, "Starting progress tracking")
            try {
                while (true) {
                    if (mediaPlayer == null) {
                        Log.d(TAG, "Progress tracking stopped - mediaPlayer is null")
                        break
                    }
                    
                    val duration = mediaPlayer?.duration ?: 0
                    if (duration > 0) {
                        val currentPosition = mediaPlayer?.currentPosition ?: 0
                        val progress = currentPosition.toFloat() / duration.toFloat()
                        onProgressChanged?.invoke(progress)
                    }
                    delay(100) // Update every 100ms
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in progress tracking", e)
            }
        }
    }
    
    private fun stopProgressTracking() {
        Log.d(TAG, "Stopping progress tracking")
        progressUpdateJob?.cancel()
        progressUpdateJob = null
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "Creating notification channel")
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Meditation Player",
                NotificationManager.IMPORTANCE_LOW // LOW importance prevents sound/vibration
            ).apply {
                description = "Controls for the meditation player"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        Log.v(TAG, "Creating notification")
        val music = currentMusic ?: return createBasicNotification()
        
        // Create intent for when notification is tapped
        val packageName = applicationContext.packageName
        val launchIntent = packageName?.let { 
            applicationContext.packageManager.getLaunchIntentForPackage(it)
        }
        val pendingContentIntent = PendingIntent.getActivity(
            this, 0, launchIntent, 
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Play/Pause action
        val playPauseIntent = Intent(this, MediaPlayerService::class.java).apply {
            action = ACTION_PLAY_PAUSE
        }
        val pendingPlayPauseIntent = PendingIntent.getService(
            this, 0, playPauseIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Previous track action
        val prevIntent = Intent(this, MediaPlayerService::class.java).apply {
            action = ACTION_PREVIOUS
        }
        val pendingPrevIntent = PendingIntent.getService(
            this, 1, prevIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Next track action
        val nextIntent = Intent(this, MediaPlayerService::class.java).apply {
            action = ACTION_NEXT
        }
        val pendingNextIntent = PendingIntent.getService(
            this, 2, nextIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Stop action
        val stopIntent = Intent(this, MediaPlayerService::class.java).apply {
            action = ACTION_STOP
        }
        val pendingStopIntent = PendingIntent.getService(
            this, 3, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Create content text with timer info
        val contentText = if (isTimerActive && remainingTimeSeconds != null) {
            val hours = remainingTimeSeconds!! / 3600
            val minutes = (remainingTimeSeconds!! % 3600) / 60
            val timerText = if (hours > 0) {
                "${hours}h ${minutes}m kaldı"
            } else {
                "${minutes}m kaldı"
            }
            "${music.artist} • $timerText"
        } else {
            music.artist
        }
        
        // Build the notification with smaller icons for controls
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(music.title)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.objects) // Uygulama logosu
            .setContentIntent(pendingContentIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true) // Make notification persistent
            
        // Önceki parça butonu
        builder.addAction(
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_media_previous,
                "Previous",
                pendingPrevIntent
            ).build()
        )
            
        // Oynat/Duraklat butonu
        builder.addAction(
            NotificationCompat.Action.Builder(
                if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play,
                if (isPlaying) "Pause" else "Play",
                pendingPlayPauseIntent
            ).build()
        )
        
        // Sonraki parça butonu
        builder.addAction(
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_media_next,
                "Next",
                pendingNextIntent
            ).build()
        )
        
        // Durdur butonu
        builder.addAction(
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_menu_close_clear_cancel,
                "Stop",
                pendingStopIntent
            ).build()
        )
        
        // Add media style and center the controls by showing all 4 actions
        builder.setStyle(
            androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession?.sessionToken)
                .setShowActionsInCompactView(0, 1, 2) // 0, 1, 2 indekslerindeki butonları kompakt görünümde göster
        )
        
        // Load cover art asynchronously and update notification
        loadCoverArtForNotification(music.imageUrl)
        
        return builder.build()
    }
    
    private fun createBasicNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Harmony Haven")
            .setContentText("Müzik çalar hazır")
            .setSmallIcon(R.drawable.objects)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }
    
    private fun loadCoverArtForNotification(imageUrl: String) {
        serviceScope.launch {
            try {
                val imageLoader = ImageLoader(this@MediaPlayerService)
                val request = ImageRequest.Builder(this@MediaPlayerService)
                    .data(imageUrl)
                    .build()
                
                val result = imageLoader.execute(request)
                if (result is SuccessResult) {
                    val bitmap = (result.drawable as? BitmapDrawable)?.bitmap
                    bitmap?.let {
                        // Resize bitmap for notification (recommend max 256x256)
                        val resizedBitmap = Bitmap.createScaledBitmap(it, 256, 256, true)
                        
                        // Update notification with cover art
                        withContext(Dispatchers.Main) {
                            updateNotificationWithCoverArt(resizedBitmap)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading cover art for notification", e)
                // Notification will show without cover art, which is fine
            }
        }
    }
    
    private fun updateNotificationWithCoverArt(coverArt: Bitmap) {
        val music = currentMusic ?: return
        
        // Create intent for when notification is tapped
        val packageName = applicationContext.packageName
        val launchIntent = packageName?.let { 
            applicationContext.packageManager.getLaunchIntentForPackage(it)
        }
        val pendingContentIntent = PendingIntent.getActivity(
            this, 0, launchIntent, 
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Play/Pause action
        val playPauseIntent = Intent(this, MediaPlayerService::class.java).apply {
            action = ACTION_PLAY_PAUSE
        }
        val pendingPlayPauseIntent = PendingIntent.getService(
            this, 0, playPauseIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Previous track action
        val prevIntent = Intent(this, MediaPlayerService::class.java).apply {
            action = ACTION_PREVIOUS
        }
        val pendingPrevIntent = PendingIntent.getService(
            this, 1, prevIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Next track action
        val nextIntent = Intent(this, MediaPlayerService::class.java).apply {
            action = ACTION_NEXT
        }
        val pendingNextIntent = PendingIntent.getService(
            this, 2, nextIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Stop action
        val stopIntent = Intent(this, MediaPlayerService::class.java).apply {
            action = ACTION_STOP
        }
        val pendingStopIntent = PendingIntent.getService(
            this, 3, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Create content text with timer info
        val contentText = if (isTimerActive && remainingTimeSeconds != null) {
            val hours = remainingTimeSeconds!! / 3600
            val minutes = (remainingTimeSeconds!! % 3600) / 60
            val timerText = if (hours > 0) {
                "${hours}h ${minutes}m kaldı"
            } else {
                "${minutes}m kaldı"
            }
            "${music.artist} • $timerText"
        } else {
            music.artist
        }
        
        // Build the notification with cover art
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(music.title)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.objects)
            .setLargeIcon(coverArt) // Set the cover art as large icon
            .setContentIntent(pendingContentIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true) // Make notification persistent
            
        // Önceki parça butonu
        builder.addAction(
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_media_previous,
                "Previous",
                pendingPrevIntent
            ).build()
        )
            
        // Oynat/Duraklat butonu
        builder.addAction(
            NotificationCompat.Action.Builder(
                if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play,
                if (isPlaying) "Pause" else "Play",
                pendingPlayPauseIntent
            ).build()
        )
        
        // Sonraki parça butonu
        builder.addAction(
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_media_next,
                "Next",
                pendingNextIntent
            ).build()
        )
        
        // Durdur butonu
        builder.addAction(
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_menu_close_clear_cancel,
                "Stop",
                pendingStopIntent
            ).build()
        )
        
        // Add media style
        builder.setStyle(
            androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession?.sessionToken)
                .setShowActionsInCompactView(0, 1, 2)
        )
        
        // Update notification
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build())
        }
    }
    
    private fun updateNotification() {
        Log.v(TAG, "Updating notification")
        // Always update notification since we're a foreground service
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, createNotification())
        }
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ${intent?.action}")
        intent?.let {
            when (it.action) {
                ACTION_PLAY_PAUSE -> togglePlayPause()
                ACTION_STOP -> {
                    stopTimer()
                    stopSelf()
                }
                ACTION_PREVIOUS -> {
                    // Önceki parçaya geçme işlevi buraya eklenebilir
                    // Şimdilik başa sarıyoruz
                    mediaPlayer?.seekTo(0)
                }
                ACTION_NEXT -> {
                    // Sonraki parçaya geçme işlevi buraya eklenebilir
                    // Şimdilik ileri sarmak için kullanıyoruz
                    val currentPosition = mediaPlayer?.currentPosition ?: 0
                    val duration = mediaPlayer?.duration ?: 0
                    val newPosition = (currentPosition + 30000).coerceAtMost(duration)
                    mediaPlayer?.seekTo(newPosition)
                }
 
                else -> {}
            }
        }
        
        return START_STICKY // Service will be restarted if killed by system
    }
    
    override fun onDestroy() {
        Log.d(TAG, "Service being destroyed")
        stopProgressTracking()
        stopTimer()
        releaseMediaPlayer()
        mediaSession?.release()
        mediaSession = null
        super.onDestroy()
    }
    
    // Timer Functions
    fun startTimer(hours: Int, minutes: Int) {
        val totalSeconds = (hours * 3600 + minutes * 60).toLong()
        if (totalSeconds <= 0) return
        
        Log.d(TAG, "Starting timer: ${hours}h ${minutes}m ($totalSeconds seconds)")
        
        stopTimer() // Stop any existing timer
        
        remainingTimeSeconds = totalSeconds
        isTimerActive = true
        
        // Notify UI
        onTimerActiveChanged?.invoke(true)
        onTimerChanged?.invoke(remainingTimeSeconds)
        
        // Update notification with timer info
        updateNotification()
        
        // Start countdown
        timerJob = serviceScope.launch {
            while (remainingTimeSeconds != null && remainingTimeSeconds!! > 0 && isTimerActive) {
                delay(1000) // Wait 1 second
                remainingTimeSeconds = remainingTimeSeconds!! - 1
                
                // Update UI
                onTimerChanged?.invoke(remainingTimeSeconds)
                
                // Update notification every 30 seconds to avoid too frequent updates
                if (remainingTimeSeconds!! % 30 == 0L) {
                    updateNotification()
                }
                
                // Check if timer finished
                if (remainingTimeSeconds == 0L) {
                    Log.d(TAG, "Timer finished - stopping music")
                    pause() // Stop the music
                    stopTimer()
                    break
                }
            }
        }
    }
    
    fun stopTimer() {
        Log.d(TAG, "Stopping timer")
        timerJob?.cancel()
        timerJob = null
        remainingTimeSeconds = null
        isTimerActive = false
        
        // Notify UI
        onTimerActiveChanged?.invoke(false)
        onTimerChanged?.invoke(null)
        
        // Update notification to remove timer info
        updateNotification()
    }
    
    fun getTimerRemainingTime(): Long? = remainingTimeSeconds
    fun isTimerActive(): Boolean = isTimerActive
} 