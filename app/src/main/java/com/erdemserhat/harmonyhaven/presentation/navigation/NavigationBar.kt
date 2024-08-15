package com.erdemserhat.harmonyhaven.presentation.navigation

import LocalGifImage
import android.annotation.SuppressLint
import android.os.Build
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.HomeScreenNew
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.NotificationScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.SettingsScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes.QuotesScreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import kotlinx.coroutines.GlobalScope

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun AppMainScreen(navController: NavController,params: MainScreenParams = MainScreenParams()) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val paramsData = params
    coroutineScope.launch {
        if(paramsData.screenNo!=-1)
            pagerState.scrollToPage(paramsData.screenNo)

    }
    Box{
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(width = 0.dp, height = 50.dp),
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
                                    paramsData.screenNo = -1
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
                                    if (pagerState.currentPage == index)
                                        Icon(
                                            imageVector = item.selectedIcon,
                                            contentDescription = item.title
                                        )
                                    else
                                        Icon(
                                            imageVector = item.unSelectedIcon,
                                            contentDescription = item.title
                                        )
                                }
                            }
                        )
                    }





                }
            },
            topBar = {
                MyAppBar(
                    navController, title =
                    when (pagerState.currentPage) {
                        0 -> "İçerikler"
                        1 -> "Bildirimler"
                        2 -> "Söz Akışı"
                        else -> "Default Title"  // Diğer durumlar için bir başlık eklemek isteyebilirsiniz

                    },
                    topBarBackgroundColor = when(pagerState.currentPage){
                        0-> Color.White
                        1->Color.White
                        2-> harmonyHavenComponentWhite
                        else->Color.White
                    }
                )


            }
        ) {


            HorizontalPager(
                state = pagerState,
                count = items.size,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) { page ->
                when (page) {
                    0 -> HomeScreenNew(navController = navController)
                    1 -> NotificationScreen(navController)
                    2 -> QuotesScreen()



                }

            }
        }

        //AnimatedGifExample(R.drawable.bird,true)


    }
}

@Composable
fun AnimatedGif(
    gifId:Int,
    shouldMoveFromRightToLeft:Boolean = true,
    duration:Int = 10000,
    @SuppressLint("ModifierParameter") modifier:Modifier = Modifier
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
        )
    )

    // Ekran boyutlarının alınabilmesi için bir Box kullanıyoruz
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // GIF'in hareket ettiği alanı tanımlıyoruz
        val offset =if(shouldMoveFromRightToLeft)  screenWidth - offsetX else offsetX

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
    val route: String
)

private val items = listOf(
    NavigationBarItem(
        "Home", Icons.Filled.Home, Icons.Outlined.Home, false, null,
        Screen.Home.route
    ),
    NavigationBarItem(
        "Notification", Icons.Filled.Notifications, Icons.Outlined.Notifications, false, null,
        Screen.Notification.route
    ),
    NavigationBarItem(
        "Quotes", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, false, null,
        Screen.Quotes.route
    ),
    // NavigationBarItem(
    //     "Profile", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle, false, null,
    //       Screen.Settings.route
    // )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(navController: NavController, title: String,topBarBackgroundColor:Color) {
    // State to manage the visibility of the dropdown menu
    var expanded by remember { mutableStateOf(false) }

        TopAppBar(
            title = { Text(text = title) },
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

                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(onClick = {
                        navController.navigate(Screen.Settings.route)
                        expanded = false


                    }) {
                        Text("Ayarlar")
                    }

                    //DropdownMenuItem(onClick = { /* Handle option 2 click */ }) {
                    //     Text("Option 2")
                    // }
                    // DropdownMenuItem(onClick = { /* Handle option 3 click */ }) {
                    //    Text("Option 3")
                    //  }
                }
            }
        )

    }





