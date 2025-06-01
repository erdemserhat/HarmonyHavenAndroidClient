package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share

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
