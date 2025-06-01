package com.erdemserhat.harmonyhaven.presentation.navigation.navbar.navbar

import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen


object NavigationBarItems {
     val items = listOf(
        NavigationBarItem(
            title = "İçerikler",
            hasNews = false,
            badgeCount = null,
            route = Screen.Home.route,
            selectedIconDarkIcon = R.drawable.homewhitefilled,
            selectedIconWhiteIcon = R.drawable.homeblackfilled,
            unSelectedIconDarkIcon = R.drawable.homewhiteunfilled,
            unSelectedIconWhiteIcon = R.drawable.homeblackunfilled

        ),


        NavigationBarItem(
            title = "Sözler",
            hasNews = false,
            badgeCount = null,
            Screen.Quotes.route,
            selectedIconDarkIcon = R.drawable.quotewhitefilled,
            selectedIconWhiteIcon = R.drawable.quoteblackfilled,
            unSelectedIconDarkIcon = R.drawable.quotewhiteunfilled,
            unSelectedIconWhiteIcon = R.drawable.quoteblackunfilled
        ),


        NavigationBarItem(
            title = "Harmonia",
            hasNews = false,
            badgeCount = null,
            route = Screen.ChatIntroScreen.route,
            selectedIconDarkIcon = R.drawable.message_f_white,
            selectedIconWhiteIcon = R.drawable.message_f_black,
            unSelectedIconDarkIcon = R.drawable.message_uf_white,
            unSelectedIconWhiteIcon = R.drawable.message_uf_black
        ),


        NavigationBarItem(
            title = "Enneagram",
            hasNews = false,
            badgeCount = null,
            route = Screen.AccountInformationScreen.route,
            selectedIconDarkIcon = R.drawable.enneagram_unfilled_black,
            selectedIconWhiteIcon = R.drawable.enneagran_filled_black,
            unSelectedIconDarkIcon = R.drawable.enneagram_white,
            unSelectedIconWhiteIcon = R.drawable.enneagram_unfilled_black
        ),

        NavigationBarItem(
            title = "Profile",
            hasNews = false,
            badgeCount = null,
            route = Screen.AccountInformationScreen.route,
            selectedIconDarkIcon = R.drawable.profile_unfilled_black,
            selectedIconWhiteIcon = R.drawable.profile_filled_black,
            unSelectedIconDarkIcon = R.drawable.profile_unfilled_white,
            unSelectedIconWhiteIcon = R.drawable.profile_unfilled_black
        )


    )
}

