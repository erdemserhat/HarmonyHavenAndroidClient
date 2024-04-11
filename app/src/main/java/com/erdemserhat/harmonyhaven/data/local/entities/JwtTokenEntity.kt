package com.erdemserhat.harmonyhaven.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "jwt_token")
data class JwtTokenEntity(
    @PrimaryKey
    val id: Int = 1,
    val token: String
)
