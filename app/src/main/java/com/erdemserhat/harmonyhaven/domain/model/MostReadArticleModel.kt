package com.erdemserhat.harmonyhaven.domain.model

import androidx.annotation.DrawableRes

data class MostReadArticleModel(
    val title:String,
    val description:String,
    @DrawableRes
    val image:Int
)
