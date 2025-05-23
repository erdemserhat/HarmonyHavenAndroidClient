package com.erdemserhat.harmonyhaven.presentation.post_authentication.player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import kotlinx.coroutines.delay

private const val TAG = "MusicPlayerScreen"

data class MeditationMusic(
    val id: String,
    val title: String,
    val artist: String,
    val duration: String,
    val imageUrl: String,
    val audioUrl: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerScreen(
    navController: NavController,
    music: MeditationMusic
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }
    var repeatMode by remember { mutableStateOf(0) } // 0: No repeat, 1: Repeat all, 2: Repeat one
    var progress by remember { mutableFloatStateOf(0f) }
    var currentPositionText by remember { mutableStateOf("0:00") }
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "progress")
    
    // Timer related states
    var showTimerBottomSheet by remember { mutableStateOf(false) }
    val timerSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedHours by remember { mutableIntStateOf(0) }
    var selectedMinutes by remember { mutableIntStateOf(0) }
    var timerActive by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf<Long?>(null) }
    
    // Loading state
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    // Service connection state
    var mediaPlayerService by remember { mutableStateOf<MediaPlayerService?>(null) }
    var serviceBound by remember { mutableStateOf(false) }
    
    // Debugging state
    var debugMessage by remember { mutableStateOf("Bağlanıyor...") }
    
    // Timer countdown effect
    LaunchedEffect(remainingTime, timerActive) {
        if (timerActive && remainingTime != null && remainingTime!! > 0) {
            delay(1000)
            remainingTime = remainingTime!! - 1
            
            // When timer reaches 0, stop the music
            if (remainingTime == 0L) {
                mediaPlayerService?.pause()
                timerActive = false
                remainingTime = null
            }
        }
    }
    
    // Define service connection
    val serviceConnection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d(TAG, "Service connected")
                try {
                    val binder = service as MediaPlayerService.MediaPlayerBinder
                    mediaPlayerService = binder.getService()
                    serviceBound = true
                    debugMessage = "Servis bağlandı"
                    
                    // Set up service callbacks
                    mediaPlayerService?.onProgressChanged = { newProgress ->
                        progress = newProgress
                        val duration = mediaPlayerService?.getDuration() ?: 0
                        val currentPosition = (duration * newProgress).toInt()
                        currentPositionText = formatTime(currentPosition / 1000f)
                    }
                    
                    mediaPlayerService?.onPlayStateChanged = { playing ->
                        Log.d(TAG, "Play state changed: $playing")
                        isPlaying = playing
                        debugMessage = if (playing) "Çalıyor" else "Duraklatıldı"
                    }
                    
                    mediaPlayerService?.onErrorOccurred = { message ->
                        Log.e(TAG, "Error occurred: $message")
                        errorMessage = message
                        isLoading = false
                        debugMessage = "Hata: $message"
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    
                    // Listen for loading state changes from service
                    mediaPlayerService?.onLoadingStateChanged = { loading ->
                        Log.d(TAG, "Loading state changed: $loading")
                        isLoading = loading
                        debugMessage = if (loading) "Yükleniyor..." else "Hazır"
                    }
                    
                    // Initialize with the music
                    isLoading = true
                    debugMessage = "Müzik yükleniyor..."
                    mediaPlayerService?.initializePlayer(music)
                } catch (e: Exception) {
                    Log.e(TAG, "Error connecting to service", e)
                    errorMessage = "Servis bağlantı hatası: ${e.message}"
                    debugMessage = "Servis bağlantı hatası"
                    isLoading = false
                }
            }
            
            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d(TAG, "Service disconnected")
                mediaPlayerService = null
                serviceBound = false
                isLoading = false
                debugMessage = "Servis bağlantısı kesildi"
            }
        }
    }
    
    // Connect to the service when the screen is first displayed
    LaunchedEffect(Unit) {
        Log.d(TAG, "Connecting to MediaPlayerService")
        try {
            val intent = Intent(context, MediaPlayerService::class.java)
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            context.startService(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Error binding to service", e)
            errorMessage = "Servis başlatma hatası: ${e.message}"
            debugMessage = "Servis başlatma hatası"
            isLoading = false
        }
    }
    
    // Auto-dismiss error after a delay
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            delay(3000)
            errorMessage = null
        }
    }
    
    // Cleanup when the screen is closed
    DisposableEffect(Unit) {
        onDispose {
            Log.d(TAG, "Disposing MediaPlayerScreen, unbinding service")
            if (serviceBound) {
                try {
                    context.unbindService(serviceConnection)
                    serviceBound = false
                } catch (e: Exception) {
                    Log.e(TAG, "Error unbinding service", e)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Blurred background image
        AsyncImage(
            model = music.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(20.dp)
        )
        
        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        )
        
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { },  // Empty title
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.statusBarsPadding()
                )
            }
        ) { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                
                // Album art
                Surface(
                    modifier = Modifier
                        .size(280.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    color = Color.Transparent
                ) {
                    AsyncImage(
                        model = music.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                
                Spacer(modifier = Modifier.height(48.dp))
                
                // Title and artist
                Text(
                    text = music.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = music.artist,
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Debug message (only in debug mode)
                Text(
                    text = debugMessage,
                    color = Color.Yellow.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Error message
                errorMessage?.let {
                    Text(
                        text = it,
                        color = Color.Red.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                
                // Progress bar
                Column(modifier = Modifier.fillMaxWidth()) {
                    Slider(
                        value = animatedProgress,
                        onValueChange = { newValue ->
                            // Only update the UI progress
                            progress = newValue
                        },
                        onValueChangeFinished = {
                            // When user finishes dragging, update the actual player position
                            if (!isLoading) {
                                val newPosition = (mediaPlayerService?.getDuration() ?: 0) * progress
                                mediaPlayerService?.seekTo(newPosition.toInt())
                            }
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = harmonyHavenGreen,
                            activeTrackColor = harmonyHavenGreen,
                            inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                        ),
                        enabled = !isLoading,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = currentPositionText,
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                        
                        Text(
                            text = music.duration,
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Controls
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { 
                            repeatMode = (repeatMode + 1) % 3
                        },
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = when (repeatMode) {
                                0 -> Icons.Default.Star
                                1 -> Icons.Default.Star
                                else -> Icons.Default.Star
                            },
                            contentDescription = "Repeat",
                            tint = if (repeatMode > 0) harmonyHavenGreen else Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    IconButton(
                        onClick = { 
                            if (!isLoading) {
                                // Skip to previous or restart
                                mediaPlayerService?.seekTo(0)
                                if (!isPlaying) {
                                    mediaPlayerService?.play()
                                }
                            }
                        },
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Previous",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    
                    // Play/Pause button or loading indicator
                    if (isLoading) {
                        // Show loading indicator
                        CircularProgressIndicator(
                            color = harmonyHavenGreen,
                            modifier = Modifier.size(70.dp)
                        )
                    } else {
                        // Show play/pause button
                        Surface(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .clickable {
                                    Log.d(TAG, "Play button clicked, current state: isPlaying=$isPlaying")
                                    mediaPlayerService?.togglePlayPause()
                                },
                            color = harmonyHavenGreen
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Star else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(32.dp)
                            )
                        }
                    }
                    
                    IconButton(
                        onClick = {
                            if (!isLoading) {
                                // Skip forward 30 seconds
                                val currentPosition = mediaPlayerService?.getCurrentPosition() ?: 0
                                val duration = mediaPlayerService?.getDuration() ?: 0
                                val newPosition = (currentPosition + 30000).coerceAtMost(duration)
                                mediaPlayerService?.seekTo(newPosition)
                            }
                        },
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Next",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    
                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) harmonyHavenGreen else Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Additional controls
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = { /* Share */ },
                            enabled = !isLoading
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = Color.White.copy(alpha = 0.7f)
                            )
                        }
                        Text(
                            text = "Paylaş",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = { showTimerBottomSheet = true },
                            enabled = !isLoading
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Sleep Timer",
                                tint = if (timerActive) harmonyHavenGreen else Color.White.copy(alpha = 0.7f)
                            )
                        }
                        Text(
                            text = if (timerActive && remainingTime != null) {
                                val hours = remainingTime!! / 3600
                                val minutes = (remainingTime!! % 3600) / 60
                                val seconds = remainingTime!! % 60
                                if (hours > 0) {
                                    String.format("%d:%02d:%02d", hours, minutes, seconds)
                                } else {
                                    String.format("%02d:%02d", minutes, seconds)
                                }
                            } else "Uyku Zamanlayıcı",
                            color = if (timerActive) harmonyHavenGreen else Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Timer Bottom Sheet
        if (showTimerBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showTimerBottomSheet = false },
                sheetState = timerSheetState,
                containerColor = Color.Black.copy(alpha = 0.95f),
                scrimColor = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Uyku Zamanlayıcı",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Hours selector
                    Text(
                        "Saat",
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Slider(
                        value = selectedHours.toFloat(),
                        onValueChange = { selectedHours = it.toInt() },
                        valueRange = 0f..12f,
                        steps = 11,
                        colors = SliderDefaults.colors(
                            thumbColor = harmonyHavenGreen,
                            activeTrackColor = harmonyHavenGreen,
                            inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                        )
                    )
                    Text(
                        "$selectedHours saat",
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Minutes selector
                    Text(
                        "Dakika",
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Slider(
                        value = selectedMinutes.toFloat(),
                        onValueChange = { selectedMinutes = it.toInt() },
                        valueRange = 0f..55f,
                        steps = 11,
                        colors = SliderDefaults.colors(
                            thumbColor = harmonyHavenGreen,
                            activeTrackColor = harmonyHavenGreen,
                            inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                        )
                    )
                    Text(
                        "$selectedMinutes dakika",
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    // Start timer button
                    Button(
                        onClick = {
                            val totalSeconds = (selectedHours * 3600 + selectedMinutes * 60).toLong()
                            if (totalSeconds > 0) {
                                remainingTime = totalSeconds
                                timerActive = true
                                showTimerBottomSheet = false
                            } else {
                                Toast.makeText(context, "Lütfen bir süre seçin", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = harmonyHavenGreen
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text(
                            if (timerActive) "Zamanlayıcıyı Güncelle" else "Zamanlayıcıyı Başlat",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (timerActive) {
                        TextButton(
                            onClick = {
                                timerActive = false
                                remainingTime = null
                                showTimerBottomSheet = false
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(
                                "Zamanlayıcıyı İptal Et",
                                color = Color.Red,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// Helper function to parse duration string to seconds
private fun parseDuration(duration: String): Float {
    val parts = duration.split(":")
    if (parts.size == 2) {
        val minutes = parts[0].toFloatOrNull() ?: 0f
        val seconds = parts[1].toFloatOrNull() ?: 0f
        return minutes * 60f + seconds
    }
    return 0f
}

// Helper function to format seconds to time string
private fun formatTime(seconds: Float): String {
    val minutes = (seconds / 60).toInt()
    val remainingSeconds = (seconds % 60).toInt()
    return String.format("%d:%02d", minutes, remainingSeconds)
} 