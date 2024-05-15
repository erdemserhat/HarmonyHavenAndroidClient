package com.erdemserhat.harmonyhaven.presentation.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "HarmonyHaven",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Giriş Yap",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Kayıt Ol",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("E-Posta") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Şifre") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /*TODO: Handle login*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Giriş Yap")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ya da",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { /*TODO: Handle Google login*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = "Google Login",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            IconButton(onClick = { /*TODO: Handle Twitter login*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = "Twitter Login",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            IconButton(onClick = { /*TODO: Handle Facebook login*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = "Facebook Login",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { /*TODO: Handle forgot password*/ }) {
            Text(
                text = "Şifremi Unuttum",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}
