package com.erdemserhat.harmonyhaven.domain.model

import com.erdemserhat.harmonyhaven.R

enum class Gender(val iconResId: Int) {
    Male(R.drawable.gender_male_icon),
    Female(R.drawable.gender_female_icon),
    Other(R.drawable.gender_other_icon),
    None(0)
}