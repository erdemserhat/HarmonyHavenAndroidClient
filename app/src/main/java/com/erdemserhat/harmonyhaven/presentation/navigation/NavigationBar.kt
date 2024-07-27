package com.erdemserhat.harmonyhaven.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.HomeScreenNew
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.NotificationScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.SettingsScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes.QuotesScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun AppMainScreen(navController: NavController) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
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
                                    Icon(imageVector = item.selectedIcon, contentDescription = item.title)
                                else
                                    Icon(imageVector = item.unSelectedIcon, contentDescription = item.title)
                            }
                        }
                    )
                }
            }
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
                3 -> SettingsScreen(navController)
            }
        }
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
        "Home", Icons.Filled.Home, Icons.Outlined.Home, true, null,
        Screen.Home.route
    ),
    NavigationBarItem(
        "Notification", Icons.Filled.Notifications, Icons.Outlined.Notifications, false, 12,
        Screen.Notification.route
    ),
    NavigationBarItem(
        "Quotes", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, false, null,
        Screen.Quotes.route
    ),
    NavigationBarItem(
        "Profile", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle, false, null,
        Screen.Settings.route
    )
)

