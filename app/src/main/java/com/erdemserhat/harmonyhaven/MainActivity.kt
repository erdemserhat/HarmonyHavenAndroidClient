package com.erdemserhat.harmonyhaven

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.example.Engine
import com.erdemserhat.harmonyhaven.example.ExampleViewModel
import com.erdemserhat.harmonyhaven.navigation.SetupNavGraph
import com.erdemserhat.model.user.LoginModel
import com.erdemserhat.network.retrofit.UserApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalFoundationApi::class)

@AndroidEntryPoint
class MainActivity  : ComponentActivity() {
    private lateinit var navController: NavHostController
    val viewModel :ExampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //you have to use your dependency after the
        // super.onCreate() function
        super.onCreate(savedInstanceState)
        //use your dependencies here
        viewModel.A()
        viewModel.postUser()




        setContent {

                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }

}
