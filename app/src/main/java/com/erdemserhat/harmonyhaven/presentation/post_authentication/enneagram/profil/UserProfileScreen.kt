package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen

@Composable
fun UserProfileScreen(navController: NavController,profileScreenViewModel: UserProfileScreenViewModel = hiltViewModel()) {
    val profileScreenState by profileScreenViewModel.state

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        if(profileScreenState.isLoading){
            Text("Yükleniyor....")
        }

        if(profileScreenState.result?.isTestTakenBefore == true){
            Text(profileScreenState.result!!.detailedResult.toString())

        }







        Button(onClick = {
            navController.navigate(Screen.EnneagramTestScreen.route)

        }) {
            Text("Testi Al.")
        }



    }

}