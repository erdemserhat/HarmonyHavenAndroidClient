package com.erdemserhat.harmonyhaven.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erdemserhat.harmonyhaven.data.local.entities.JwtTokenEntity


@Dao
interface JwtTokenDao {

    /**
     * Retrieves the JWT token from the database.
     *
     * This method performs a query to fetch the JWT token stored in the 'jwt_token' table.
     * It assumes that there is only one token entry with an ID of 1.
     *
     * @return The JWT token as a [String] retrieved from the database.
     */
    @Query("SELECT token FROM jwt_token WHERE id=1")
    suspend fun getJwtToken(): String

    /**
     * Inserts or updates a JWT token in the database.
     *
     * This method performs an insert operation on the 'jwt_token' table. If a token with the same
     * ID already exists, it will be replaced with the new token data.
     *
     * @param jwtTokenEntity The [JwtTokenEntity] object representing the JWT token to be inserted or updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJwtToken(jwtTokenEntity: JwtTokenEntity)
}
