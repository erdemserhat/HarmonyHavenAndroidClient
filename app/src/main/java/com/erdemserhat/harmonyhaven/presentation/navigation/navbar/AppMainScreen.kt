package com.erdemserhat.harmonyhaven.presentation.navigation.navbar

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.presentation.navigation.MainScreenParams
import com.erdemserhat.harmonyhaven.presentation.navigation.navbar.navbar.BottomNavigationBar
import com.erdemserhat.harmonyhaven.presentation.navigation.navbar.navbar.NavigationBarItems
import com.erdemserhat.harmonyhaven.presentation.post_authentication.chat.ChatIntroScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil.EnneagramScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil.UserProfileScreenViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.HomeScreenNew
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.ProfileScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card.VolumeControlViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment.CommentViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun AppMainScreen(
    navController: NavController,
    params: MainScreenParams = MainScreenParams(),
    window: Window,
    quoteViewModel: QuoteMainViewModel = hiltViewModel(),
    userProfileSharedViewModel: UserProfileScreenViewModel


) {
    val pagerState = rememberPagerState(
        initialPage = 0

    )
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current


    var postID by rememberSaveable {
        mutableIntStateOf(3044)
    }

    var shouldShowExitDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val activity = context as? Activity // Context'i Activity'ye dönüştürüyoruz

    var shouldShowCommentBottomModal by remember {
        mutableStateOf(false)
    }
    val volumeControlViewModel = hiltViewModel<VolumeControlViewModel>()

    val commentSheetState = SheetState(skipPartiallyExpanded = true, density = Density(context))

    var shouldShowCategoryBottomModal by remember {
        mutableStateOf(false)
    }
    val categorySheetState = SheetState(skipPartiallyExpanded = false, density = Density(context))

    val commentViewModel: CommentViewModel = hiltViewModel()

    var lastClickTime by remember { mutableLongStateOf(0L) }
    val doubleClickThreshold = 300L

    LaunchedEffect(params.screenNo) {
        if (params.screenNo != -1) {
            pagerState.scrollToPage(params.screenNo)
        }

    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != 2) {
            volumeControlViewModel.saveLastCondition()
            volumeControlViewModel.mute()
        } else {
            volumeControlViewModel.setLastCondition()

        }
    }


    val screens: Map<Int, @Composable () -> Unit> = remember {
        mapOf(0 to { HomeScreenNew(navController) },
            1 to {
                QuoteMainScreen(
                    viewModel = quoteViewModel,
                    volumeControllerViewModel = volumeControlViewModel,
                    navController = navController,
                )
            },
            2 to { ChatIntroScreen(navController = navController) },
            3 to {
                EnneagramScreen(
                    navController,
                    profileScreenViewModel = userProfileSharedViewModel
                )
            },

            4 to {
                ProfileScreen(
                    navController
                )
            }

        )
    }

    //set status bar and system navbar color
    if (pagerState.currentPage == 1) {
        window.let {
            // content fill the system navbar- status bar
            //do not change this
            WindowCompat.setDecorFitsSystemWindows(it, false)
            val insetsController = WindowCompat.getInsetsController(it, it.decorView)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                it.statusBarColor = Color.Black.toArgb()
            }

            insetsController.isAppearanceLightStatusBars = false
            insetsController.isAppearanceLightNavigationBars = false


        }
    } else if (listOf(2, 3).contains(pagerState.currentPage)) {
        window.let {
            WindowCompat.setDecorFitsSystemWindows(
                it, false
            ) // content fill the system navbar- status bar
            val insetsController = WindowCompat.getInsetsController(it, it.decorView)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                it.statusBarColor = Color.Transparent.toArgb()
            }


            insetsController.isAppearanceLightStatusBars = true
            insetsController.isAppearanceLightNavigationBars = true


        }

    } else if (listOf(0, 4).contains(pagerState.currentPage)) {
        window.let {
            WindowCompat.setDecorFitsSystemWindows(
                it, false
            ) // content fill the system navbar- status bar
            val insetsController = WindowCompat.getInsetsController(it, it.decorView)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                it.statusBarColor = Color.Transparent.toArgb()
            }


            insetsController.isAppearanceLightStatusBars = false
            insetsController.isAppearanceLightNavigationBars = true


        }
        val systemUiController = rememberSystemUiController()

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )


    }


    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                pageIndex = pagerState.currentPage,
            ) { index ->
                coroutineScope.launch {
                    if (index == 1) {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastClickTime < doubleClickThreshold) {
                            // Double tap detected
                            coroutineScope.launch {
                                quoteViewModel.shouldScrollToStart()
                                quoteViewModel.refreshList()
                            }
                        }
                        lastClickTime = currentTime
                    }
                    keyboardController?.hide()
                    pagerState.scrollToPage(index)
                    params.screenNo = -1
                }

            }

        }
    ) { padding ->

        var lastBackPressTime by remember { mutableLongStateOf(0L) } // Son basma zamanını tutacak değişken
        BackHandler {

            val currentTime = System.currentTimeMillis() // Şu anki zaman
            if (currentTime - lastBackPressTime < 1000) { // Eğer 500 ms içinde iki kez tıklanmışsa
                shouldShowExitDialog = true
            } else {
                lastBackPressTime = currentTime
            }

        }


        if (shouldShowExitDialog) {
            ExitAlertDialog(
                onExit = {
                    activity?.finish()
                },
                onDismissRequest = {
                    shouldShowExitDialog = false
                }
            )


        }


        HorizontalPager(state = pagerState,
            userScrollEnabled = false,
            count = NavigationBarItems.items.size,
            modifier = Modifier
                .fillMaxSize()
                .background(if (pagerState.currentPage == 1) Color.Black else Color.White.copy(alpha = 0.95f))
                .padding(
                    if (pagerState.currentPage in listOf(0, 4)) PaddingValues(
                        top = 0.dp,
                        bottom = padding.calculateBottomPadding(),
                    )
                    else padding
                )
                .onGloballyPositioned {
                    Log.d("debugDelayRender", "ready")
                }) { page ->

            screens[page]?.let { it() }


        }


    }
}


















