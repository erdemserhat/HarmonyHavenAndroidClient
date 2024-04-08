package com.erdemserhat.harmonyhaven.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JwtTokenEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jwtTokenDao(): JwtTokenDao
}


