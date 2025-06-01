package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.dto.responses.QuoteForOrderModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.quote_share.QuoteShareScreenParams
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card.VideoCard
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card.VolumeControlViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.animated_items.AnimatedLike
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.animated_items.AnimatedLikeBottomControlButton
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.static_card.QuoteCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A generic card component that displays quotes with interactive features.
 * Supports both image and video content, like animations, and various user interactions.
 *
 * @param quote The quote data to display
 * @param currentScreen Current screen index for video playback control
 * @param isVisible Whether the card is currently visible
 * @param isCurrentPage Whether this is the current page in the pager
 * @param modifier Modifier for customizing the layout
 * @param viewmodel ViewModel for managing quote data and operations
 * @param onCategoryClicked Callback for category button click
 * @param navController Navigation controller for handling navigation
 * @param onCommentClicked Callback for comment button click
 * @param volumeControllerViewModel Optional ViewModel for controlling audio volume
 */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun QuoteGenericCard(
    quote: Quote,
    currentScreen: Int,
    isVisible: Boolean,
    isCurrentPage: Boolean,
    modifier: Modifier,
    viewmodel: QuoteMainViewModel,
    onCategoryClicked: () -> Unit,
    navController: NavController? = null,
    onCommentClicked: () -> Unit,
    volumeControllerViewModel: VolumeControlViewModel? = null
) {
    // State management
    var isQuoteLiked by remember { mutableStateOf(quote.isLiked) }
    var isVisibleLikeAnimation by remember { mutableStateOf(false) }
    var shouldAnimateLikeButton by remember { mutableStateOf(false) }
    var shouldScreenBeClear by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Sync liked state with quote data
    LaunchedEffect(quote.isLiked) {
        isQuoteLiked = quote.isLiked
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        quote.isLiked = true
                        isQuoteLiked = true
                        isVisibleLikeAnimation = true
                        viewmodel.likeQuote(quote.id)
                        shouldAnimateLikeButton = true
                    },
                    onTap = {
                        volumeControllerViewModel?.toggleMute()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Like animation overlay
        if (isVisibleLikeAnimation) {
            AnimatedLike { isVisibleLikeAnimation = false }
        }

        // Bottom controls
        if (!shouldScreenBeClear) {
            BottomControls(
                modifier = Modifier
                    .padding(bottom = 50.dp, end = 7.dp)
                    .align(Alignment.BottomEnd),
                isQuoteLiked = isQuoteLiked,
                shouldAnimateLikeButton = shouldAnimateLikeButton,
                onLikeClicked = { isLiked ->
                    isQuoteLiked = isLiked
                    shouldAnimateLikeButton = true
                    if (isLiked) {
                        viewmodel.likeQuote(quote.id)
                        quote.isLiked = true
                    } else {
                        viewmodel.removeLikeQuote(quote.id)
                        quote.isLiked = false
                    }
                },
                onCategoryClicked = onCategoryClicked,
                onShareQuoteClicked = {
                    coroutineScope.launch {
                        handleQuoteShare(
                            quote = quote,
                            currentScreen = currentScreen,
                            viewmodel = viewmodel,
                            navController = navController
                        )
                    }
                },
                onCommentClicked = onCommentClicked
            )
        }

        // Content display
        if (!quote.imageUrl.endsWith(".mp4")) {
            QuoteCard(
                modifier = Modifier.align(Alignment.Center),
                quoteWriter = quote.writer,
                quoteSentence = quote.quote,
                quoteURL = quote.imageUrl
            )
        } else {
            VideoCard(
                videoUrl = quote.imageUrl,
                isPlaying = isCurrentPage,
                prepareOnly = isVisible,
                viewModel = volumeControllerViewModel
            )
        }
    }
}

/**
 * Displays the bottom control buttons for quote interactions.
 */
@Composable
private fun BottomControls(
    modifier: Modifier,
    isQuoteLiked: Boolean,
    shouldAnimateLikeButton: Boolean,
    onLikeClicked: (Boolean) -> Unit,
    onCategoryClicked: () -> Unit,
    onShareQuoteClicked: () -> Unit,
    onCommentClicked: () -> Unit
) {
    Column(
        modifier = modifier.zIndex(4f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedLikeBottomControlButton(
            isQuoteLiked = isQuoteLiked,
            shouldAnimate = shouldAnimateLikeButton,
            onLikeClicked = onLikeClicked,
            onAnimationEnd = { }
        )

        IconTextButton(
            iconRes = R.drawable.commenss_,
            label = "Yorum",
            onClick = onCommentClicked
        )

        IconTextButton(
            iconRes = R.drawable.share__3_,
            label = "Paylaş",
            onClick = onShareQuoteClicked
        )

        IconTextButton(
            iconRes = R.drawable.category,
            label = "Kategori",
            onClick = onCategoryClicked
        )
    }
}

/**
 * A button with an icon and text label.
 */
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun IconTextButton(
    iconRes: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(iconRes),
            contentDescription = label
        )
        Text(
            text = label,
            color = Color.White,
            fontSize = 10.sp
        )
    }
}

/**
 * Handles the quote sharing process.
 */
private suspend fun handleQuoteShare(
    quote: Quote,
    currentScreen: Int,
    viewmodel: QuoteMainViewModel,
    navController: NavController?
) {
    val bundle = Bundle().apply {
        putParcelable(
            "params",
            QuoteShareScreenParams(
                quoteUrl = quote.imageUrl,
                quote = quote.quote,
                author = quote.writer
            )
        )
    }
    viewmodel.updateSelectedQuote(
        QuoteForOrderModel(
            id = quote.id,
            quote = quote.quote,
            writer = quote.writer,
            imageUrl = quote.imageUrl,
            currentPage = currentScreen
        )
    )
    withContext(Dispatchers.Main) {
        navController?.navigate(route = Screen.QuoteShareScreen.route, args = bundle)
    }
}




