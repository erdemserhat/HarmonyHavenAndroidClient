package com.erdemserhat.harmonyhaven.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erdemserhat.harmonyhaven.data.local.dao.JwtTokenDao
import com.erdemserhat.harmonyhaven.data.local.entities.JwtTokenEntity

@Database(entities = [JwtTokenEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jwtTokenDao(): JwtTokenDao
}


