package com.erdemserhat.harmonyhaven.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erdemserhat.harmonyhaven.dto.responses.Quote


@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val quoteId: Int=0,
    val quote: String="",
    val writer: String="",
    val imageUrl: String="",
    val quoteCategory: Int=21,
    val isLiked: Boolean=false
){
    fun convertToQuote(): Quote {
        return Quote(
            id = quoteId,
            quote = quote,
            writer = writer,
            imageUrl = imageUrl,
            isLiked = isLiked)

    }
}
