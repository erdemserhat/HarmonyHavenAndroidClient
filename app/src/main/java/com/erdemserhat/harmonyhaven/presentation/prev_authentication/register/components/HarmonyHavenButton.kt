package com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun HarmonyHavenButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled:Boolean

) {

    Button(
        onClick =  onClick ,
        shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 20.dp),
        modifier = modifier
            .size(width = 200.dp, 40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = harmonyHavenGreen
        ),
        enabled = isEnabled


    ) {
        Text(text = buttonText)

    }

}