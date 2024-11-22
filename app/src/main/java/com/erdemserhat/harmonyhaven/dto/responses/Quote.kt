package com.erdemserhat.harmonyhaven.dto.responses
import kotlinx.serialization.Serializable


@Serializable
data class Quote(
    val id:Int=-1,
    val quote:String="",
    val writer:String="",
    val imageUrl:String="",
    val quoteCategory:Int=-1,
    var isLiked: Boolean=false
){}

data class QuoteForOrderModel(
    val id:Int=-1,
    val quote:String="",
    val writer:String="",
    val imageUrl:String="",
    val quoteCategory:Int=-1,
    var isLiked: Boolean=false,
    val currentPage:Int=0
){}

fun QuoteForOrderModel.toQuote():Quote{
    return Quote(id,quote,writer,imageUrl,quoteCategory,isLiked)
}

