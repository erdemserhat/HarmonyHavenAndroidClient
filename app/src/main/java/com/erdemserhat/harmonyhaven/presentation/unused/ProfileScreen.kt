package com.erdemserhat.harmonyhaven.presentation.unused

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientWhite

@Composable
fun ProfileScreen(navController: NavController) {
    ProfileScreenContent(navController)
}

@Preview(
    showBackground = true
)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreenContent(navController)
}

@Composable
fun ProfileScreenContent(navController: NavController) {

    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        harmonyHavenGradientGreen,
                        harmonyHavenGradientWhite
                    )
                )
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.size(25.dp))
        RoundedShapeWithImage(R.drawable.pp_mock)

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Option(
                optionName = "Accounts Information",
                Icons.Outlined.AccountCircle
            )
            Option(optionName = "Liked Quotes",
                Icons.Outlined.ThumbUp)
            Option(optionName = "Take a Personality Test",
                Icons.Outlined.Check)
            Option(optionName = "About us",
                Icons.Outlined.Info)
            Option(optionName = "Report Problem",
                Icons.AutoMirrored.Outlined.Send
            )
            Option(
                optionName = "Sign Out",
                imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                onOptionClicked = {navController.navigate(Screen.Login.route)}

            )

        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.harmony_haven_icon),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Text(text = "Harmony Haven Version 1.0.0", fontSize = 18.sp)

            }


        }


    }


}


@Composable
fun RoundedShapeWithImage(imageResId: Int) {
    Image(
        modifier = Modifier.size(100.dp),
        painter = painterResource(id = imageResId),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Composable
fun Option(
    optionName: String,
    imageVector: ImageVector,
    onOptionClicked: () -> Unit = {}

) {
    Box {
        Column {
            Row(
                modifier = Modifier
                    .clickable {
                        onOptionClicked()
                    }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = imageVector,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = optionName,
                    modifier = Modifier,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic
                )
            }

            Image(painter = painterResource(id = R.drawable.basicline), contentDescription = null)

        }


    }

}