package com.erdemserhat.harmonyhaven.temp

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView


@Composable
fun TempScreen(url:String) {
    //FullScreenVideoPlayer(url)
}

@OptIn(UnstableApi::class)
@Composable
fun FullScreenVideoPlayer(
    videoUrl: String,
    isPlaying: Boolean, // Aktif sayfa mı?
    prepareOnly: Boolean // Görünür sayfa mı?
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
        }
    }

    // Bu kod, video görünür olduğunda oynatılmasını sağlar
    DisposableEffect(key1 = videoUrl) {
        onDispose {
            exoPlayer.playWhenReady = false
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
        exoPlayer.volume = if (isPlaying) 1f else 0f // Ses sadece aktif sayfa için açık
    }

    if(prepareOnly){
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    useController = false
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT
                    )
                }
            }
        )




    }


    // Orijinal pencere bayraklarını hatırlayın ve tam ekran modunu yönetin
    val activity = context as? Activity
    var originalWindowFlags by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        activity?.let {
            originalWindowFlags = it.window.attributes.flags
            it.window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            activity?.let {
                it.window.setFlags(
                    originalWindowFlags,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }
    }


}