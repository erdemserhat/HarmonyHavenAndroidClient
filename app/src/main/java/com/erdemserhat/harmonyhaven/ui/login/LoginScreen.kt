package com.erdemserhat.harmonyhaven.ui.login

import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.navigation.Screens
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite
import com.erdemserhat.harmonyhaven.util.customFontFamilyJunge

@Composable
fun LoginScreenContent(navController: NavController) {
    //Content of Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.SpaceBetween,


        ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HarmonyHavenGreetingLogo(
                modifier = Modifier
                    .padding(top = 30.dp)
            )
            HarmonyHavenGreetingTitle(
                modifier = Modifier
                    .padding(top = 5.dp)
            )

            HarmonyHavenGreetingText(
                modifier = Modifier
                    .padding(10.dp)
            )

        }

        Column(
            modifier = Modifier
                .padding(bottom = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                InputText(placeholderText = stringResource(R.string.e_mail))
                InputPasswordText(label = stringResource(id = R.string.password))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                RememberCheckBox(stringResource(R.string.remember_me))
                Spacer(modifier = Modifier.size(60.dp))
                TextButton(stringResource(R.string.forgot_password), onClick = {navController.navigate(Screens.PasswordReset)})
            }
            Spacer(modifier = Modifier.size(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HarmonyHavenGreetingButton(stringResource(id = R.string.sign_in))
                Spacer(modifier = Modifier.size(10.dp))
                ButtonWithIcon(
                    R.drawable.google_sign_in_icon,
                    stringResource(R.string.sign_in_via_google)
                )


                Spacer(modifier = Modifier.size(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Don't Have Account?")
                    TextButton(
                        onClick = { navController.navigate(Screens.Register) }
                    ) {
                        Text(stringResource(R.string.sign_up), color = harmonyHavenGreen)
                    }
                }

            }
        }


    }

}

@Composable
fun LoginScreen(navController: NavController) {
    ScreenWithBackground(
        content = { LoginScreenContent(navController = navController) },
        backgroundImageId = R.drawable.login_register_background
    )

}

@Preview
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)

}
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
fun TextButton(buttonText: String, onClick:()->Unit={}) {

    TextButton(onClick = onClick) {
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
fun HarmonyHavenGreetingButton(buttonText: String) {

    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 20.dp),
        modifier = Modifier
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