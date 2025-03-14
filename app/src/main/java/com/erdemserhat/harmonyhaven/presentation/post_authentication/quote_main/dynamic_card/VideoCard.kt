package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.OptIn
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.erdemserhat.harmonyhaven.AppLifecycleObserver
import com.google.accompanist.placeholder.PlaceholderHighlight

import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer

@OptIn(UnstableApi::class)
@Composable
fun VideoCard(
    videoUrl: String,
    isPlaying: Boolean,
    prepareOnly: Boolean,
    viewModel: VolumeControlViewModel? = null

) {

    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    val isMuted = viewModel?.isMuted?.collectAsState()



    // Cache Oluşturma
    val cache = CacheManager.getCache(context)

    val dataSourceFactory = DefaultDataSource.Factory(context)
    val cacheDataSourceFactory = CacheDataSource.Factory()
        .setCache(cache)
        .setUpstreamDataSourceFactory(dataSourceFactory)
        .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
            .build().apply {
                if (isMuted != null) {
                    Log.d("dqddasdasdas",isMuted.value.toString())
                    volume = if(isMuted.value) 0f else 1f
                }



                setMediaItem(MediaItem.fromUri(videoUrl))
                prepare()
                playWhenReady = true
                repeatMode = ExoPlayer.REPEAT_MODE_ALL
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        if (state == ExoPlayer.STATE_READY) {
                            isLoading = false
                        }
                    }
                })

            }

    }

    LaunchedEffect(isMuted?.value) {
        exoPlayer.volume = if(isMuted?.value == true) 0f else 1f
    }



    // LifecycleObserver için bir uygulama yaşam döngüsü gözlemcisi ekle
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = AppLifecycleObserver(context, exoPlayer)
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
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
        exoPlayer.volume = if (isPlaying && (isMuted?.value == false)) 1f else 0f // Ses sadece aktif sayfa için açık
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (prepareOnly) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White.copy(0.08f),
                            animationSpec = InfiniteRepeatableSpec(
                                animation = tween(durationMillis = 4000),
                                repeatMode = RepeatMode.Restart
                            )
                        ), // Shimmer efekti
                        color = Color.Black // Shimmer efekti için arka plan rengi
                    ),
                factory = {
                    PlayerView(context)
                        .apply {

                        useController = false
                        player = exoPlayer
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                }
            )
        }
    }

    // Orijinal pencere bayraklarını hatırlayın ve tam ekran modunu yönetin
    val activity = context as? Activity
    var originalWindowFlags by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        activity?.let {
            originalWindowFlags = it.window.attributes.flags
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
            activity?.let {
                it.window.setFlags(
                    originalWindowFlags,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }
    }
}
