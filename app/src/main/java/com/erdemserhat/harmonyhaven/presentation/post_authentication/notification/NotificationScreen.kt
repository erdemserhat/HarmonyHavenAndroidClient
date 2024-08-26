package com.erdemserhat.harmonyhaven.presentation.post_authentication.notification

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import com.erdemserhat.harmonyhaven.presentation.navigation.MainScreenParams
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenButton
import com.erdemserhat.harmonyhaven.ui.theme.customFontInter
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun convertTimestampToTurkishDate(timestamp: Long): String {
    val date = java.util.Date(timestamp * 1000) // Timestamp'i milisaniyeye çeviriyoruz
    val sdf = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("tr", "TR"))
    return sdf.format(date)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel()

) {
    //val notifications by viewModel.allNotifications.observeAsState(initial = emptyList())
    val notifications by viewModel.notifications.collectAsState()
    var permissionGranted by remember { mutableStateOf(viewModel.isPermissionGranted()) }

    val context = LocalContext.current
    val activity = context as? Activity
    val window = activity?.window

    SideEffect {

        window?.let {

            WindowCompat.setDecorFitsSystemWindows(it, true)

            it.statusBarColor = Color.White.toArgb()
            it.navigationBarColor = Color.White.toArgb()



            val insetsController = WindowCompat.getInsetsController(it, it.decorView)
            insetsController.isAppearanceLightStatusBars = true
            insetsController.isAppearanceLightNavigationBars = true

        }

    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted = isGranted
            viewModel.updatePermissionStatus(isGranted)

        }
    )
    LaunchedEffect(Unit) {
        viewModel.loadNotifications()
        notificationPermissionLauncher.launch(
            Manifest.permission.POST_NOTIFICATIONS
        )
    }





    Column(
        Modifier
            .fillMaxSize()
            .background(
                Color.White
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (Build.VERSION.SDK_INT >= 31 && !permissionGranted) {
            //Request Permission
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Spacer(modifier = Modifier.size(25.dp))
                Image(
                    painter = painterResource(id = R.drawable.notication),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Harmony Haven, size daha iyi bir deneyim sunmak için bildirimler gönderebilir. Bu bildirimler, ilgi alanlarınıza ve kullanım alışkanlıklarınıza göre özelleştirilmiştir.",
                    modifier = Modifier.widthIn(max = 400.dp), // Metnin genişliği
                    softWrap = true // Satır başı yapma
                )

                Spacer(modifier = Modifier.size(20.dp))
                HarmonyHavenButton(buttonText = "İzin Ver", onClick = {
                    notificationPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }, isEnabled = true)
                Spacer(modifier = Modifier.size(20.dp))

            }

        } else {
            if (notifications.isEmpty()) {
                Spacer(modifier = Modifier.size(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.no_mail),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Henüz bir bildirim yok, en kısa sürede bildirim almaya başlayacaksınız.",
                    textAlign = TextAlign.Center

                )


            } else {
                val scrollState = rememberLazyListState()
                LazyColumn(state = scrollState) {
                    items(notifications) {it
                        NotificationContent(it,navController)
                    }

                    // Loading indicator or more items
                    item {
                        if (viewModel.isLoading) {
                            // Show loading indicator
                            Text(text = "Loading...", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                        }
                    }

                }

                // Detect when user scrolls to the end
                LaunchedEffect(scrollState) {
                    snapshotFlow { scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                        .collect { lastVisibleIndex ->
                            if (lastVisibleIndex != null) {
                                if (lastVisibleIndex >= notifications.size - 1 && !viewModel.isLoading && viewModel.hasMoreData) {
                                    viewModel.loadNotifications()
                                }
                            }
                        }
                }




            }


        }


    }


}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun NotificationScreenPreview() {
    val navController = rememberNavController()
    NotificationScreen(navController = navController)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationContent(notification: NotificationDto,navController: NavController) {
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            text = convertTimestampToTurkishDate(notification.timeStamp),
            fontFamily = customFontInter,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(bottom = 5.dp)
        )
        Box(
            modifier = Modifier
                .width(380.dp)
                .wrapContentHeight()
                .defaultMinSize(minHeight = 100.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = harmonyHavenComponentWhite)
                .clickable {
                    val shouldNavigateToPost = notification.screenCode.startsWith("-1")
                    if(shouldNavigateToPost){
                        val postId = notification.screenCode.drop(2)
                        val bundleArticle = Bundle()
                        bundleArticle.putParcelable("article",
                            ArticlePresentableUIModel(
                                id =postId.toInt())
                        )

                        navController.navigate(
                            route = Screen.Article.route,
                            args = bundleArticle
                        )

                    }else if(notification.screenCode!="1"){
                        val bundleMain = Bundle()
                        val screenCode = notification.screenCode.toInt()
                        bundleMain.putParcelable("params", MainScreenParams(screenNo = screenCode))
                        navController.navigate(
                            route =Screen.Main.route,
                            args = bundleMain)
                    }


                },

            ) {




            Column {
                Text(
                    text = notification.title,
                    fontFamily = customFontInter,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 5.dp, bottom = 10.dp)

                )
                Text(
                    text = notification.content,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    textAlign = TextAlign.Justify,
                    fontFamily = customFontInter,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize

                )

            }

           // Image(
              //  painter = painterResource(id = R.drawable.shareicon),
              //  contentDescription = null,
              //  Modifier
               //     .size(50.dp)
                //    .padding(10.dp)
               //     .align(Alignment.BottomEnd),

               // )

            Image(
                painter = painterResource(id = R.drawable.harmony_haven_icon),
                contentDescription = null,
                Modifier
                    .size(35.dp)
                    .padding(10.dp)
                    .align(Alignment.TopEnd),

                )

        }
    }

}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}


@RequiresApi(Build.VERSION_CODES.O)
fun unixToDateTime(unixTimestamp: Long, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    val instant = Instant.ofEpochSecond(unixTimestamp)
    val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return formatter.format(dateTime)
}
