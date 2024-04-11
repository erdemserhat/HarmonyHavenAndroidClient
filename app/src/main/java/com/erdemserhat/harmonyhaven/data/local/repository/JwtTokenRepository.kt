package com.erdemserhat.harmonyhaven.data.local.repository

import com.erdemserhat.harmonyhaven.data.local.dao.JwtTokenDao
import com.erdemserhat.harmonyhaven.data.local.entities.JwtTokenEntity

class JwtTokenRepository(private val jwtTokenDao: JwtTokenDao) {
    fun getJwtToken(): String = jwtTokenDao.getJwtToken()

    fun saveJwtToken(token: String) {
        jwtTokenDao.insertJwtToken(JwtTokenEntity(token=token))
    }
}