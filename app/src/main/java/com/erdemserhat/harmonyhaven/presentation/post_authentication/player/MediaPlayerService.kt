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
import android.support.v4.media.MediaMetadataCompat
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
    
    // Repeat mode: 0 = no repeat, 1 = repeat all (loop), 2 = repeat one
    private var repeatMode: Int = 0
    
    // Callback for client to receive updates
    var onProgressChanged: ((Float) -> Unit)? = null
    var onPlayStateChanged: ((Boolean) -> Unit)? = null
    var onErrorOccurred: ((String) -> Unit)? = null
    var onLoadingStateChanged: ((Boolean) -> Unit)? = null // New callback for loading state
    var onTimerChanged: ((Long?) -> Unit)? = null // Timer remaining time callback
    var onTimerActiveChanged: ((Boolean) -> Unit)? = null // Timer active state callback
    var onRepeatModeChanged: ((Int) -> Unit)? = null
    
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
        Log.d(TAG, "onCreate: Service created for API level ${Build.VERSION.SDK_INT}")
        
        // Android 14+ için bildirim izni kontrolü
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) 
                != PackageManager.PERMISSION_GRANTED) {
                Log.w(TAG, "POST_NOTIFICATIONS permission not granted")
            }
        }
        
        createNotificationChannel()
        setupMediaSession()
        
        try {
            // Start as foreground service immediately with basic notification
            startForeground(NOTIFICATION_ID, createBasicNotification())
            Log.d(TAG, "Foreground service started successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error starting foreground service", e)
            // Android 14'te foreground service başlatma hatası durumunda
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                Log.e(TAG, "Android 14+ foreground service restrictions may apply")
            }
        }
    }
    
    private fun setupMediaSession() {
        Log.d(TAG, "Setting up media session")
        mediaSession = MediaSessionCompat(this, "MeditationPlayerSession").apply {
            setCallback(object : MediaSessionCompat.Callback() {
                override fun onPlay() {
                    Log.d(TAG, "MediaSession onPlay called")
                    play()
                }
                
                override fun onPause() {
                    Log.d(TAG, "MediaSession onPause called")
                    pause()
                }
                
                override fun onStop() {
                    Log.d(TAG, "MediaSession onStop called")
                    pause()
                    stopSelf()
                }
                
                override fun onSeekTo(pos: Long) {
                    Log.d(TAG, "MediaSession onSeekTo called: $pos")
                    mediaPlayer?.seekTo(pos.toInt())
                    
                    // Seek sonrası immediate state update
                    updatePlaybackState()
                    
                    // MediaMetadata'yı da güncelle (duration değişmiş olabilir)
                    currentMusic?.let { updateMediaMetadata(it) }
                    
                    updateNotification()
                }
                
                override fun onSkipToNext() {
                    Log.d(TAG, "MediaSession onSkipToNext called")
                    // 30 saniye ileri sar
                    val currentPosition = mediaPlayer?.currentPosition ?: 0
                    val duration = mediaPlayer?.duration ?: 0
                    val newPosition = (currentPosition + 30000).coerceAtMost(duration)
                    mediaPlayer?.seekTo(newPosition)
                    updatePlaybackState()
                }
                
                override fun onSkipToPrevious() {
                    Log.d(TAG, "MediaSession onSkipToPrevious called")
                    // Başa sar
                    mediaPlayer?.seekTo(0)
                    updatePlaybackState()
                }
            })
            
            // Android 14+ için gerekli metadata'yı ayarla
            isActive = true
            updatePlaybackState()
        }
    }
    
    private fun updatePlaybackState() {
        val state = if (isPreparing) {
            PlaybackStateCompat.STATE_BUFFERING
        } else if (isPlaying) {
            PlaybackStateCompat.STATE_PLAYING
        } else {
            PlaybackStateCompat.STATE_PAUSED
        }
        
        val currentPosition = mediaPlayer?.currentPosition?.toLong() ?: 0L
        val duration = mediaPlayer?.duration?.toLong() ?: 0L
        
        // Android 14+ için playback speed'i set et (progress tracking için kritik)
        val playbackSpeed = if (isPlaying && !isPreparing) 1.0f else 0.0f
        
        val playbackState = PlaybackStateCompat.Builder()
            .setActions(
                PlaybackStateCompat.ACTION_PLAY or
                PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_STOP or
                PlaybackStateCompat.ACTION_SEEK_TO or
                PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            )
            .setState(state, currentPosition, playbackSpeed)
            .build()
            
        mediaSession?.setPlaybackState(playbackState)
        
        Log.v(TAG, "Updated playback state: state=$state, position=$currentPosition, duration=$duration, speed=$playbackSpeed")
    }
    
    private fun updateMediaMetadata(music: MeditationMusic) {
        Log.d(TAG, "Updating MediaMetadata for: ${music.title}")
        
        val duration = mediaPlayer?.duration?.toLong() ?: 0L
        
        val metadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, music.title)
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, music.artist)
            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "Harmony Haven")
            .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration)
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, music.id)
            .build()
            
        mediaSession?.setMetadata(metadata)
        Log.d(TAG, "MediaMetadata updated - Duration: $duration ms")
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
        
        // MediaMetadata'yı hemen set et (basic bilgilerle)
        val basicMetadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, music.title)
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, music.artist)
            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "Harmony Haven")
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, music.id)
            .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, 0L) // Will be updated when prepared
            .build()
        mediaSession?.setMetadata(basicMetadata)
        
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
                    
                    // MediaMetadata'yı güncelle (duration bilgisi için kritik)
                    currentMusic?.let { updateMediaMetadata(it) }
                    
                    // Notify that loading is finished
                    onLoadingStateChanged?.invoke(false)
                    
                    // Start playing automatically after prepared
                    this@MediaPlayerService.isPlaying = true
                    start()
                    updatePlaybackState() // Android 14+ için kritik
                    updateNotification()
                    startProgressTracking()
                    onPlayStateChanged?.invoke(true)
                    Log.d(TAG, "Auto-started playback after preparation")
                }
                
                setOnCompletionListener {
                    Log.d(TAG, "Media playback completed")
                    // Handle repeat mode
                    when (repeatMode) {
                        1 -> {
                            // Repeat all (loop) - restart the same track
                            Log.d(TAG, "Repeat mode: looping current track")
                            try {
                                seekTo(0)
                                start()
                                updatePlaybackState()
                            } catch (e: Exception) {
                                Log.e(TAG, "Error restarting track in loop mode", e)
                            }
                        }
                        2 -> {
                            // Repeat one - same as repeat all for single track
                            Log.d(TAG, "Repeat one: restarting current track")
                            try {
                                seekTo(0)
                                start()
                                updatePlaybackState()
                            } catch (e: Exception) {
                                Log.e(TAG, "Error restarting track in repeat one mode", e)
                            }
                        }
                        else -> {
                            // No repeat - just stop
                            Log.d(TAG, "No repeat: stopping playback")
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
                            updatePlaybackState() // Android 14+ için kritik
                            onPlayStateChanged?.invoke(false)
                            updateNotification()
                            
                            Log.d(TAG, "Track completed - reset to beginning and paused")
                        }
                    }
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
            updatePlaybackState()
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
                    updatePlaybackState() // Android 14+ için kritik
                    updateNotification()
                    startProgressTracking()
                    onPlayStateChanged?.invoke(true)
                    Log.d(TAG, "Playback started successfully")
                } else {
                    Log.d(TAG, "Already playing")
                    isPlaying = true
                    updatePlaybackState()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error starting playback", e)
                isPlaying = false
                updatePlaybackState()
                onErrorOccurred?.invoke("Çalma hatası: ${e.message}")
                onPlayStateChanged?.invoke(false)
            }
        } ?: run {
            Log.e(TAG, "MediaPlayer is null, can't play")
            isPlaying = false
            updatePlaybackState()
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
            updatePlaybackState()
            onPlayStateChanged?.invoke(false)
            return
        }
        
        mediaPlayer?.let {
            if (it.isPlaying) {
                try {
                    it.pause()
                    isPlaying = false
                    stopProgressTracking()
                    updatePlaybackState() // Android 14+ için kritik
                    updateNotification()
                    onPlayStateChanged?.invoke(false)
                    Log.d(TAG, "Playback paused successfully")
                } catch (e: Exception) {
                    Log.e(TAG, "Error pausing playback", e)
                    isPlaying = false
                    updatePlaybackState()
                    onPlayStateChanged?.invoke(false)
                }
            } else {
                Log.d(TAG, "Already paused")
                isPlaying = false
                updatePlaybackState()
            }
        } ?: run {
            Log.d(TAG, "MediaPlayer is null, can't pause")
            isPlaying = false
            updatePlaybackState()
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
            updatePlaybackState()
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
                var updateCounter = 0
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
                        
                        // Android 14+ için playback state'i daha sık güncelle (notification progress için)
                        updateCounter++
                        if (updateCounter % 10 == 0) { // Her 1 saniyede bir güncelle (10 * 100ms = 1s)
                            updatePlaybackState()
                        }
                        
                        // MediaMetadata'da duration değişikliği varsa güncelle
                        if (updateCounter % 100 == 0) { // Her 10 saniyede bir kontrol et
                            currentMusic?.let { 
                                val sessionDuration = mediaSession?.controller?.metadata?.getLong(MediaMetadataCompat.METADATA_KEY_DURATION) ?: 0L
                                if (sessionDuration != duration.toLong()) {
                                    updateMediaMetadata(it)
                                }
                            }
                        }
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
            Log.d(TAG, "Creating notification channel for API level ${Build.VERSION.SDK_INT}")
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Harmony Haven Medya Oynatıcı",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Meditasyon müzik oynatıcısı için kontroller"
                setShowBadge(false)
                
                // Android 14 için özel ayarlar
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    setBlockable(false) // Kullanıcının kanalı engellemesini zorlaştır
                }
                
                // Medya oynatıcı için gerekli ayarlar
                enableLights(false)
                enableVibration(false)
                setSound(null, null) // Medya bildirimleri sessiz olmalı
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d(TAG, "Notification channel created successfully")
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
        
        // Android 14+ için progress tracking etkinleştir
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val currentPosition = mediaPlayer?.currentPosition?.toLong() ?: 0L
            val duration = mediaPlayer?.duration?.toLong() ?: 0L
            
            if (isPlaying && duration > 0) {
                builder.setWhen(System.currentTimeMillis() - currentPosition)
                    .setShowWhen(true)
                    .setUsesChronometer(true)
            } else {
                builder.setShowWhen(false)
                    .setUsesChronometer(false)
            }
        }
        
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
        
        // Android 14+ için progress tracking etkinleştir
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val currentPosition = mediaPlayer?.currentPosition?.toLong() ?: 0L
            val duration = mediaPlayer?.duration?.toLong() ?: 0L
            
            if (isPlaying && duration > 0) {
                builder.setWhen(System.currentTimeMillis() - currentPosition)
                    .setShowWhen(true)
                    .setUsesChronometer(true)
            } else {
                builder.setShowWhen(false)
                    .setUsesChronometer(false)
            }
        }
        
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
                ACTION_PLAY_PAUSE -> {
                    Log.d(TAG, "ACTION_PLAY_PAUSE received")
                    togglePlayPause()
                    updatePlaybackState() // Android 14+ için ek güncelleme
                }
                ACTION_STOP -> {
                    Log.d(TAG, "ACTION_STOP received")
                    pause()
                    stopTimer()
                    stopSelf()
                }
                ACTION_PREVIOUS -> {
                    Log.d(TAG, "ACTION_PREVIOUS received")
                    // MediaSession callback'ini tetikle
                    mediaSession?.controller?.transportControls?.skipToPrevious()
                }
                ACTION_NEXT -> {
                    Log.d(TAG, "ACTION_NEXT received")
                    // MediaSession callback'ini tetikle
                    mediaSession?.controller?.transportControls?.skipToNext()
                }
                else -> {
                    Log.d(TAG, "Unknown action: ${it.action}")
                }
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
    
    // Repeat Mode Functions
    fun setRepeatMode(mode: Int) {
        Log.d(TAG, "Setting repeat mode to: $mode")
        repeatMode = mode
        onRepeatModeChanged?.invoke(repeatMode)
    }
    
    fun getRepeatMode(): Int = repeatMode
    
    fun toggleRepeatMode() {
        val newMode = (repeatMode + 1) % 3
        setRepeatMode(newMode)
    }
} 