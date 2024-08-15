package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.account_information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R

@Composable
fun AccountInformationTopBar(navController:NavController){
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color.White,
        contentColor = Color.Transparent,
        title = { Text(text = "Profil") },
        navigationIcon = {
            IconButton(onClick = { /* Geri gitme işlemi */ }) {
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