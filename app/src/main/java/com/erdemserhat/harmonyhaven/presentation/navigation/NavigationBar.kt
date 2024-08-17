package com.erdemserhat.harmonyhaven.presentation.navigation

import LocalGifImage
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountCircle
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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenIndicatorColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenSelectedNavigationBarItemColor
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import coil.compose.rememberAsyncImagePainter
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.SetSystemBarsAppearance
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.HomeScreenNew
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.NotificationScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.AlertDialogBase
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.SettingsScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes.QuotesScreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun AppMainScreen(navController: NavController, params: MainScreenParams = MainScreenParams()) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()


 //   val context = LocalContext.current
   // val activity = context as? Activity
  //  val window = activity?.window

   // window?.let {
    //    WindowCompat.setDecorFitsSystemWindows(it, true)
    //    it.setFlags(
     //       WindowManager.LayoutParams.FLAG_SECURE,
     //       WindowManager.LayoutParams.FLAG_SECURE
     ///   )
   // }

  //  SetSystemBarsAppearance(
    //    navigationBarColor = Color.White,
    //    statusBarColor = Color.White,
    //    navigationBarDarkIcons = true,
    //    statusBarDarkIcons = true
  //  )
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







    // Scroll to the initial page if provided
    LaunchedEffect(params.screenNo) {
        if (params.screenNo != -1) {
            pagerState.scrollToPage(params.screenNo)
        }
    }



    // Observe page changes and update status bar color accordingly
   // LaunchedEffect(pagerState.currentPage) {
      //  snapshotFlow { pagerState.currentPage }.collectLatest { page ->
         //   when (page) {
          //      0, 1 -> systemUiController.setSystemBarsColor(color = Color.White, darkIcons = true)
           //     2 -> systemUiController.setSystemBarsColor(color = Color.Black, darkIcons = false)
           // }
        //}
   /// }

    Scaffold(
        bottomBar = {
            // Conditionally show or hide the NavigationBar based on the current page
            // Hide NavigationBar on the third page
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)




            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = pagerState.currentPage == index,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = harmonyHavenSelectedNavigationBarItemColor,
                            selectedTextColor = harmonyHavenSelectedNavigationBarItemColor,
                            indicatorColor = harmonyHavenIndicatorColor,
                            unselectedIconColor = harmonyHavenSelectedNavigationBarItemColor
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
                                Icon(
                                    imageVector = if (pagerState.currentPage == index) item.selectedIcon else item.unSelectedIcon,
                                    contentDescription = item.title
                                )
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
            if (pagerState.currentPage != 2) {
                MyAppBar(
                    modifier = Modifier.zIndex(1f),
                    navController = navController,
                    title = when (pagerState.currentPage) {
                        0 -> "Harmony Haven"
                        1 -> "Bildirimler"
                        2 -> "Söz Akışı"
                        else -> "Default Title"
                    },
                    topBarBackgroundColor = when (pagerState.currentPage) {
                        0 -> Color.White
                        1 -> Color.White
                        2 -> Color.White
                        else -> Color.White
                    },
                    isMainScreen = pagerState.currentPage == 0
                )
            }
        }
    ) {padding->
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                count = items.size,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) { page ->
                when (page) {
                    0 -> HomeScreenNew(navController = navController)
                    1 -> NotificationScreen(navController)
                    2 -> QuotesScreen()
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
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route: String)

private val items = listOf(
    NavigationBarItem(
        "İçerikler", Icons.Filled.Home, Icons.Outlined.Home, false, null,
        Screen.Home.route
    ),
    NavigationBarItem(
        "Bildirimler", Icons.Filled.Notifications, Icons.Outlined.Notifications, false, null,
        Screen.Notification.route
    ),
    //NavigationBarItem(
    //    "Sözler", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, false, null,
     //   Screen.Quotes.route
  //  ),
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
            IconButton(onClick = {
                navController.navigate(Screen.QuoteMain.route){

                }


            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
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
            containerColor =topBarBackgroundColor
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









