package com.erdemserhat.harmonyhaven.data.local.repository

import androidx.lifecycle.LiveData
import com.erdemserhat.harmonyhaven.data.local.dao.NotificationDao
import com.erdemserhat.harmonyhaven.data.local.dao.QuoteDao
import com.erdemserhat.harmonyhaven.data.local.entities.QuoteEntity

class QuoteRepository(private val quoteDao: QuoteDao) {

    suspend fun getCachedQuotes(): List<QuoteEntity> = quoteDao.getAllQuotes()
    suspend fun addCachedQuotes(quotes: List<QuoteEntity>) = quoteDao.insertQuotes(quotes)
    suspend fun clearCachedQuotes() = quoteDao.clearQuotes()

}