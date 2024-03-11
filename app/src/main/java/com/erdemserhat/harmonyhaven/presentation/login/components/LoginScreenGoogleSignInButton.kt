package com.erdemserhat.harmonyhaven.presentation.login.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite

@Composable
fun LoginScreenGoogleSignInButton(
    modifier: Modifier = Modifier,
    leadingIconId: Int = R.drawable.google_sign_in_icon,
    buttonText: String = stringResource(R.string.sign_in),
    onClick:()->Unit = {}

) {
    OutlinedButton(
        modifier = Modifier
            .size(width = 195.dp, height = 40.dp),
        onClick = { onClick() },
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = harmonyHavenWhite,
        )
    ) {
        Icon(
            painterResource(id = leadingIconId),
            tint = Color.Unspecified,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize),
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = buttonText, color = Color.Black)

    }


}