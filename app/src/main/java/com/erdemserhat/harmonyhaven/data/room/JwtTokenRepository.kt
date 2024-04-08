package com.erdemserhat.harmonyhaven.data.room

class JwtTokenRepository(private val jwtTokenDao: JwtTokenDao) {
    fun getJwtToken(): String = jwtTokenDao.getJwtToken()

    fun saveJwtToken(token: String) {
        jwtTokenDao.insertJwtToken(JwtTokenEntity(token=token))
    }
}