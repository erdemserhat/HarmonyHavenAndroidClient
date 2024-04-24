package com.erdemserhat.harmonyhaven.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erdemserhat.harmonyhaven.data.local.dao.JwtTokenDao
import com.erdemserhat.harmonyhaven.data.local.dao.NotificationDao
import com.erdemserhat.harmonyhaven.data.local.entities.JwtTokenEntity
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity

@Database(entities = [JwtTokenEntity::class,NotificationEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jwtTokenDao(): JwtTokenDao

    abstract fun notificationDao():NotificationDao

}


