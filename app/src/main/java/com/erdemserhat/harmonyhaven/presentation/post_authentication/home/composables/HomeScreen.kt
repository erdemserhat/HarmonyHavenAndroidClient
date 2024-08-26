package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.domain.model.rest.toArticleResponseType
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.HomeViewModel

@Composable
fun HomeScreenNew(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by homeViewModel.homeState.collectAsState()

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
                allArticles = homeState.allArticles.map {
                    it.toArticleResponseType(homeState.categories)
                }
            )



}