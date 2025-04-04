package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.DefaultAppFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
  Column {
      Scaffold(
          topBar = {
              TopAppBar(
                  elevation = 0.dp,
                  backgroundColor = Color.White,
                  contentColor = Color.Transparent,
                  title = { Text(text = "Ayarlar") },
                  navigationIcon = {
                      androidx.compose.material.IconButton(onClick = { /* Geri gitme işlemi */ }) {
                          Icon(
                              painter = painterResource(id = R.drawable.return_back_icon),
                              contentDescription = "Geri",
                              modifier = Modifier
                                  .clip(RoundedCornerShape(50.dp))
                                  .size(32.dp)
                                  .clickable {
                                      navController.popBackStack()
                                  }
                          )
                      }
                  }
              )
          }

      ){padding->
          SettingsScreenContent(navController, modifier = Modifier.background(Color.White).padding(padding))
      }
  }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()

    
}

@Composable
fun SettingsScreenContent(navController: NavController,modifier: Modifier) {

    var shouldShowLogoutAlertDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

///////////////////////////////Exit Alert Dialog/////////////////////////////
        if (shouldShowLogoutAlertDialog) {
            AlertDialogBase(
                alertTitle = "Çıkış Yap",
                alertBody = "Çıkış yapmak istediğine emin misin?",
                positiveButtonText = "Çıkış Yap",
                negativeButtonText = "Vazgeç",
                onPositiveButtonClicked = {

                    navController.navigate(Screen.Login.route)
                    
                    navController.navigate("Login")


                    shouldShowLogoutAlertDialog = false

                },
                onNegativeButtonClicked = {
                    shouldShowLogoutAlertDialog = false
                }) {

            }
        }
///////////////////////////////Exit Alert Dialog/////////////////////////////



        // Border ile ayrılmış 3 adet buton
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
        ) {
            SettingsButton(
                icon = painterResource(id = R.drawable.account_icon),
                title = "Hesap Bilgileri",
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            12.dp, 12.dp, 0.dp, 0.dp
                        )
                    ),
                onButtonClicked = {
                    navController.navigate(Screen.Profile.route)
                }
            )

            Divider(
                color = Color(0xFFD9D9D9),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

          //  SettingsButton(
            //    icon = painterResource(id = R.drawable.saved_articles_icon),
             //   title = "Saved articles",
             //   modifier = Modifier.clip(
              //      RoundedCornerShape(
               //         0.dp, 0.dp, 12.dp, 12.dp
                //    )
               // ),
               // onButtonClicked = {
                //    navController.navigate(Screen.SavedArticles.route)
               // }
          //  )
        }

        Spacer(modifier = Modifier.size(25.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
        ) {
            //SettingsButton(
              //  icon = painterResource(id = R.drawable.information_icon),
               // title = "Hakkımızda",
               // modifier = Modifier.clip(
                 //   RoundedCornerShape(
                   //     12.dp, 12.dp, 0.dp, 0.dp
                  //  )
              //  ),
              //  onButtonClicked = {
              //     navController.navigate(Screen.AboutUs.route)
              //  }
          //  )

           // Divider(
            //    color = Color(0xFFD9D9D9),
           //     thickness = 1.dp,
            //    modifier = Modifier.padding(horizontal = 16.dp)
         //   )

         //   SettingsButton(
              //  icon = painterResource(id = R.drawable.report_icon),
              //  title = "Report problem",
              //  modifier = Modifier
           // )

            Divider(
                color = Color(0xFFD9D9D9),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

          //  SettingsButton(
             //   icon = painterResource(id = R.drawable.terms_icon),
              //  title = "Terms and conditions",
               // modifier = Modifier.clip(
                //    RoundedCornerShape(
                  //      0.dp, 0.dp, 12.dp, 12.dp
                  //  )
              //  )
           // )
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
                title = "Çıkış Yap",
                titleColor = Color.Red,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            12.dp, 12.dp, 12.dp, 12.dp
                        )
                    ),
                onButtonClicked = {shouldShowLogoutAlertDialog=true}

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
    modifier: Modifier = Modifier,
    onButtonClicked:()->Unit ={}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = { onButtonClicked() })
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
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFFD9D9D9)
        )
    }
}


@Composable
fun AlertDialogBase(
    alertTitle: String,
    alertBody: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveButtonClicked: () -> Unit,
    onNegativeButtonClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        backgroundColor = Color.White,
        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = alertTitle,
                fontFamily = DefaultAppFont,
                color = Color.Black

            )
        },
        text = {
            Text(
                text = alertBody,
                fontFamily = DefaultAppFont,
                color = Color.Black

            )
        },
        confirmButton = {
            TextButton(onClick = {
                onPositiveButtonClicked()
            }
            ) {
                Text(
                    text = positiveButtonText,
                    color = Color.Red,
                    fontFamily = DefaultAppFont

                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onNegativeButtonClicked()
            }) {
                Text(
                    text = negativeButtonText,
                    fontFamily = DefaultAppFont

                )
            }
        }
    )
}


@Composable
fun AccountInformation(
    alertTitle: String,
    alertBody: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveButtonClicked: () -> Unit,
    onNegativeButtonClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        backgroundColor = Color.White,
        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = alertTitle,
                fontFamily = DefaultAppFont

            )
        },
        text = {
            Text(
                text = alertBody,
                fontFamily = DefaultAppFont

            )
        },
        confirmButton = {
            TextButton(onClick = {
                onPositiveButtonClicked()
            }
            ) {
                Text(
                    text = positiveButtonText,
                    color = Color.Red,
                    fontFamily = DefaultAppFont

                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onNegativeButtonClicked()
            }) {
                Text(
                    text = negativeButtonText,
                    fontFamily = DefaultAppFont

                )
            }
        }
    )
}


