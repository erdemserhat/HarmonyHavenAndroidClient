package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.ExoDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File

@UnstableApi
object CacheManager {
    private var cache: SimpleCache? = null

    @OptIn(UnstableApi::class)
    fun getCache(context: Context): SimpleCache {
        if (cache == null) {
            val cacheDir = File(context.cacheDir, "harmony_video_cache")
            val databaseProvider = ExoDatabaseProvider(context)
            cache = SimpleCache(
                cacheDir,
                LeastRecentlyUsedCacheEvictor(500 * 1024 * 1024), // 100 MB
                databaseProvider
            )
        }
        return cache!!
    }

    fun releaseCache() {
        cache?.release()
        cache = null
    }
}
