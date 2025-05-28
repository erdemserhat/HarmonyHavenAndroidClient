package com.erdemserhat.harmonyhaven.presentation.common.appcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.customFontFamilyJunge
import com.erdemserhat.harmonyhaven.ui.theme.georgiaFont
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenTitleTextColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite

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
fun ScreenWithBackground(content: @Composable () -> Unit, backgroundImageId: Int) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Arka plan Ekran Resmi
        Image(
            painter = painterResource(backgroundImageId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        content()
    }
}

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