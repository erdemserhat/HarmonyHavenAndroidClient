package com.erdemserhat.harmonyhaven.presentation.prev_authentication.welcome

import androidx.annotation.DrawableRes
import com.erdemserhat.harmonyhaven.R

sealed class OnBoardingPage(
    @DrawableRes
    val image:Int,
    val title:String,
    val description:String
){
    data object First: OnBoardingPage(
        image = R.drawable.harmonyhaven_icon,
        title = "Hoş Geldiniz!",
        description = "Harmony Haven ile gününüzü aydınlatın. Güncel haberler ve kişiselleştirilmiş önerilerle her anınızı daha anlamlı kılın!"
    )

    data object Second: OnBoardingPage(
        image = R.drawable.onboarding_screen_image_2,
        title = "Özel Bildirimler",
        description = "Kişiselleştirilmiş bildirimlerle ilham verici içeriklere ulaşın ve zor zamanlarda destek bulun."
    )

    data object Third: OnBoardingPage(
        image = R.drawable.onboarding_screen_image_3,
        title = "Günlük Bilgiler",
        description = "Her gün yeni bilgiler öğrenin ve ilham verici sözlerle kendinizi geliştirin."
    )

}