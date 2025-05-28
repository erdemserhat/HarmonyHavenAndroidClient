package com.erdemserhat.harmonyhaven.presentation.navigation.navbar.navbar

data class NavigationBarItem(
    val title: String,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route: String,
    val selectedIconDarkIcon: Int,
    val selectedIconWhiteIcon: Int,
    val unSelectedIconDarkIcon: Int,
    val unSelectedIconWhiteIcon: Int

)
