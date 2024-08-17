package com.erdemserhat.harmonyhaven.presentation.post_authentication.home

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.SetSystemBarsAppearance
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.domain.model.rest.toArticleResponseType
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen

@Composable
fun HomeScreenNew(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by homeViewModel.homeState.collectAsState()
    if (homeState.authStatus==2){
        navController.navigate(Screen.Login.route)
    }else{
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

            HomeScreenContentNew(
                categories = homeState.categories,
                navController = navController,
                articles =  homeState.categorizedArticles,
                onCategorySelected = {
                    homeViewModel.getArticlesByCategoryId(it.id)
                },
            )
       


    }


   // if(homeState.authStatus==2)
    //    navController.navigate(Screen.Login.route)

  //  homeViewModel.getArticlesByCategoryId(selectedCategory.id)



}