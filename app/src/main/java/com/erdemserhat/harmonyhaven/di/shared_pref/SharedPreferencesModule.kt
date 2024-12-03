package com.erdemserhat.harmonyhaven.di.shared_pref

import android.content.Context
import android.content.SharedPreferences
import com.erdemserhat.harmonyhaven.test.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {


    @Provides
    @Singleton
    fun provideSession(): SessionManager {
        return SessionManager()
    }
    @Provides
    @Singleton
    @Named("CategorySelection")
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("CategorySelection", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @Named("Permission")
    fun providePermissionPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("PermissionPreferences", Context.MODE_PRIVATE)

    }

    @Provides
    @Singleton
    @Named("UserTutorial")
    fun provideUserTutorialPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("UserTutorial", Context.MODE_PRIVATE)

    }
}