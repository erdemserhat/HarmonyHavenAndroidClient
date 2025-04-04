package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.paging.LOG_TAG
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.SharedViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card.VolumeControlViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.CategoryPickerModalBottomSheet
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenProgressIndicator
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun PostFlow(
    modifier: Modifier,
    viewmodel: QuoteMainViewModel,
    navController: NavController? = null,
    sharedViewModel: SharedViewModel,
    onCommentsClicked: (postId: Int) -> Unit,
    onCategoryClicked: () -> Unit,
    volumeControllerViewModel:VolumeControlViewModel? = null
) {
    val quoteList1 = viewmodel.quotes.collectAsState()
    val shareQuoteSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )
    val pagerState = rememberPagerState { quoteList1.value.size }
    var isMuted  = volumeControllerViewModel?.isMuted?.collectAsState()

    DisposableEffect(Unit) {
        Log.d("MyScreen", "Composable is Mounted!")

        onDispose {
            Log.d("MyScreen", "Composable is Destroyed!")
        }
    }

    LaunchedEffect(viewmodel.shouldScrollToStart.value) {
        if (viewmodel.shouldScrollToStart.value){
            pagerState.scrollToPage(0)
            viewmodel.scrolledToStart()
        }
    }




    // State to track if the pull-to-refresh is in progress
    var isRefreshing  = viewmodel.isRefreshing.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var isVisible by rememberSaveable { mutableStateOf(false) }
    var isFirstInitialization by rememberSaveable {
        mutableStateOf(true)
    }

    Box(modifier = modifier) {
        // When isMuted changes, start a timer to hide the icon
        LaunchedEffect(isMuted?.value) {
            if(!isFirstInitialization){
                isVisible = true // Show the icon when the state changes
                delay(1000) // Wait for 2 seconds (adjust as needed)
                isVisible = false // Hide the icon

            }
            isFirstInitialization = false

        }



        if (isVisible) {
            Box(
                modifier = Modifier
                    .zIndex(10f)
                    .align(Alignment.Center)
                    .background(Color.Black.copy(alpha = 0.60f), shape = RoundedCornerShape(50.dp))
                    .size(65.dp)
            ) {
                Image(
                    painter = painterResource(if (isMuted?.value == true) R.drawable.volume_off_icon else R.drawable.volume_on_icon),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center).size(23.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }



        if (quoteList1.value.isNotEmpty()) {
            // PullToRefreshBox for pull-to-refresh functionality
            PullToRefreshBox(
                isRefreshing = isRefreshing.value,
                onRefresh = {
                    // Trigger refresh logic
                    coroutineScope.launch {
                        viewmodel.refreshList()

                    }
                },
                modifier = Modifier.fillMaxSize() // Ensure it takes up the entire screen
            ) {
                VerticalPager(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    state = pagerState
                ) { page ->
                    val isCurrentPageVisible = pagerState.currentPage == page
                    val isPreviousPageVisible = pagerState.currentPage == page + 1
                    val isNextPageVisible = pagerState.currentPage == page - 1
                    val isPageVisible = isCurrentPageVisible || isPreviousPageVisible || isNextPageVisible

                    LaunchedEffect(page) {
                        if (page == quoteList1.value.size - 5) {
                            viewmodel.loadMoreQuote()
                        }
                        if (page == pagerState.pageCount - 1) {
                            viewmodel.loadMoreQuote()
                        }
                    }

                    Crossfade(targetState = quoteList1.value.toList()[page], label = "") { quote ->
                        Quote(
                            volumeControllerViewModel = volumeControllerViewModel,
                            quote = quote,
                            currentScreen = pagerState.currentPage,
                            isVisible = isPageVisible,
                            isCurrentPage = isCurrentPageVisible,
                            modifier = Modifier.zIndex(2f),
                            viewmodel = viewmodel,
                            onShareQuoteClicked = {
                                coroutineScope.launch { shareQuoteSheetState.show() }
                            },
                            onCategoryClicked = {
                                onCategoryClicked()

                            },
                            navController = navController,
                            onCommentClicked = {
                                onCommentsClicked(quote.id)
                            }
                        )
                    }
                }
            }
        } else {
            // Loading Indicator when the quote list is empty
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                HarmonyHavenProgressIndicator()
            }
        }
    }
}