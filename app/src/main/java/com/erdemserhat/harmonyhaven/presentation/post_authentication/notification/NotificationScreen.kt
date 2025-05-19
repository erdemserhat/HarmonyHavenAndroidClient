package com.erdemserhat.harmonyhaven.presentation.post_authentication.notification

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import com.erdemserhat.harmonyhaven.presentation.navigation.MainScreenParams
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.scheduler_screen.NotificationSchedulerScreen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.scheduler_screen.NotificationSchedulerViewModel
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenButton
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenProgressIndicator
import com.erdemserhat.harmonyhaven.ui.theme.customFontInter
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

fun convertTimestampToTurkishDate(timestamp: Long): String {
    val date = java.util.Date(timestamp * 1000) // Timestamp'i milisaniyeye çeviriyoruz
    val sdf = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("tr", "TR"))
    return sdf.format(date)
}

@OptIn(ExperimentalPagerApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel(),
    schedulerViewModel: NotificationSchedulerViewModel = hiltViewModel()
) {

    val notifications by viewModel.notifications.collectAsState()
    var permissionGranted by remember { mutableStateOf(viewModel.isPermissionGranted()) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted = isGranted
            viewModel.updatePermissionStatus(isGranted)
        }
    )

    val isLoading = viewModel.isLoading.collectAsState()
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Pager setup for swipeable interface
    val pagerState = rememberPagerState(initialPage = 0)
    val tabTitles = listOf("Bildirimler", "Zamanlayıcı")

    LaunchedEffect(Unit) {
        viewModel.loadNotifications()
        notificationPermissionLauncher.launch(
            Manifest.permission.POST_NOTIFICATIONS
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.size(WindowInsets.systemBars.asPaddingValues().calculateTopPadding()))
        // Tab Row with Indicator
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Color.White,
            contentColor = harmonyHavenGreen,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    height = 3.dp,
                    color = harmonyHavenGreen
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = title,
                            color = if (pagerState.currentPage == index) Color.Black else Color.Black.copy(
                                alpha = 0.7f
                            ),
                            fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }

        // Horizontal Pager
        HorizontalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier.fillMaxSize().background(Color.White)
        ) { page ->
            when (page) {
                0 -> NotificationsContent(
                    navController = navController,
                    notifications = notifications,
                    isLoading = isLoading.value,
                    permissionGranted = permissionGranted,
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        isRefreshing = true
                        coroutineScope.launch {
                            viewModel.refreshNotification {
                                isRefreshing = false
                            }
                        }
                    },
                    onRequestPermission = {
                        notificationPermissionLauncher.launch(
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    },
                    loadMoreNotifications = {
                        if (!isLoading.value && viewModel.hasMoreData) {
                            viewModel.loadNotifications()
                        }
                    },
                    viewModel = viewModel
                )

                1 -> NotificationSchedulerScreen(schedulerViewModel)
            }
        }



    }

}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationsContent(
    navController: NavController,
    notifications: List<NotificationDto>,
    isLoading: Boolean,
    permissionGranted: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onRequestPermission: () -> Unit,
    loadMoreNotifications: () -> Unit,
    viewModel: NotificationViewModel
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (Build.VERSION.SDK_INT >= 31 && !permissionGranted) {
                //Request Permission
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.size(25.dp))
                    Image(
                        painter = painterResource(id = R.drawable.notication),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(
                        text = "Harmony Haven, size daha iyi bir deneyim sunmak için bildirimler gönderebilir. Bu bildirimler, ilgi alanlarınıza ve kullanım alışkanlıklarınıza göre özelleştirilmiştir.",
                        modifier = Modifier.widthIn(max = 400.dp),
                        softWrap = true
                    )

                    Spacer(modifier = Modifier.size(20.dp))
                    HarmonyHavenButton(
                        buttonText = "İzin Ver",
                        onClick = onRequestPermission,
                        isEnabled = true
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                }
            } else {
                if (notifications.isEmpty() && isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        HarmonyHavenProgressIndicator()
                    }
                } else if (notifications.isEmpty()) {
                    Spacer(modifier = Modifier.size(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.no_mail),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Henüz bir bildirim yok, en kısa sürede bildirim almaya başlayacaksınız.",
                        textAlign = TextAlign.Center
                    )
                } else {
                    val scrollState = rememberLazyListState()
                    LazyColumn(state = scrollState) {
                        items(notifications) { notification ->
                            NotificationCard(notification, navController)
                        }

                        // Loading indicator or more items
                        item {
                            if (isLoading) {
                                // Show loading indicator
                                Text(
                                    text = "...",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Detect when user scrolls to the end
                    LaunchedEffect(scrollState) {
                        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                            .collect { lastVisibleIndex ->
                                if (lastVisibleIndex != null) {
                                    if (lastVisibleIndex >= notifications.size - 1 && !isLoading && viewModel.hasMoreData) {
                                        loadMoreNotifications()
                                    }
                                }
                            }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun NotificationScreenPreview() {
    val navController = rememberNavController()
    NotificationScreen(navController = navController)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationCard(notification: NotificationDto, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .width(380.dp)
                .wrapContentHeight()
                .defaultMinSize(minHeight = 100.dp)
                .background(color = Color.Transparent)
                .clickable {
                    val shouldNavigateToPost = notification.screenCode.startsWith("-1")
                    if (shouldNavigateToPost) {
                        val postId = notification.screenCode.drop(2)
                        val bundleArticle = Bundle()
                        bundleArticle.putParcelable(
                            "article",
                            ArticlePresentableUIModel(
                                id = postId.toInt()
                            )
                        )

                        navController.navigate(
                            route = Screen.Article.route,
                            args = bundleArticle
                        )

                        // do nothing when screen code is 1 and 2
                    } else if (notification.screenCode.toInt() !in listOf(1,2)) {
                        val screenCode = notification.screenCode.toIntOrNull()
                        if (screenCode != null) {
                            val bundleMain = Bundle().apply {
                                putParcelable("params", MainScreenParams(screenNo = screenCode))
                            }
                            navController.navigate(
                                route = Screen.Main.route,
                                args = bundleMain
                            )
                        } else {
                            // Hatalı değer geldiğinde log basabilir veya varsayılan ekran gösterebilirsin
                            Log.e("Notification", "Geçersiz screenCode: ${notification.screenCode}")
                            // İsteğe bağlı: kullanıcıyı hata ekranına yönlendirme veya görmezden gelme
                        }
                    }
                },
        ) {
            Column {
                Text(
                    text = notification.title,
                    fontFamily = customFontInter,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 5.dp, bottom = 10.dp)
                )
                Text(
                    text = notification.content,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    textAlign = TextAlign.Justify,
                    fontFamily = customFontInter,
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )

                Text(
                    text = convertTimestampToTurkishDate(notification.timeStamp),
                    fontSize = 13.sp,
                    color = Color.Black.copy(alpha = 0.65f),
                    fontFamily = customFontInter,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(bottom = 5.dp, start = 10.dp)
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(harmonyHavenGreen.copy(alpha = 0.65f))
                    .size(8.dp)
                    .align(Alignment.TopEnd)
                    .padding(5.dp)
            )
        }
        Divider(color = Color.Black.copy(alpha = 0.07f))
    }
}
