package com.erdemserhat.harmonyhaven.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity


@Dao
interface NotificationDao {


    @Insert
    suspend fun insert(notification: NotificationEntity)

    @Query("SELECT * FROM notifications ORDER BY date DESC")
    fun getAllNotifications(): LiveData<List<NotificationEntity>>
}
