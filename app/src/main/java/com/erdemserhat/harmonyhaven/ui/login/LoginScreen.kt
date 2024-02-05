package com.erdemserhat.harmonyhaven.ui.login

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.util.customFontFamilyJunge

@Composable
fun LoginScreen(navController:NavController) {
    Box(

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
                InputText(label = "E-mail")
                InputPasswordText(label = "Password")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                RememberCheckBox("Remember me")
                Spacer(modifier = Modifier.size(60.dp))
                TextButton("Forgot Password")
            }
            Spacer(modifier = Modifier.size(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginRegisterButtonEmail("Sign in")
                Spacer(modifier = Modifier.size(10.dp))
                ButtonWithIcon(R.drawable.google,"Sign in via Google")


                Spacer(modifier = Modifier.size(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Don't Have Account?")
                    TextButton(
                        onClick = {navController.navigate("register_screen")}
                        ){
                        Text("Sign-Up", color = Color(0xff436850))
                    }
                }

            }
        }




    }

}
}

@Preview
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController =navController)

}


@Composable
fun InputText(label: String) {
    var text by rememberSaveable {
        mutableStateOf("")

    }
    OutlinedTextField(
        modifier = Modifier
            .size(width = 370.dp, height = 60.dp),
        value = text,
        onValueChange = { text = it },
        label = { Text(text = label) },
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

@Composable
fun InputPasswordText(label: String) {
    var password by rememberSaveable {
        mutableStateOf("")
    }

    var passwordHidden by rememberSaveable {
        mutableStateOf(true)
    }

    val visibilityIcon = if (passwordHidden) {
        painterResource(id = R.drawable.visibility_fill0_wght400_grad0_opsz24)
    } else {
        painterResource(id = R.drawable.visibility_off_fill0_wght400_grad0_opsz24)
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

@Composable
fun RememberCheckBox(text:String) {
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
            colors =CheckboxDefaults.colors(
                checkedColor = Color(0xff436850),
            ),
            checked = checkedState,
            onCheckedChange = { checkedState = !checkedState },
        )
        Text(text = text)

    }
}

@Composable
fun TextButton(buttonText:String) {

    TextButton(onClick = {  }) {
        Text(text = buttonText, color = Color(0xff436850))
    }

}

@Composable
fun ButtonWithIcon(painterId:Int,buttonText: String) {
    OutlinedButton(
        modifier = Modifier
            .size(width = 195.dp, height = 40.dp),
        onClick = { /*TODO*/ },
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xffD9D9D9),
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
@Composable
fun LoginRegisterButtonEmail(buttonText:String){

    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 20.dp),
        modifier = Modifier
            .size(width = 200.dp,40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff436850)
        )


    ) {
        Text(text = buttonText)

    }

}

