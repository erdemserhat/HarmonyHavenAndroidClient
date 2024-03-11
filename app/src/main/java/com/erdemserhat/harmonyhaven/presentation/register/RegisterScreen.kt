package com.erdemserhat.harmonyhaven.presentation.register


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.Gender
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.appcomponents.ButtonWithIcon
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenGreetingButton
import com.erdemserhat.harmonyhaven.presentation.appcomponents.InputPasswordText
import com.erdemserhat.harmonyhaven.presentation.appcomponents.InputText
import com.erdemserhat.harmonyhaven.presentation.appcomponents.RememberCheckBox
import com.erdemserhat.harmonyhaven.presentation.appcomponents.ScreenWithBackground
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.util.customFontFamilyJunge

@Composable
fun RegisterScreenContent(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),


        //horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,


        ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.harmony_haven_icon),
                contentDescription = null,
                Modifier.padding(top = 30.dp)

            )
            Text(
                text = "Harmony Haven",
                fontSize = 40.sp,
                fontFamily = customFontFamilyJunge

            )

            Text(
                text = stringResource(R.string.login_register_greeting_text),
                modifier = Modifier
                    .padding(10.dp),
                fontSize = 14.sp,
                textAlign = TextAlign.Center


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
                InputText(placeholderText = stringResource(R.string.name))
                InputText(placeholderText = stringResource(R.string.surname))
                InputText(placeholderText = stringResource(id = R.string.e_mail))
                InputPasswordText(label = stringResource(id = R.string.password))
                InputPasswordText(label = stringResource(R.string.confirm_password))
                Spacer(modifier = Modifier.size(20.dp))
                GenderSection()

            }


            Spacer(modifier = Modifier.size(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HarmonyHavenGreetingButton(stringResource(id = R.string.sign_up))
                Spacer(modifier = Modifier.size(10.dp))
                ButtonWithIcon(
                    R.drawable.google_sign_in_icon,
                    stringResource(id = R.string.sign_in_via_google)
                )
                Spacer(modifier = Modifier.size(10.dp))
                RememberCheckBox(stringResource(R.string.terms_use))


            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.already_have_an_account))
                TextButton(
                    onClick = { navController.navigate(Screen.Login.route) }
                ) {
                    Text(stringResource(R.string.sign_in), color = harmonyHavenGreen)
                }
            }
        }


    }

}

@Composable
fun RegisterScreen(navController: NavController) {
    ScreenWithBackground(
        content = { RegisterScreenContent(navController)},
        backgroundImageId = R.drawable.login_register_background)

}

@Preview
@Composable
fun RegisterScreenPreview() {
    val navController = rememberNavController()

    RegisterScreen(navController)

}

@Preview(showBackground = true)
@Composable
fun GenderPreview() {
    GenderSection()
}

@Composable
fun GenderSection() {
    var selectedGender by remember { mutableStateOf(Gender.None) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Gender")
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            GenderIcon(
                gender = Gender.Male,
                isSelected = selectedGender == Gender.Male,
                onClick = { selectedGender = Gender.Male }
            )
            Spacer(modifier = Modifier.size(25.dp))
            GenderIcon(
                gender = Gender.Female,
                isSelected = selectedGender == Gender.Female,
                onClick = { selectedGender = Gender.Female }
            )
            Spacer(modifier = Modifier.size(25.dp))
            GenderIcon(
                gender = Gender.Other,
                isSelected = selectedGender == Gender.Other,
                onClick = { selectedGender = Gender.Other }
            )
        }
    }
}

@Composable
fun GenderIcon(
    gender: Gender,
    isSelected: Boolean,
    onClick: () -> Unit
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
                ) { onClick() },
            colorFilter = colorFilter
        )
        Text(gender.name)

    }

}

