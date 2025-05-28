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
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card.VolumeControlViewModel
import com.erdemserhat.harmonyhaven.presentation.prev_authentication.register.components.HarmonyHavenProgressIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A vertical scrolling feed of quotes with pull-to-refresh functionality.
 * Handles quote display, volume control, and user interactions.
 *
 * @param modifier Modifier for customizing the layout
 * @param viewmodel ViewModel for managing quote data and operations
 * @param navController Navigation controller for handling navigation
 * @param onCommentsClicked Callback for when comments are clicked
 * @param onCategoryClicked Callback for when category is clicked
 * @param volumeControllerViewModel Optional ViewModel for controlling audio volume
 */
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
    onCommentsClicked: (postId: Int) -> Unit,
    onCategoryClicked: () -> Unit,
    volumeControllerViewModel: VolumeControlViewModel? = null
) {
    // State management
    val quoteList = viewmodel.quotes.collectAsState()
    val isRefreshing = viewmodel.isRefreshing.collectAsState()
    val isMuted = volumeControllerViewModel?.isMuted?.collectAsState()
    
    // UI state
    var isVolumeIconVisible by rememberSaveable { mutableStateOf(false) }
    var isFirstInitialization by rememberSaveable { mutableStateOf(true) }
    
    // Controllers
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState { quoteList.value.size }
    val shareQuoteSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    // Handle scroll to start
    LaunchedEffect(viewmodel.shouldScrollToStart.value) {
        if (viewmodel.shouldScrollToStart.value) {
            pagerState.scrollToPage(0)
            viewmodel.scrolledToStart()
        }
    }

    // Handle volume icon visibility
    LaunchedEffect(isMuted?.value) {
        if (!isFirstInitialization) {
            isVolumeIconVisible = true
            delay(1000)
            isVolumeIconVisible = false
        }
        isFirstInitialization = false
    }

    // Main content
    Box(modifier = modifier) {
        // Volume control icon
        if (isVolumeIconVisible) {
            VolumeControlIcon(isMuted = isMuted?.value ?: false, modifier = Modifier.align(Alignment.Center))
        }

        // Quote feed
        if (quoteList.value.isNotEmpty()) {
            QuoteFeed(
                quoteList = quoteList.value.toList(),
                pagerState = pagerState,
                isRefreshing = isRefreshing.value,
                onRefresh = { coroutineScope.launch { viewmodel.refreshList() } },
                onLoadMore = { viewmodel.loadMoreQuote() },
                onCategoryClick = onCategoryClicked,
                onCommentClick = onCommentsClicked,
                volumeControllerViewModel = volumeControllerViewModel,
                viewmodel = viewmodel,
                navController = navController
            )
        } else {
            LoadingIndicator()
        }
    }
}

/**
 * Displays the volume control icon with animation.
 */
@Composable
private fun VolumeControlIcon(modifier: Modifier = Modifier, isMuted: Boolean) {
    Box(
        modifier = modifier
            .zIndex(10f)
            .background(Color.Black.copy(alpha = 0.60f), shape = RoundedCornerShape(50.dp))
            .size(65.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                if (isMuted) R.drawable.volume_off_icon 
                else R.drawable.volume_on_icon
            ),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(23.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

/**
 * Displays the main quote feed with pull-to-refresh functionality.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuoteFeed(
    quoteList: List<Quote>,
    pagerState: androidx.compose.foundation.pager.PagerState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    onCategoryClick: () -> Unit,
    onCommentClick: (Int) -> Unit,
    volumeControllerViewModel: VolumeControlViewModel?,
    viewmodel: QuoteMainViewModel,
    navController: NavController?
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize()
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

            // Load more quotes when reaching near the end
            LaunchedEffect(page) {
                if (page == quoteList.size - 5 || page == pagerState.pageCount - 1) {
                    onLoadMore()
                }
            }

            Crossfade(targetState = quoteList[page], label = "") { quote ->
                Quote(
                    volumeControllerViewModel = volumeControllerViewModel,
                    quote = quote,
                    currentScreen = pagerState.currentPage,
                    isVisible = isPageVisible,
                    isCurrentPage = isCurrentPageVisible,
                    modifier = Modifier.zIndex(2f),
                    viewmodel = viewmodel,
                    onCategoryClicked = onCategoryClick,
                    navController = navController,
                    onCommentClicked = { onCommentClick(quote.id) }
                )
            }
        }
    }
}

/**
 * Displays a loading indicator when the quote list is empty.
 */
@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HarmonyHavenProgressIndicator()
    }
}