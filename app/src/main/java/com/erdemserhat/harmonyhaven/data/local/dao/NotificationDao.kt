package com.erdemserhat.harmonyhaven.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity


@Dao
interface NotificationDao {

    /**
     * Inserts a new notification into the database.
     *
     * This method performs an insert operation on the 'notifications' table. If the notification
     * already exists, it will be replaced based on its primary key.
     *
     * @param notification The [NotificationEntity] object representing the notification to be inserted.
     */
    @Insert
    suspend fun insert(notification: NotificationEntity)

    /**
     * Retrieves all notifications from the database, ordered by date in descending order.
     *
     * This method performs a query to fetch all notifications from the 'notifications' table. The results
     * are ordered by the date field in descending order. The result is observed as LiveData, which allows
     * the UI to reactively update when the data changes.
     *
     * @return A [LiveData] object containing a list of [NotificationEntity] objects representing all notifications.
     */
    @Query("SELECT * FROM notifications ORDER BY date DESC")
    fun getAllNotifications(): LiveData<List<NotificationEntity>>
}
