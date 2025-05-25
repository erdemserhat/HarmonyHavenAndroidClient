package com.erdemserhat.harmonyhaven.data.repository

import com.erdemserhat.harmonyhaven.domain.model.rest.Mood
import com.erdemserhat.harmonyhaven.domain.repository.MoodRepository
import com.erdemserhat.harmonyhaven.domain.usecase.user.GetAllMoods
import com.erdemserhat.harmonyhaven.domain.usecase.user.GetUserMood
import com.erdemserhat.harmonyhaven.domain.usecase.user.UpdateUserMood
import javax.inject.Inject

class MoodRepositoryImpl @Inject constructor(
    private val getAllMoods: GetAllMoods,
    private val getUserMood: GetUserMood,
    private val updateUserMood: UpdateUserMood
) : MoodRepository {

    override suspend fun getAllMoods(): List<Mood> {
        return getAllMoods.executeRequest()
    }

    override suspend fun getUserCurrentMood(): String? {
        val moodResponse = getUserMood.executeRequest()
        return moodResponse?.get("moodId")
    }

    override suspend fun updateUserMood(moodId: String): Boolean {
        return updateUserMood.executeRequest(moodId)
    }
} 