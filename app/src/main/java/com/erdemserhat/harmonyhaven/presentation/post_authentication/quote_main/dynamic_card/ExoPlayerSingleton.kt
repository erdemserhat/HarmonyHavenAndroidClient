package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

object ExoPlayerSingleton {
    private var exoPlayer: ExoPlayer? = null

    fun getInstance(context: Context, videoUrl: String): ExoPlayer {
        return exoPlayer ?: ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            playWhenReady = false
            exoPlayer = this
        }
    }

    fun release() {
        exoPlayer?.release()
        exoPlayer = null
    }
}
