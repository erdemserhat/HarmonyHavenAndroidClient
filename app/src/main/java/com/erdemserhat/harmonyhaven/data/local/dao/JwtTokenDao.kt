package com.erdemserhat.harmonyhaven.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erdemserhat.harmonyhaven.data.local.entities.JwtTokenEntity


@Dao
interface JwtTokenDao {


    @Query("SELECT token FROM jwt_token WHERE id=1")
    suspend fun getJwtToken(): String


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJwtToken(jwtTokenEntity: JwtTokenEntity)

    @Query("DELETE FROM jwt_token")
    suspend fun deleteJwtToken()
}
