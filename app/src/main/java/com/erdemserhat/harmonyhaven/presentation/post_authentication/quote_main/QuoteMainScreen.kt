package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.PostFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun QuoteMainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewmodel: QuoteMainViewModel = hiltViewModel()
) {

    QuoteMainContent(
        modifier = modifier,
        viewmodel = viewmodel,
        navController = navController
    )

}

@Composable
fun QuoteMainContent(
    modifier: Modifier = Modifier,
    viewmodel: QuoteMainViewModel,
    navController: NavController? = null
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
            quoteList = quotes.value,
            modifier = Modifier,
            viewmodel = viewmodel,
            navController = navController
        )
        var isButtonClicked by remember { mutableStateOf(false) }
        if (shouldShowUxDialog1.value) {

            UxScrollInformer(modifier = Modifier.zIndex(2f),
                onClick = {
                    viewmodel.setShouldShowUxDialog1(false)
                    isButtonClicked = true
                })

        }


    }

}







