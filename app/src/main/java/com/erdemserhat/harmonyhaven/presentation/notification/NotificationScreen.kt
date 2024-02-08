package com.erdemserhat.harmonyhaven.presentation.notification

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun NotificationScreen(navController: NavController) {
    Text(text = "Notification")

}
@Preview
@Composable
fun NotificationScreenPreview() {
    val navController = rememberNavController()
    NotificationScreen(navController)
}