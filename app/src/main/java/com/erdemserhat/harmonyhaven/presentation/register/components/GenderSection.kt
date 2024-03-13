package com.erdemserhat.harmonyhaven.presentation.register.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.domain.model.Gender
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen


@Composable
fun GenderSection(
    gender: Gender,
    onGenderSelected: (Gender) -> Unit
) {

    //var selectedGender by remember { mutableStateOf(Gender.Male) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Gender")
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            GenderIconDev(gender = Gender.Female, isSelected = gender==Gender.Female, onClick = onGenderSelected)
            GenderIconDev(gender = Gender.Male, isSelected = gender==Gender.Male, onClick = onGenderSelected)
            GenderIconDev(gender = Gender.Other, isSelected = gender==Gender.Other, onClick = onGenderSelected)


        }
    }
}

@Composable
fun GenderIconDev(
    gender: Gender,
    isSelected: Boolean,
    onClick: (Gender) -> Unit
) {
    val colorFilter =
        if (isSelected) androidx.compose.ui.graphics.ColorFilter.tint(color = harmonyHavenGreen) else null

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(

            painter = painterResource(id = gender.iconResId),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true, radius = 25.dp)
                ) { onClick(gender) },
            colorFilter = colorFilter
        )
        Text(gender.name)

    }
}