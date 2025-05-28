package com.erdemserhat.harmonyhaven.presentation.navigation

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuoteShareScreenParams(
    var quote: String = "",
    var author: String = "",
    val quoteUrl: String = "",
    val bitmap: Bitmap? = null
) : Parcelable
