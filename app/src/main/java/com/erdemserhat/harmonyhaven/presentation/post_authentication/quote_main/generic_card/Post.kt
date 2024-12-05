package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.erdemserhat.harmonyhaven.presentation.navigation.QuoteShareScreenParams
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card.VideoCard
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.animated_items.AnimatedLike
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.animated_items.AnimatedLikeBottomControlButton
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.static_card.QuoteText
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Quote(
    quote: Quote,
    currentScreen: Int,
    isVisible: Boolean,
    isCurrentPage: Boolean,
    modifier: Modifier,
    viewmodel: QuoteMainViewModel,
    onCategoryClicked: () -> Unit,
    onShareQuoteClicked: () -> Unit,
    onReachedToLastPage: () -> Unit,
    navController: NavController? = null
) {
    var isQuoteLiked by remember { mutableStateOf(quote.isLiked) }
    var isVisibleLikeAnimation by remember { mutableStateOf(false) }
    var shouldAnimateLikeButton by remember { mutableStateOf(false) }
    var shouldScreenBeClear by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val capturableController = rememberCaptureController()

    // Triggers when the page is reached
    LaunchedEffect(quote) {
        coroutineScope.launch {
            delay(1500)
            onReachedToLastPage()
        }
    }

    // Syncs the liked state with the quote data
    LaunchedEffect(quote.isLiked) {
        isQuoteLiked = quote.isLiked
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        isQuoteLiked = true
                        isVisibleLikeAnimation = true
                        viewmodel.likeQuote(quote.id)
                        shouldAnimateLikeButton = true
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Like animation overlay
        if (isVisibleLikeAnimation) {
            AnimatedLike { isVisibleLikeAnimation = false }
        }

        // Bottom controls: Like, Category, Share
        if (!shouldScreenBeClear) {
            BottomControls(
                modifier = Modifier
                    .padding(bottom = 100.dp, end = 2.dp)
                    .align(Alignment.BottomEnd),
                isQuoteLiked = isQuoteLiked,
                shouldAnimateLikeButton = shouldAnimateLikeButton,
                onLikeClicked = {
                    isQuoteLiked = it
                    shouldAnimateLikeButton = true
                    if (it) {
                        viewmodel.likeQuote(quote.id)
                    } else {
                        viewmodel.removeLikeQuote(quote.id)
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
                }
            )
        }

        // Display quote as an image or video
        if (!quote.imageUrl.endsWith(".mp4")) {
            QuoteText(
                modifier = Modifier.align(Alignment.Center),
                quoteWriter = quote.writer,
                quoteSentence = quote.quote,
                quoteURL = quote.imageUrl
            )
        } else {
            VideoCard(
                videoUrl = quote.imageUrl,
                isPlaying = isCurrentPage,
                prepareOnly = isVisible
            )

        }
    }
}

@Composable
private fun BottomControls(
    modifier: Modifier,
    isQuoteLiked: Boolean,
    shouldAnimateLikeButton: Boolean,
    onLikeClicked: (Boolean) -> Unit,
    onCategoryClicked: () -> Unit,
    onShareQuoteClicked: () -> Unit
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
            iconRes = R.drawable.category,
            label = "Kategori",
            onClick = onCategoryClicked
        )

        IconTextButton(
            iconRes = R.drawable.share__3_,
            label = "Paylaş",
            onClick = onShareQuoteClicked
        )
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun IconTextButton(
    iconRes: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 25.dp)
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
            contentDescription = null
        )
        Text(label, color = Color.White, fontSize = 10.sp)
    }
}

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


