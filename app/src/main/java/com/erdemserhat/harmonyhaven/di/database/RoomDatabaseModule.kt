package com.erdemserhat.harmonyhaven.di.database

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.erdemserhat.harmonyhaven.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }


    @Provides
    @Singleton
    fun provideRoomDatabase(
        context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext, // applicationContext kullanmak için
            AppDatabase::class.java,
            "harmony-haven-database",
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}