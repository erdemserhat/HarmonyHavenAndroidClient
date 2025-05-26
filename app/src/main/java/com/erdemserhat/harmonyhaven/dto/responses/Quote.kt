package com.erdemserhat.harmonyhaven.dto.responses
import android.os.Parcelable
import com.erdemserhat.harmonyhaven.data.local.entities.QuoteEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class Quote(
    val id:Int=-1,
    val quote:String="",
    val writer:String="",
    val imageUrl:String="",
    val quoteCategory:Int=-1,
    var isLiked: Boolean=false
): Parcelable {
    fun convertToEntity(): QuoteEntity {
        return QuoteEntity(
            quoteId = id,
            quote = quote,
            writer = writer,
            imageUrl = imageUrl,
            isLiked = isLiked,
            quoteCategory = quoteCategory

        )

    }
}

@Parcelize
data class QuoteForOrderModel(
    val id:Int=-1,
    val quote:String="",
    val writer:String="",
    val imageUrl:String="",
    val quoteCategory:Int=-1,
    var isLiked: Boolean=false,
    val currentPage:Int=0
): Parcelable

fun QuoteForOrderModel.toQuote():Quote{
    return Quote(id,quote,writer,imageUrl,quoteCategory,isLiked)
}

