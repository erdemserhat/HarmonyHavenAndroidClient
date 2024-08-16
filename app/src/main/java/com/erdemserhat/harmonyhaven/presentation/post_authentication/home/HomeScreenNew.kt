package com.erdemserhat.harmonyhaven.presentation.post_authentication.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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