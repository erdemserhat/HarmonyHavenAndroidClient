package com.erdemserhat.harmonyhaven.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity
import com.erdemserhat.harmonyhaven.data.local.entities.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quoteEntity: QuoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuotes(quotes: List<QuoteEntity>)

    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): List<QuoteEntity>

    @Query("DELETE FROM quotes")
    fun clearQuotes()
}
