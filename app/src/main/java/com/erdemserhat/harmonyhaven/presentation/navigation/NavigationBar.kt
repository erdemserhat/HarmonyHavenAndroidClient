package com.erdemserhat.harmonyhaven.presentation.navigation

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.feature.google_auth.TestScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.chat.ChatIntroScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.chat.ChatScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.EnneagramTestScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.HomeScreenNew
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.NotificationScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card.VolumeControlViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.CategoryPickerModalBottomSheet
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment.CommentModalBottomSheet
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment.CommentViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
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
    val pagerState = rememberPagerState(
        initialPage = 1

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

    var shouldShowCommentBottomModal by remember{
        mutableStateOf(false)
    }
    val volumeControlViewModel = hiltViewModel<VolumeControlViewModel>()

    val commentSheetState = SheetState(skipPartiallyExpanded = true, density = Density(context))

    var shouldShowCategoryBottomModal by remember{
        mutableStateOf(false)
    }
    val categorySheetState = SheetState(skipPartiallyExpanded = false, density = Density(context))

    val  commentViewModel :CommentViewModel = hiltViewModel()

    var lastClickTime by remember { mutableStateOf(0L) }
    val doubleClickThreshold = 300L // Çift tıklama için eşik süre (ms)
    // Scroll to the initial page if provided
    LaunchedEffect(params.screenNo) {
        if (params.screenNo != -1) {
            pagerState.scrollToPage(params.screenNo)
        }

    }

    LaunchedEffect(pagerState.currentPage) {
        Log.d("dsdfsdfsdf",pagerState.currentPage.toString())
        if(pagerState.currentPage!=1){
            volumeControlViewModel.saveLastCondition()
            volumeControlViewModel.mute()
        }else{
            volumeControlViewModel.setLastCondition()


        }
    }




    val screens: Map<Int, @Composable () -> Unit> = remember {
        mapOf(
            0 to { HomeScreenNew(navController) },
            1 to { QuoteMainScreen(
                volumeControllerViewModel = volumeControlViewModel,
                navController = navController,
                sharedViewModel = viewModel,
                onCommentsClicked = { postId ->
                    postID = postId
                    shouldShowCommentBottomModal = true
                },
                onCategoryClicked = {
                    coroutineScope.launch {
                        shouldShowCategoryBottomModal = true
                    }
                },
                viewmodel = quoteViewModel
            ) },
            2 to { NotificationScreen(navController) },
            3 to { ChatIntroScreen(navController = navController) },
            4 to { EnneagramTestScreen(navController) },

            )
    }




    //set status bar and system navbar color
    if (pagerState.currentPage == 1) {
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
            .background(if (pagerState.currentPage == 1) Color.Black else Color.White.copy(alpha = 0.95f))
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        bottomBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (pagerState.currentPage == 1) Color.Black else Color.White.copy(alpha = 0.95f))
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
                                    if(index==1){
                                        val currentTime = System.currentTimeMillis()
                                        if (currentTime - lastClickTime < doubleClickThreshold) {
                                            // Çift tıklama algılandı
                                            coroutineScope.launch {
                                                // Çift tıklama işlemi
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

                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        val iconResource = if (pagerState.currentPage == 1) {
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

            if (pagerState.currentPage != 1) {
                MyAppBar(
                    onExitClicked = {
                        viewModel.logout()
                    },
                    modifier = Modifier.zIndex(1f),
                    navController = navController,
                    title = when (pagerState.currentPage) {
                        0 -> "Harmony Haven"
                        2 -> "Bildirimler"
                        1 -> "Söz Akışı"
                        3 -> "Harmonia"
                        4-> "Enneagram"
                        else -> ""
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


        LaunchedEffect(commentSheetState.isVisible) {
            if (commentSheetState.isVisible) {
                if ( commentViewModel.lastPostId.value != postID) {
                    commentViewModel.loadComments(postID)
                } else {
                    commentViewModel.loadFromCache()
                }
            } else {
                keyboardController?.hide()
                commentViewModel.setLastPostId(postID)
                commentViewModel.resetList()
                commentViewModel.commitApiCallsWithoutDelay()
            }
        }


        if (shouldShowExitDialog) {
            Box(modifier = Modifier.fillMaxSize()){
                AlertDialog(
                    modifier = Modifier.align(Alignment.Center),
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


        }



//////////////////////////////////////////////////////

//////////////////////////////////////////////////
// //
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            count = items.size,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(padding)
                .onGloballyPositioned {
                    Log.d("debugDelayRender", "ready")
                }
        ) { page ->

            Box{

                screens[page]?.let { it() }



                AnimatedVisibility(
                    visible = shouldShowCommentBottomModal,
                    enter = slideInVertically(
                        // Slide in from the bottom (start position is the height of the composable)
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = slideOutVertically(
                        // Slide out to the bottom (end position is the height of the composable)
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {


                CommentModalBottomSheet(
                        onDismissRequest = {shouldShowCommentBottomModal = false},
                        sheetState = commentSheetState ,
                        modifier = Modifier.align(Alignment.BottomCenter),
                        postId = postID,
                        viewModel = commentViewModel
                    )

                }



                if(shouldShowCategoryBottomModal){

                    CategoryPickerModalBottomSheet(
                        onDismissRequest = {
                            shouldShowCategoryBottomModal = false

                        },
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
                }


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
        title = "İçerikler",
        hasNews = false,
        badgeCount = null,
        route = Screen.Home.route,
        selectedIconDarkIcon = R.drawable.homewhitefilled,
        selectedIconWhiteIcon = R.drawable.homeblackfilled,
        unSelectedIconDarkIcon = R.drawable.homewhiteunfilled,
        unSelectedIconWhiteIcon = R.drawable.homeblackunfilled

    ),
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
        title = "Harmonia",
        hasNews = false,
        badgeCount = null,
        route = Screen.Notification.route,
        selectedIconDarkIcon = R.drawable.message_f_white,
        selectedIconWhiteIcon = R.drawable.message_f_black,
        unSelectedIconDarkIcon = R.drawable.message_uf_white,
        unSelectedIconWhiteIcon = R.drawable.message_uf_black
    ),



      NavigationBarItem(
          title = "Profile",
         hasNews = false,
          badgeCount = null,
          route = Screen.Home.route,
          selectedIconDarkIcon =R.drawable.house,
          selectedIconWhiteIcon =R.drawable.house,
          unSelectedIconDarkIcon = R.drawable.house,
          unSelectedIconWhiteIcon =R.drawable.house

      )
)














