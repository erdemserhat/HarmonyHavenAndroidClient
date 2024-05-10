package com.erdemserhat.harmonyhaven.data.local.repository

import androidx.lifecycle.LiveData
import com.erdemserhat.harmonyhaven.data.local.dao.NotificationDao
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity

class NotificationRepository(private val notificationDao: NotificationDao) {

    val allNotifications: LiveData<List<NotificationEntity>> = notificationDao.getAllNotifications()

    suspend fun insert(notification: NotificationEntity) {
        notificationDao.insert(notification)
    }
}
