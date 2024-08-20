package com.erdemserhat.harmonyhaven.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Represents a JWT token entity in the 'jwt_token' table of the database.
 *
 * This data class defines the structure of a JWT token record stored in the database.
 * Each property maps to a column in the 'jwt_token' table.
 *
 * @property id The unique identifier of the JWT token record. It is set to 1 by default and serves as the primary key.
 * @property token The JWT token string that is stored in the database.
 */
@Entity(tableName = "jwt_token")
data class JwtTokenEntity(
    @PrimaryKey
    val id: Int = 1,
    val token: String
)
