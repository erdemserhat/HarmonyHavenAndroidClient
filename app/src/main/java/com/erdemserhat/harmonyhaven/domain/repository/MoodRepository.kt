package com.erdemserhat.harmonyhaven.domain.repository

import com.erdemserhat.harmonyhaven.domain.model.rest.Mood

interface MoodRepository {
    suspend fun getAllMoods(): List<Mood>
    suspend fun getUserCurrentMood(): String?
    suspend fun updateUserMood(moodId: String): Boolean
} 