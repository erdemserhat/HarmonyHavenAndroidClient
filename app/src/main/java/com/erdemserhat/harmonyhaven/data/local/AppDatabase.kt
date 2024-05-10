package com.erdemserhat.harmonyhaven.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erdemserhat.harmonyhaven.data.local.dao.ArticleDao
import com.erdemserhat.harmonyhaven.data.local.dao.CategoryDao
import com.erdemserhat.harmonyhaven.data.local.dao.JwtTokenDao
import com.erdemserhat.harmonyhaven.data.local.dao.NotificationDao
import com.erdemserhat.harmonyhaven.data.local.entities.ArticleEntity
import com.erdemserhat.harmonyhaven.data.local.entities.CategoryEntity
import com.erdemserhat.harmonyhaven.data.local.entities.JwtTokenEntity
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity

@Database(
    entities = [
        JwtTokenEntity::class,
        NotificationEntity::class,
        ArticleEntity::class,
        CategoryEntity::class

    ], version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jwtTokenDao(): JwtTokenDao

    abstract fun notificationDao(): NotificationDao

    abstract fun articleDao(): ArticleDao

    abstract fun categoryDao():CategoryDao

}


