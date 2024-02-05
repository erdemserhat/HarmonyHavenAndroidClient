package com.erdemserhat.harmonyhaven.ui.register

import android.graphics.ColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.login.ButtonWithIcon
import com.erdemserhat.harmonyhaven.ui.login.InputPasswordText
import com.erdemserhat.harmonyhaven.ui.login.InputText
import com.erdemserhat.harmonyhaven.ui.login.LoginRegisterButtonEmail
import com.erdemserhat.harmonyhaven.ui.login.LoginScreen
import com.erdemserhat.harmonyhaven.ui.login.RememberCheckBox
import com.erdemserhat.harmonyhaven.ui.login.TextButton
import com.erdemserhat.harmonyhaven.util.customFontFamilyJunge

@Composable
fun RegisterScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Background
        Image(
            painter = painterResource(id = R.drawable._fear),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )


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
                    painter = painterResource(id = R.drawable.group1_6),
                    contentDescription = null,
                    Modifier.padding(top = 30.dp)

                )
                Text(
                    text = "Harmony Haven",
                    fontSize = 40.sp,
                    fontFamily = customFontFamilyJunge

                )

                Text(
                    text = "Harmony Haven Awaits You! Sign Up and Start Exploring. Join Now for Free and Enjoy All the Wonders of Our World!",
                    modifier = Modifier
                        .padding(10.dp),
                    fontSize = 14.sp


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
                    InputText(label = "Name")
                    InputText(label = "Surname")
                    InputText(label = "E-Mail")
                    InputPasswordText(label = "Password")
                    InputPasswordText(label = "Confirm Password")
                    Spacer(modifier = Modifier.size(20.dp))
                    GenderSection()

                }


                Spacer(modifier = Modifier.size(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoginRegisterButtonEmail("Sign up")
                    Spacer(modifier = Modifier.size(10.dp))
                    ButtonWithIcon(R.drawable.google,"Sign in via Google")
                    Spacer(modifier = Modifier.size(10.dp))
                    RememberCheckBox("I accept the terms of use")




                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Already Have an Account?")
                    TextButton(
                        onClick = {navController.navigate("login_screen")}
                    ){
                        Text("Sign-In", color = Color(0xff436850))
                    }
                }
            }






        }

    }
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
    val colorFilter = if (isSelected) androidx.compose.ui.graphics.ColorFilter.tint(color = Color(0xff436850)) else null

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = gender.iconResId),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clickable { onClick() },
            colorFilter = colorFilter
        )
        Text(gender.name)

    }

}

enum class Gender(val iconResId: Int,) {
    Male(R.drawable.male_icon,),
    Female(R.drawable.female_icon,),
    Other(R.drawable.fiber_manual_record_fill0_wght400_grad0_opsz24),
    None(0)
}