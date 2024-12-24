package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.presentation.navigation.SharedViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.PostFlow

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun QuoteMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewmodel: QuoteMainViewModel,
    sharedViewModel: SharedViewModel,
    onCommentsClicked:()->Unit,
    onCategoryClicked:()->Unit
) {

    QuoteMainContent(
        modifier = modifier,
        viewmodel = viewmodel,
        navController = navController,
        sharedViewModel = sharedViewModel,
        onCommentsClicked = onCommentsClicked,
        onCategoryClicked = onCategoryClicked
    )

}

@Composable
fun QuoteMainContent(
    modifier: Modifier = Modifier,
    viewmodel: QuoteMainViewModel,
    navController: NavController? = null,
    sharedViewModel:SharedViewModel,
    onCommentsClicked:()->Unit,
    onCategoryClicked:()->Unit
) {

    //
    val quotes = viewmodel.quotes.collectAsState()


    val shouldShowUxDialog1 = viewmodel.shouldShowUxDialog1.collectAsState()

    var permissionGranted by remember { mutableStateOf(viewmodel.isPermissionGranted()) }


    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted = isGranted
            viewmodel.updatePermissionStatus(isGranted)

        }
    )
    LaunchedEffect(Unit) {
        notificationPermissionLauncher.launch(
            Manifest.permission.POST_NOTIFICATIONS
        )
    }






    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        PostFlow(
            modifier = Modifier,
            viewmodel = viewmodel,
            navController = navController,
            sharedViewModel = sharedViewModel,
            onCommentsClicked = onCommentsClicked,
            onCategoryClicked = onCategoryClicked
        )
        var isButtonClicked by remember { mutableStateOf(false) }
        if (shouldShowUxDialog1.value) {

            UxScrollInformer(modifier = Modifier.zIndex(2f),
                onClick = {
                    viewmodel.markScrollTutorialDone(false)
                    isButtonClicked = true
                })

        }


    }

}







