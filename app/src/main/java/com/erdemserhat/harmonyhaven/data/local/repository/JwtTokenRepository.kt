package com.erdemserhat.harmonyhaven.data.local.repository

import com.erdemserhat.harmonyhaven.data.local.dao.JwtTokenDao
import com.erdemserhat.harmonyhaven.data.local.entities.JwtTokenEntity

class JwtTokenRepository(private val jwtTokenDao: JwtTokenDao) {
    suspend fun getJwtToken(): String = jwtTokenDao.getJwtToken()

    suspend fun saveJwtToken(token: String) {
        jwtTokenDao.insertJwtToken(JwtTokenEntity(token=token))
    }
}