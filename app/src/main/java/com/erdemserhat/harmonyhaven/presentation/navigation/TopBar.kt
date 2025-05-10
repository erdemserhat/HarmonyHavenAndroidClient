package com.erdemserhat.harmonyhaven.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.AlertDialogBase
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenSelectedNavigationBarItemColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    navController: NavController,
    title: String,
    topBarBackgroundColor: Color,
    isMainScreen: Boolean,
    modifier: Modifier = Modifier,
    onExitClicked: () -> Unit = {},
    isNotificationScreen:Boolean


    ) {

    // State to manage the visibility of the dropdown menu
    var expanded by remember { mutableStateOf(false) }
    var shouldShowLogoutAlertDialog by rememberSaveable {
        mutableStateOf(false)
    }

    if (shouldShowLogoutAlertDialog) {
        AlertDialogBase(
            alertTitle = "Çıkış Yap",
            alertBody = "Çıkış yapmak istediğine emin misin?",
            positiveButtonText = "Çıkış Yap",
            negativeButtonText = "Vazgeç",
            onPositiveButtonClicked = {
                onExitClicked()
                navController.navigate(Screen.Login.route)
                shouldShowLogoutAlertDialog = false

            },
            onNegativeButtonClicked = {
                shouldShowLogoutAlertDialog = false
            }) {

        }
    }



    TopAppBar(
        modifier = modifier.drawBehind {
            drawLine(
                color = Color(0xFFE0E0E0), // Alt kenar rengini belirler
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 1.dp.toPx() // Sınır kalınlığını belirler
            )
        },
        navigationIcon = {

        },

        title = {


            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = when (isMainScreen) {
                    true -> FontWeight.Bold
                    false -> FontWeight.Normal
                },

                color = when (isMainScreen) {
                    true -> harmonyHavenSelectedNavigationBarItemColor
                    false -> Color.Black
                }// Beyazdan yeşile çalan renk (RGB: 191, 255, 191)
            )


        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topBarBackgroundColor
        ),
        actions = {

            if(isNotificationScreen){
                IconButton(onClick = {
                    navController.navigate(Screen.NotificationScheduler.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "More options"
                    )
                }
            }


            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More options"
                )
            }
            // Dropdown menu
            DropdownMenu(
                modifier = Modifier.background(harmonyHavenComponentWhite),

                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    navController.navigate(Screen.Profile.route)
                    expanded = false


                }, text = {
                    Text("Hesap Ayarları")
                })

                DropdownMenuItem(onClick = {
                    expanded = false
                    shouldShowLogoutAlertDialog = true


                },text={
                    Text("Çıkış Yap")

                })

            }
        },

        )

}
