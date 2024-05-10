package com.erdemserhat.harmonyhaven.presentation.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R

@Composable
fun SettingsScreen(navController: NavController) {
    SettingsScreenContent(navController)
}

@Preview(
    showBackground = true
)
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    SettingsScreenContent(navController)
}

@Composable
fun SettingsScreenContent(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        Text(
            text = "Settings",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Border ile ayrılmış 3 adet buton
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
        ) {
            SettingsButton(
                icon = painterResource(id = R.drawable.account_icon),
                title = "Account information",
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Divider(
                color = Color(0xFFD9D9D9),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            SettingsButton(
                icon = painterResource(id = R.drawable.saved_articles_icon),
                title = "Saved articles",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.size(25.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
        ) {
            SettingsButton(
                icon = painterResource(id = R.drawable.information_icon),
                title = "About us",
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Divider(
                color = Color(0xFFD9D9D9),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            SettingsButton(
                icon = painterResource(id = R.drawable.report_icon),
                title = "Report problem",
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Divider(
                color = Color(0xFFD9D9D9),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            SettingsButton(
                icon = painterResource(id = R.drawable.terms_icon),
                title = "Terms and conditions",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.size(25.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
        ) {
            SettingsButton(
                icon = painterResource(id = R.drawable.logout_icon),
                iconColor = Color.Red,
                title = "Sign out",
                titleColor = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun SettingsButton(
    icon: Painter,
    title: String,
    titleColor: Color = Color.Black,
    iconColor: Color = Color(0xFF222222),
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = {})
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                color = titleColor,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFFD9D9D9)
        )
    }
}
