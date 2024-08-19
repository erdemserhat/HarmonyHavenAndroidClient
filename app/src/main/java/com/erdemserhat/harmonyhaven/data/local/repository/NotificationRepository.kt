package com.erdemserhat.harmonyhaven.data.local.repository

import androidx.lifecycle.LiveData
import com.erdemserhat.harmonyhaven.data.local.dao.NotificationDao
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity

/**
 * Repository class for managing notification data.
 *
 * This class provides methods to interact with the [NotificationDao] for performing database operations related to notifications.
 * It acts as an intermediary between the data layer (DAO) and the rest of the application.
 *
 * @constructor Creates an instance of [NotificationRepository] with the given [NotificationDao] dependency.
 * @property notificationDao The DAO (Data Access Object) used to perform operations on the 'notifications' table.
 * @property allNotifications A [LiveData] object that provides a stream of notification data from the 'notifications' table.
 */
class NotificationRepository(private val notificationDao: NotificationDao) {

    /**
     * A [LiveData] list of all notifications from the database.
     *
     * This property exposes a stream of notification records, automatically updated when the data in the 'notifications' table changes.
     *
     * @return A [LiveData] object containing a list of [NotificationEntity] objects representing the notifications.
     */
    val allNotifications: LiveData<List<NotificationEntity>> = notificationDao.getAllNotifications()

    /**
     * Inserts a new notification into the database.
     *
     * This method inserts a notification record into the 'notifications' table.
     *
     * @param notification The [NotificationEntity] object to be inserted into the database.
     */
    suspend fun insert(notification: NotificationEntity) {
        notificationDao.insert(notification)
    }
}
