package com.erdemserhat.harmonyhaven.presentation.navigation

import LocalGifImage
import android.annotation.SuppressLint
import android.app.Activity
import android.media.Image
import android.os.Build
import android.util.Log
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.HomeScreenNew
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.NotificationScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.AlertDialogBase
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainScreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenIndicatorColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenSelectedNavigationBarItemColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun AppMainScreen(
    navController: NavController,
    params: MainScreenParams = MainScreenParams(),
    window: Window


) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

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

            it.statusBarColor = Color.Black.toArgb()
            it.navigationBarColor = Color.Black.toArgb()
            val insetsController = WindowCompat.getInsetsController(it, it.decorView)
            insetsController.isAppearanceLightStatusBars = false
            insetsController.isAppearanceLightNavigationBars = false


        }
    } else {
        window.let {
            it.statusBarColor = Color.Black.toArgb()
            it.navigationBarColor = Color.Black.toArgb()
            val insetsController = WindowCompat.getInsetsController(it, it.decorView)
            insetsController.isAppearanceLightStatusBars = false
            insetsController.isAppearanceLightNavigationBars = false


        }

    }


    Scaffold(
        bottomBar = {
            NavigationBar(

                containerColor = if (pagerState.currentPage == 0) Color.Black else Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)


            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(

                        selected = pagerState.currentPage == index,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = if (pagerState.currentPage == 0) Color.Black else Color.Black,
                            selectedTextColor = harmonyHavenSelectedNavigationBarItemColor,
                            indicatorColor = Color.Transparent,
                            unselectedIconColor = if (pagerState.currentPage == 0) Color.Black else Color.Black
                        ),
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                                params.screenNo = -1
                            }
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.badgeCount != null) {
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    } else if (item.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                if (pagerState.currentPage == 0) {
                                    Image(
                                        modifier = Modifier.size(23.dp),
                                        painter = painterResource(id = if (pagerState.currentPage == index) item.selectedIconDarkIcon else item.unSelectedIconDarkIcon),
                                        contentDescription = null
                                    )
                                } else {
                                    Image(
                                        modifier = Modifier.size(23.dp),
                                        painter = painterResource(id = if (pagerState.currentPage == index) item.selectedIconWhiteIcon else item.unSelectedIconWhiteIcon),
                                        contentDescription = null
                                    )


                                }
                            }

                        },
                        label = {
                            //  Text(
                            //    text = item.title,
                            //   fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal
                            // ) // Simgelerin altına yazıyı ekler
                        }
                    )
                }
            }

        },
        topBar = {
            if (pagerState.currentPage != 0) {
                MyAppBar(
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
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                count = items.size,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .onGloballyPositioned {
                        Log.d("debugDelayRender","ready")
                    }
            ) { page ->
                when (page) {
                    2 -> HomeScreenNew(navController = navController)
                    1 -> NotificationScreen(navController)
                    0 -> QuoteMainScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun AnimatedGif(
    gifId: Int,
    shouldMoveFromRightToLeft: Boolean = true,
    duration: Int = 10000,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    // Ekranın genişliğini temsil edecek bir Float değeri tanımlıyoruz
    val screenWidth = 1000f // Ekran genişliği, ekran genişliğinize göre ayarlayın

    // InfiniteTransition kullanarak animasyon oluşturuyoruz
    val infiniteTransition = rememberInfiniteTransition()

    // GIF'in sağdan sola hareketini sağlamak için offset animasyonu oluşturuyoruz
    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = screenWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    // Ekran boyutlarının alınabilmesi için bir Box kullanıyoruz
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // GIF'in hareket ettiği alanı tanımlıyoruz
        val offset = if (shouldMoveFromRightToLeft) screenWidth - offsetX else offsetX

        val imageModifier = Modifier
            .offset(x = (offset).dp) // Ekranın genişliği kadar hareket
            .size(50.dp) // GIF boyutunu belirliyoruz

        // GIF görüntüleyici
        LocalGifImage(
            gifId,
            modifier = imageModifier.size(10.dp)
        )
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
        selectedIconWhiteIcon =R.drawable.quoteblackfilled,
        unSelectedIconDarkIcon =R.drawable.quotewhiteunfilled,
        unSelectedIconWhiteIcon =R.drawable.quoteblackunfilled
    ),
    NavigationBarItem(
        title = "Bildirimler",
        hasNews = false,
        badgeCount = null,
        route = Screen.Notification.route,
        selectedIconDarkIcon = R.drawable.notificationwhitefilled,
        selectedIconWhiteIcon =R.drawable.notificationblackfilled,
        unSelectedIconDarkIcon = R.drawable.notificationwhiteunfilled,
        unSelectedIconWhiteIcon =R.drawable.notificationblackunfilled
    ),
    NavigationBarItem(
        title = "İçerikler",
        hasNews = false,
        badgeCount = null,
        route = Screen.Home.route,
        selectedIconDarkIcon =R.drawable.homewhitefilled,
        selectedIconWhiteIcon =R.drawable.homeblackfilled,
        unSelectedIconDarkIcon = R.drawable.homewhiteunfilled,
        unSelectedIconWhiteIcon =R.drawable.homeblackunfilled

    )

// NavigationBarItem(
//     "Profile", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle, false, null,
//       Screen.Settings.route
// )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    navController: NavController,
    title: String,
    topBarBackgroundColor: Color,
    isMainScreen: Boolean,
    modifier: Modifier = Modifier


) {

    // State to manage the visibility of the dropdown menu
    var expanded by remember { mutableStateOf(false) }
    var shouldShowLogoutAlertDialog by rememberSaveable {
        mutableStateOf(false)
    }

    if (shouldShowLogoutAlertDialog) {
        AlertDialogBase(
            alertTitle = "Çıkış Yap",
            alertBody = "Çıkış yapmak istediğine emin misin?",
            positiveButtonText = "Çıkış Yap",
            negativeButtonText = "Vazgeç",
            onPositiveButtonClicked = {
                navController.navigate(Screen.Login.route)
                shouldShowLogoutAlertDialog = false

            },
            onNegativeButtonClicked = {
                shouldShowLogoutAlertDialog = false
            }) {

        }
    }

    TopAppBar(
        modifier = modifier.drawBehind {
            drawLine(
                color = Color(0xFFE0E0E0), // Alt kenar rengini belirler
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 1.dp.toPx() // Sınır kalınlığını belirler
            )
        },
        navigationIcon = {

        },

        title = {


            


            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = when (isMainScreen) {
                    true -> FontWeight.Bold
                    false -> FontWeight.Normal
                },

                color = when (isMainScreen) {
                    true -> harmonyHavenSelectedNavigationBarItemColor
                    false -> Color.Black
                }// Beyazdan yeşile çalan renk (RGB: 191, 255, 191)
            )


        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topBarBackgroundColor
        ),
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More options"
                )
            }
            // Dropdown menu
            DropdownMenu(
                modifier = Modifier.background(harmonyHavenComponentWhite),

                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    navController.navigate(Screen.Profile.route)
                    expanded = false


                }) {
                    Text("Hesap Ayarları")
                }

                DropdownMenuItem(onClick = {
                    expanded = false
                    shouldShowLogoutAlertDialog = true


                }) {
                    Text("Çıkış Yap")

                }

                //DropdownMenuItem(onClick = { /* Handle option 2 click */ }) {
                //     Text("Option 2")
                // }
                // DropdownMenuItem(onClick = { /* Handle option 3 click */ }) {
                //    Text("Option 3")
                //  }
            }
        },

        )

}









