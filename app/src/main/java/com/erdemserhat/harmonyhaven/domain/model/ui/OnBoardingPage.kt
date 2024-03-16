package com.erdemserhat.harmonyhaven.domain.model.ui

import androidx.annotation.DrawableRes
import com.erdemserhat.harmonyhaven.R

sealed class OnBoardingPage(
    @DrawableRes
    val image:Int,
    val title:String,
    val description:String
){
    data object First: OnBoardingPage(
        image = R.drawable.onboarding_screen_image_1,
        title = "Welcome to Harmony Haven!",
        description = "Discover something new every day with personalized notifications and daily tips on personal development, health, relationships, and more. Let's embark on this journey together!"
    )

    data object Second: OnBoardingPage(
        image = R.drawable.onboarding_screen_image_2,
        title = "Get Personalized Notifications",
        description = "Receive personalized notifications and discover inspiring content. Find support in tough times, and always have a companion by your side."
    )

    data object Third: OnBoardingPage(
        image = R.drawable.onboarding_screen_image_3,
        title = "Explore New Insights Daily",
        description = "Discover something new every day with personalized notifications and daily tips on personal development, health, relationships, and more. Let's embark on this journey together!"
    )

}