package com.erdemserhat.harmonyhaven.di.repository

import com.erdemserhat.harmonyhaven.data.repository.MoodRepositoryImpl
import com.erdemserhat.harmonyhaven.domain.repository.MoodRepository
import com.erdemserhat.harmonyhaven.domain.usecase.user.GetAllMoods
import com.erdemserhat.harmonyhaven.domain.usecase.user.GetUserMood
import com.erdemserhat.harmonyhaven.domain.usecase.user.UpdateUserMood
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoodRepositoryModule {

    @Provides
    @Singleton
    fun provideMoodRepository(
        getAllMoods: GetAllMoods,
        getUserMood: GetUserMood,
        updateUserMood: UpdateUserMood
    ): MoodRepository {
        return MoodRepositoryImpl(
            getAllMoods = getAllMoods,
            getUserMood = getUserMood,
            updateUserMood = updateUserMood
        )
    }
} 