package com.erdemserhat.harmonyhaven.data.local.entities

import android.icu.text.CaseMap.Title
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L, // Otomatik artan id
    val title: String,
    val body: String,
    val date: Long
)