package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreenNew(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by homeViewModel.homeState.collectAsState()


    HomeScreenContentNew(
        isArticlesReady = homeState.isArticleReady,
        isCategoryReady = homeState.isCategoryReady,
        categories = homeState.categories,
        navController = navController,
        articles = homeState.categorizedArticles,
        onCategorySelected = {
            homeViewModel.getArticlesByCategoryId(it.id)
        },
        allArticles = homeState.allArticles.map {
            it.toArticleResponseType(homeState.categories)
        },
        onRefreshed = {
            homeViewModel.refresh(it)
        }
    )


}