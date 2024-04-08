package com.erdemserhat.harmonyhaven

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.Manifest

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.example.ExampleViewModel
import com.erdemserhat.harmonyhaven.presentation.home.HomeViewModel
import com.erdemserhat.harmonyhaven.presentation.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity  : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel: ExampleViewModel by viewModels()
    //private val homeViewModel:HomeViewModel by viewModels()
    //val loginViewModel:LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        //you have to use your dependency after the
        // super.onCreate() function
        super.onCreate(savedInstanceState)

        //use your dependencies here
        //viewModel.getArticlesByCategory(1)
        requestNotificationPermission()
        viewModel.getToken()


        setContent {

            navController = rememberNavController()
            SetupNavGraph(navController = navController)
        }
    }


    private fun requestNotificationPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if(!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }

}


