package com.erdemserhat.harmonyhaven.presentation.prev_authentication.login.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun LoginScreenLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonText: String = stringResource(id = R.string.sign_in),


    ) {


    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 20.dp),
        modifier = modifier
            .size(width = 200.dp, 40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = harmonyHavenGreen
        )


    ) {
        Text(text = buttonText)

    }

}