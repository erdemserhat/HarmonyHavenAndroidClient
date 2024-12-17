package com.erdemserhat.harmonyhaven.data.local.repository

import com.erdemserhat.harmonyhaven.data.local.dao.JwtTokenDao
import com.erdemserhat.harmonyhaven.data.local.entities.JwtTokenEntity
/**
 * Repository class for managing JWT token data.
 *
 * This class provides methods to interact with the [JwtTokenDao] for performing database operations related to JWT tokens.
 * It serves as an intermediary between the data layer (DAO) and the rest of the application.
 *
 * @constructor Creates an instance of [JwtTokenRepository] with the given [JwtTokenDao] dependency.
 * @property jwtTokenDao The DAO (Data Access Object) used to perform operations on the 'jwt_token' table.
 */
class JwtTokenRepository(private val jwtTokenDao: JwtTokenDao) {

    /**
     * Retrieves the JWT token from the database.
     *
     * This method fetches the JWT token stored in the 'jwt_token' table.
     *
     * @return The JWT token string.
     */
    suspend fun getJwtToken(): String = jwtTokenDao.getJwtToken()

    /**
     * Saves a new JWT token to the database.
     *
     * This method inserts or updates the JWT token record in the 'jwt_token' table.
     * It creates a new [JwtTokenEntity] instance with the provided token string.
     *
     * @param token The JWT token string to be inserted or updated in the database.
     */
    suspend fun saveJwtToken(token: String) {
        jwtTokenDao.insertJwtToken(JwtTokenEntity(token = token))
    }

    suspend fun deleteJwtToken() {
        jwtTokenDao.deleteJwtToken()
    }
}
