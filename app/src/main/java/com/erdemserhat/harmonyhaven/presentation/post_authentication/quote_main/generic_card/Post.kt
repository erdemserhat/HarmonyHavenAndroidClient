package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
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
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card.VolumeControlViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.animated_items.AnimatedLike
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.animated_items.AnimatedLikeBottomControlButton
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.static_card.QuoteCard
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
    navController: NavController? = null,
    onCommentClicked: () -> Unit,
    volumeControllerViewModel:VolumeControlViewModel? = null
) {
    var isQuoteLiked by remember { mutableStateOf(quote.isLiked) }
    var isVisibleLikeAnimation by remember { mutableStateOf(false) }
    var shouldAnimateLikeButton by remember { mutableStateOf(false) }
    var shouldScreenBeClear by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val capturableController = rememberCaptureController()
    val context = LocalContext.current


    // Syncs the liked state with the quote data
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
                        Log.d("dasdasdas","dasdas")
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

        // Bottom controls: Like, Category, Share
        if (!shouldScreenBeClear) {
            BottomControls(
                modifier = Modifier
                    .padding(bottom = 50.dp, end = 7.dp)
                    .align(Alignment.BottomEnd),
                isQuoteLiked = isQuoteLiked,
                shouldAnimateLikeButton = shouldAnimateLikeButton,
                onLikeClicked = {
                    isQuoteLiked = it
                    shouldAnimateLikeButton = true
                    if (it) {
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

        // Display quote as an image or video
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

@Composable
private fun BottomControls(
    modifier: Modifier,
    isQuoteLiked: Boolean,
    shouldAnimateLikeButton: Boolean,
    onLikeClicked: (Boolean) -> Unit,
    onCategoryClicked: () -> Unit,
    onShareQuoteClicked: () -> Unit,
    onCommentClicked:()->Unit
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



fun toggleAppSound(context: Context) {
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val isMuted = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0

    if (isMuted) {
        // Ses Aç
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
            0
        )
    } else {
        // Ses Kapat
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
    }
}


