package com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreenTopBar(
    navController: NavController,
    onShareButtonClicked: () -> Unit,
    color: Color,
    isContentLight: Boolean = false,
    modifier: Modifier = Modifier
) {

    TopAppBar(
        modifier = modifier.background(Color.Transparent),
        title = { },
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.return_back_icon),
                    contentDescription = "Back",
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .size(32.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onShareButtonClicked
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.shareicon),
                    contentDescription = "Share",
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .size(23.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color,
            navigationIconContentColor = if (isContentLight) Color.White else Color.Black,
            actionIconContentColor = if (isContentLight) Color.White else Color.Black
        ),

    )
}