package com.erdemserhat.harmonyhaven.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


//My App Colors

    val harmonyHavenGreen = Color(0xff436850)
val harmonyHavenWhite =Color(0xffD9D9D9)
val harmonyHavenBottomAppBarContainerColor = Color(0xffD4E8E7)
val harmonyHavenSelectedNavigationBarItemColor=Color(0xff12372A)
val harmonyHavenIndicatorColor = Color(0xff9DC8C1)
val harmonyHavenDarkGreenColor = Color(0xff12372A)

val harmonyHavenGradientGreen = Color(0xff92E3A9)
val harmonyHavenGradientWhite=Color(0xffFFFFFF)
val harmonyHavenComponentWhite = Color(0xffE5F5F5)
val harmonyHavenTitleTextColor = Color(0xff605757)


val ColorScheme.textColor
@Composable
get() = if(isSystemInDarkTheme()) Color.Black else Color.Black