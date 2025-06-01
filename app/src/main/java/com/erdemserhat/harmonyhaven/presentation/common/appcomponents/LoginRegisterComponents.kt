package com.erdemserhat.harmonyhaven.presentation.common.appcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.georgiaFont
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun HarmonyHavenGreetingButton(
    buttonText: String,onClick: () -> Unit={},
    modifier: Modifier = Modifier

) {

    Button(
        onClick =  onClick ,
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
////////////////////////////// At the End Of Component //////////////////////////////////////


@Composable
fun HarmonyHavenGreetingLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.harmony_haven_icon),
        contentDescription = null,
        modifier = modifier.aspectRatio(16f / 9f) // Aspect ratio burada belirtiliyor


    )
}

@Composable
fun HarmonyHavenGreetingTitle(modifier: Modifier,text: String ="",fontSize:Int=24) {
    Text(

        text = text,
        fontSize = fontSize.sp,
        fontFamily = georgiaFont,
        modifier = modifier.padding(10.dp),
        color = harmonyHavenDarkGreenColor

    )
}

@Composable
fun HarmonyHavenGreetingText(modifier: Modifier) {
    Text(
        text = stringResource(R.string.login_greeting_text),
        modifier = modifier,
        fontSize = 14.sp,
        textAlign = TextAlign.Center


    )

}