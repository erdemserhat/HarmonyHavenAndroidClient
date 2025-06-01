package com.erdemserhat.harmonyhaven.presentation.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainScreenParams(
    var screenNo: Int = -1,
    val articleId: Int = 0
) : Parcelable
