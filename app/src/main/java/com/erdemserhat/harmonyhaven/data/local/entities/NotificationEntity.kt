package com.erdemserhat.harmonyhaven.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Represents a notification entity in the 'notifications' table of the database.
 *
 * This data class defines the structure of a notification record stored in the database.
 * Each property maps to a column in the 'notifications' table.
 *
 * @property id The unique identifier of the notification record. It is auto-generated and serves as the primary key.
 * @property title The title of the notification.
 * @property body The content or body of the notification.
 * @property date The date and time when the notification was created, represented as a timestamp (milliseconds since epoch).
 */
@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L, // Automatically generated ID for the notification
    val title: String,
    val body: String,
    val date: Long
)
