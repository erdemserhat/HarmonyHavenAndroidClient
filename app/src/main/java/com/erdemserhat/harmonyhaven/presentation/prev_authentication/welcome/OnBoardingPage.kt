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
        image = R.drawable.onboarding_screen_image_1,
        title = "Harmony Haven'a Hoş Geldiniz!",
        description = "Harmony Haven ile gününüzü aydınlatın! En güncel ve ilginç haberleri anında bildirim olarak alın. Ayrıca, kişiselleştirilmiş önerilerle ilgi alanlarınıza özel içeriklere ulaşarak her gününüzü daha anlamlı kılın. Bu yolculukta bize katılın ve bilgi dolu bir deneyimin keyfini çıkarın!"
    )

    data object Second: OnBoardingPage(
        image = R.drawable.onboarding_screen_image_2,
        title = "Size Özel Olarak Hazırlanmış Bildirimler Alın",
        description = "Kişiselleştirilmiş bildirimler alın ve ilham verici içeriklerle tanışın. Zor zamanlarda destek bulun ve her zaman yanınızda bir dost hissini yaşayın."
    )

    data object Third: OnBoardingPage(
        image = R.drawable.onboarding_screen_image_3,
        title = "Günlük Olarak Yeni Bilgiler Öğrenin",
        description = "Farklı yazarların derlediği ve her gün güncellenen ilham verici sözlere göz atın. Ayrıca, günlük olarak hayatınıza değer katacak yeni bilgiler öğrenin ve kendinizi sürekli geliştirin"
    )

}