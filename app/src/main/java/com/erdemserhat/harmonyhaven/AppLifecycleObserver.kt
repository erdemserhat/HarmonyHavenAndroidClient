package com.erdemserhat.harmonyhaven

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.media3.exoplayer.ExoPlayer


class AppLifecycleObserver(
    private val context: Context,
    private val exoPlayer: ExoPlayer // Kullanmakta olduğun medya oynatıcı
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        // Uygulama arka plana alındığında medya oynatıcıyı durdur
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        // Uygulama ön plana geldiğinde medya oynatıcıyı tekrar başlatmak istersen buraya ekleyebilirsin
        exoPlayer.play()
    }
}
