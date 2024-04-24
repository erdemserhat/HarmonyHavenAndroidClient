package com.erdemserhat.harmonyhaven.presentation.notification

import android.Manifest
import android.app.Activity
import android.app.Notification
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite
import com.erdemserhat.harmonyhaven.util.customFontInter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel()

) {
    val notifications by viewModel.allNotifications.observeAsState(initial = emptyList())



    val permissionsToRequest = arrayOf(
        Manifest.permission.POST_NOTIFICATIONS
    )

    val dialogQueue = viewModel.visiblePermissionDialogQueue


    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.POST_NOTIFICATIONS,
                isGranted = isGranted
            )
        }
    )


    Column(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        harmonyHavenGradientGreen,
                        harmonyHavenGradientWhite
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())

        ) {
            Button(onClick = {
                cameraPermissionResultLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }) {
                androidx.compose.material.Text(text = "Give Permission To Take Notification ( Tiramisu )1")
            }


            Log.d("erdem1212",viewModel.allNotifications.value.toString())



        }

        LazyColumn {
            items(notifications){
                NotificationContent(notification = it)
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
fun NotificationContent(notification: NotificationEntity) {

    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            text = unixToDateTime( notification.date/1000),
            fontFamily = customFontInter,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(bottom = 5.dp)
        )
        Box(
            modifier = Modifier
                .width(380.dp)
                .wrapContentHeight()
                .background(color = harmonyHavenComponentWhite, shape = RoundedCornerShape(20.dp)),

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
                    text = notification.body,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                        .width(310.dp),
                    textAlign = TextAlign.Justify,
                    fontFamily = customFontInter,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize

                )

            }

            Image(
                painter = painterResource(id = R.drawable.shareicon),
                contentDescription = null,
                Modifier
                    .size(50.dp)
                    .padding(10.dp)
                    .align(Alignment.BottomEnd),

                )
            Image(
                painter = painterResource(id = R.drawable.harmony_haven_icon),
                contentDescription = null,
                Modifier
                    .size(50.dp)
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