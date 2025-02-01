package com.erdemserhat.harmonyhaven.presentation.navigation

import LocalGifImage
import android.annotation.SuppressLint
import android.app.Activity
import android.media.Image
import android.os.Build
import android.util.Log
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.collection.mutableIntSetOf
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.AlertDialog
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.HomeScreenNew
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.NotificationScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.AlertDialogBase
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.CategoryPickerModalBottomSheet
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment.CommentModalBottomSheet
import com.erdemserhat.harmonyhaven.presentation.post_authentication.user.ProfileScreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenIndicatorColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenSelectedNavigationBarItemColor
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
    viewModel: SharedViewModel = hiltViewModel(),
    quoteViewModel: QuoteMainViewModel = hiltViewModel()


) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val commentSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true

    )
    val categorySheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    var postID by rememberSaveable {
        mutableIntStateOf(3044)
    }

    var shouldShowExitDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val activity = context as? Activity // Context'i Activity'ye dönüştürüyoruz


    // Scroll to the initial page if provided
    LaunchedEffect(params.screenNo) {
        if (params.screenNo != -1) {
            pagerState.scrollToPage(params.screenNo)
        }

    }

    window.let {

        WindowCompat.setDecorFitsSystemWindows(it, true)


    }


    //set status bar and system navbar color
    if (pagerState.currentPage == 0) {
        window.let {
            // content fill the system navbar- status bar
            //do not change this
            WindowCompat.setDecorFitsSystemWindows(it, false)
            val insetsController = WindowCompat.getInsetsController(it, it.decorView)
            if (Build.VERSION.SDK_INT <Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                it.statusBarColor = Color.Black.toArgb()
                it.navigationBarColor = Color.Black.toArgb()
            }

            insetsController.isAppearanceLightStatusBars = false
            insetsController.isAppearanceLightNavigationBars = false


        }
    } else {
        window.let {
            WindowCompat.setDecorFitsSystemWindows(it, false) // content fill the system navbar- status bar
            val insetsController = WindowCompat.getInsetsController(it, it.decorView)

            if (Build.VERSION.SDK_INT <Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                it.statusBarColor = Color.Transparent.toArgb()
                it.navigationBarColor = Color.Transparent.toArgb()
            }


            insetsController.isAppearanceLightStatusBars = true
            insetsController.isAppearanceLightNavigationBars = true


        }

    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(if (pagerState.currentPage == 0) Color.Black else Color.White)
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (pagerState.currentPage == 0) Color.Black else Color.White)
                    //for android 15 (api level 35)
                    .padding()
                    .height(45.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items.forEachIndexed { index, item ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }

                            ) {
                                coroutineScope.launch {
                                    keyboardController?.hide()
                                    pagerState.scrollToPage(index)
                                    params.screenNo = -1
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        val iconResource = if (pagerState.currentPage == 0) {
                            if (pagerState.currentPage == index) item.selectedIconDarkIcon else item.unSelectedIconDarkIcon
                        } else {
                            if (pagerState.currentPage == index) item.selectedIconWhiteIcon else item.unSelectedIconWhiteIcon
                        }
                        Image(
                            modifier = Modifier.size(23.dp),
                            painter = painterResource(id = iconResource),
                            contentDescription = null
                        )
                    }
                }
            }

        },
        topBar = {
            if (pagerState.currentPage != 0) {
                MyAppBar(
                    onExitClicked = {
                        viewModel.logout()
                    },
                    modifier = Modifier.zIndex(1f),
                    navController = navController,
                    title = when (pagerState.currentPage) {
                        2 -> "Harmony Haven"
                        1 -> "Bildirimler"
                        0 -> "Söz Akışı"
                        else -> "Default Title"
                    },
                    topBarBackgroundColor = when (pagerState.currentPage) {
                        2 -> Color.White
                        1 -> Color.White
                        0 -> Color.White
                        else -> Color.White // do not laugh it will be a future :)
                    },
                    isMainScreen = pagerState.currentPage == 0
                )
            }
        }
    ) { padding ->
        var lastBackPressTime by remember { mutableLongStateOf(0L) } // Son basma zamanını tutacak değişken
        BackHandler {
            if (commentSheetState.isVisible) {
                coroutineScope.launch { commentSheetState.hide() }

            } else if (categorySheetState.isVisible) {
                coroutineScope.launch { categorySheetState.hide() }

            } else {
                val currentTime = System.currentTimeMillis() // Şu anki zaman
                if (currentTime - lastBackPressTime < 1000) { // Eğer 500 ms içinde iki kez tıklanmışsa
                    shouldShowExitDialog = true
                } else {
                    lastBackPressTime = currentTime
                }
            }
        }





        if (shouldShowExitDialog) {
            AlertDialog(
                onDismissRequest = { shouldShowExitDialog = false },
                title = {
                    androidx.compose.material.Text(text = "Çıkmak Üzeresiniz")
                },
                text = {
                    androidx.compose.material.Text(text = "Uygulamadan çıkmak istediğinizden emin misiniz?")
                },
                confirmButton = {
                    TextButton(onClick = {
                        activity?.finish() // Uygulamayı sonlandır
                    }) {
                        androidx.compose.material.Text(text = "Evet", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { shouldShowExitDialog = false }) {
                        androidx.compose.material.Text(text = "Vazgeç", color = Color.White)
                    }
                }
            )

        }



//////////////////////////////////////////////////////
        CommentModalBottomSheet(
            sheetState = commentSheetState,
            modifier = Modifier.zIndex(2f),
            postId = postID
        )

        CategoryPickerModalBottomSheet(
            sheetState = categorySheetState,
            actions = CommentBottomModalSheetActions(
                onShouldFilterQuotes = { selectedCategories ->
                    quoteViewModel.loadCategorizedQuotes(selectedCategories)

                },
                onSaveCategorySelection = { model ->
                    quoteViewModel.saveCategorySelection(model)
                },
                onGetCategorySelectionModel = quoteViewModel.getCategorySelection()

            )
        )
//////////////////////////////////////////////////////
        HorizontalPager(
            state = pagerState,
            count = items.size,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(padding)
                .onGloballyPositioned {
                    Log.d("debugDelayRender", "ready")
                }
        ) { page ->
            when (page) {
                3-> ProfileScreen()
                2 -> HomeScreenNew(navController = navController)
                1 -> NotificationScreen(navController)
                0 -> QuoteMainScreen(
                    navController = navController,
                    sharedViewModel = viewModel,
                    onCommentsClicked = {postId->
                        postID = postId
                        coroutineScope.launch {
                            commentSheetState.show()
                        }

                    },
                    onCategoryClicked = {
                        coroutineScope.launch {
                            categorySheetState.show()
                        }
                    },
                    viewmodel = quoteViewModel


                )
            }


        }
    }
}

private data class NavigationBarItem(
    val title: String,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route: String,
    val selectedIconDarkIcon: Int,
    val selectedIconWhiteIcon: Int,
    val unSelectedIconDarkIcon: Int,
    val unSelectedIconWhiteIcon: Int

)

private val items = listOf(
    NavigationBarItem(
        title = "Sözler",
        hasNews = false,
        badgeCount = null,
        Screen.Quotes.route,
        selectedIconDarkIcon = R.drawable.quotewhitefilled,
        selectedIconWhiteIcon = R.drawable.quoteblackfilled,
        unSelectedIconDarkIcon = R.drawable.quotewhiteunfilled,
        unSelectedIconWhiteIcon = R.drawable.quoteblackunfilled
    ),
    NavigationBarItem(
        title = "Bildirimler",
        hasNews = false,
        badgeCount = null,
        route = Screen.Notification.route,
        selectedIconDarkIcon = R.drawable.notificationwhitefilled,
        selectedIconWhiteIcon = R.drawable.notificationblackfilled,
        unSelectedIconDarkIcon = R.drawable.notificationwhiteunfilled,
        unSelectedIconWhiteIcon = R.drawable.notificationblackunfilled
    ),
    NavigationBarItem(
        title = "İçerikler",
        hasNews = false,
        badgeCount = null,
        route = Screen.Home.route,
        selectedIconDarkIcon = R.drawable.homewhitefilled,
        selectedIconWhiteIcon = R.drawable.homeblackfilled,
        unSelectedIconDarkIcon = R.drawable.homewhiteunfilled,
        unSelectedIconWhiteIcon = R.drawable.homeblackunfilled

    ),
    //  NavigationBarItem(
    //      title = "Home",
    //      hasNews = false,
    //      badgeCount = null,
    //      route = Screen.Home.route,
    //      selectedIconDarkIcon =R.drawable.house,
    //      selectedIconWhiteIcon =R.drawable.house,
    //      unSelectedIconDarkIcon = R.drawable.house,
    //      unSelectedIconWhiteIcon =R.drawable.house
//
    //  )
)










