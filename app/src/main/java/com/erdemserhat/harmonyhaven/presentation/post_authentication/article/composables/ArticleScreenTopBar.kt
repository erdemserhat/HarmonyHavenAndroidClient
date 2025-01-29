package com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
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

@Composable
fun ArticleScreenTopBar(
    onTextFontPlusClicked: () -> Unit,
    onTextFontMinusClicked: () -> Unit,
    navController: NavController,
    onShareButtonClicked:()->Unit


) {
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
    TopAppBar(
        modifier = Modifier,
        elevation = 0.dp,
        backgroundColor = if(false) Color.Black else Color.White,
        contentColor = if(false) Color.White else Color.Black,
        title = { },
        navigationIcon = {
            IconButton(onClick = {
            }) {
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
        },
        actions = {

            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.text_size_minus),
                    contentDescription = "Play",
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .size(32.dp)
                        .clickable { onTextFontMinusClicked() },


                    )
            }

            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.text_size_plus),
                    contentDescription = "Play",
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .size(32.dp)
                        .clickable { onTextFontPlusClicked() }


                )
            }

            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.shareicon),
                    contentDescription = "Play",
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .size(26.dp)
                        .clickable { onShareButtonClicked() }


                )
            }

        },
    )
}
