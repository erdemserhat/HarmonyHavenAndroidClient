package com.erdemserhat.harmonyhaven

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.media3.exoplayer.ExoPlayer


class AppLifecycleObserver(
    private val context: Context,
    private val exoPlayer: ExoPlayer
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        exoPlayer.play()
    }
}
