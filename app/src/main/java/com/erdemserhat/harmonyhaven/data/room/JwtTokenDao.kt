package com.erdemserhat.harmonyhaven.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface JwtTokenDao {
    @Query("SELECT token FROM jwt_token WHERE id=1")
    fun getJwtToken():String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJwtToken(jwtTokenEntity:JwtTokenEntity)

}