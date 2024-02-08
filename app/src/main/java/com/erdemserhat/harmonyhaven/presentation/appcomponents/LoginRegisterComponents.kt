package com.erdemserhat.harmonyhaven.presentation.appcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite
import com.erdemserhat.harmonyhaven.util.customFontFamilyJunge

/////////////////////////////// Reusable Composable Components ////////////////////////////////////


//////////////////////////////////////// Component ////////////////////////////////////////////////
/**
 * Creates an input text field.
 *
 * @param label The label for the input text field.
 */
@Composable
fun InputText(placeholderText: String) {
    var text by rememberSaveable {
        mutableStateOf("")

    }
    OutlinedTextField(
        modifier = Modifier
            .size(width = 370.dp, height = 60.dp),
        value = text,
        onValueChange = { text = it },
        label = { Text(text = placeholderText) },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Black,
            focusedLabelColor = Color.Black

        ),
        singleLine = true
    )
}
////////////////////////////// At the End Of Component //////////////////////////////////////


///////////////////////////////////// Component /////////////////////////////////////////////
/**
 * creates an password text field.
 *
 * @param label placeholder text for password field
 */
@Composable
fun InputPasswordText(label: String) {
    var password by rememberSaveable {
        mutableStateOf("")
    }

    var passwordHidden by rememberSaveable {
        mutableStateOf(true)
    }

    val visibilityIcon = if (passwordHidden) {
        painterResource(id = R.drawable.visibility_eye_icon)
    } else {
        painterResource(id = R.drawable.visibility_off_eye_icon)
    }

    OutlinedTextField(
        modifier = Modifier
            .size(width = 370.dp, height = 60.dp),
        value = password,
        onValueChange = { password = it },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Black,
            focusedLabelColor = Color.Black
        ),
        singleLine = true,
        label = { Text(text = label) },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val description =
                    if (passwordHidden) stringResource(id = R.string.show_password) else stringResource(
                        id = R.string.hide_password
                    )
                Icon(painter = visibilityIcon, contentDescription = description)
            }
        }
    )
}
////////////////////////////// At the End Of Component //////////////////////////////////////


///////////////////////////////////// Component /////////////////////////////////////////////
@Composable
fun RememberCheckBox(text: String) {
    var checkedState by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        Modifier
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            colors = CheckboxDefaults.colors(
                checkedColor = harmonyHavenGreen,
            ),
            checked = checkedState,
            onCheckedChange = { checkedState = !checkedState },
        )
        Text(text = text)

    }
}
////////////////////////////// At the End Of Component //////////////////////////////////////


///////////////////////////////////// Component /////////////////////////////////////////////
@Composable
fun HarmonyHavenTextButton(buttonText: String, onClick:()->Unit={}) {

    androidx.compose.material3.TextButton(onClick = onClick) {
        Text(text = buttonText, color = harmonyHavenGreen)
    }

}
////////////////////////////// At the End Of Component //////////////////////////////////////


///////////////////////////////////// Component /////////////////////////////////////////////
@Composable
fun ButtonWithIcon(painterId: Int, buttonText: String) {
    OutlinedButton(
        modifier = Modifier
            .size(width = 195.dp, height = 40.dp),
        onClick = { /*TODO*/ },
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = harmonyHavenWhite,
        )
    ) {
        Icon(
            painterResource(id = painterId),
            tint = Color.Unspecified,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize),
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = buttonText, color = Color.Black)

    }


}
////////////////////////////// At the End Of Component //////////////////////////////////////


///////////////////////////////////// Component /////////////////////////////////////////////
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
        modifier = modifier

    )
}

@Composable
fun HarmonyHavenGreetingTitle(modifier: Modifier) {
    Text(
        text = stringResource(R.string.harmony_haven),
        fontSize = 40.sp,
        fontFamily = customFontFamilyJunge,
        modifier = modifier

    )
}

@Composable
fun HarmonyHavenGreetingText(modifier: Modifier) {
    Text(
        text = stringResource(R.string.login_greeting_text),
        modifier = Modifier,
        fontSize = 14.sp,
        textAlign = TextAlign.Center


    )

}