package com.erdemserhat.harmonyhaven.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.presentation.appcomponents.HarmonyHavenNavigationBar
import com.erdemserhat.harmonyhaven.presentation.home.HomeScreen
import com.erdemserhat.harmonyhaven.presentation.notification.NotificationScreen
import com.erdemserhat.harmonyhaven.presentation.profile.ProfileScreen

@Composable
fun DashboardScreen(navHostController: NavHostController) {
    var selectedItem = rememberSaveable {
        mutableIntStateOf(0)
    }
    Scaffold(
        bottomBar = { HarmonyHavenNavigationBar(navController = navHostController, selectedItem=selectedItem)}
    ) {paddingValues->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            if(selectedItem.value==0){
                HomeScreen(navController = navHostController)
            }else if(selectedItem.value==1){
                NotificationScreen(navController = navHostController)
            }else if (selectedItem.value==2){
                ProfileScreen(navController = navHostController)
            }
            
        }
        
    }
    
}

@Preview
@Composable
fun DashboardScreenPreview() {
    val navController = rememberNavController()
    DashboardScreen(navHostController = navController)
}