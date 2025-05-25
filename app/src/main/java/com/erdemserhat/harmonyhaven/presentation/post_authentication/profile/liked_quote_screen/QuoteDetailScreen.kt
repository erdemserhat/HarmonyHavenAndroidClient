package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile.liked_quote_screen

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment.CommentModalBottomSheet
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment.CommentViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.static_card.QuoteCard
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteDetailScreen(
    quote: Quote,
    navController: NavController,
    viewModel: QuoteMainViewModel = hiltViewModel(),
    volumeControlViewModel: VolumeControlViewModel = hiltViewModel(),
    commentViewModel: CommentViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    var shouldShowCommentBottomModal by remember { mutableStateOf(false) }
    val commentSheetState = SheetState(skipPartiallyExpanded = true, density = Density(context))

    val coroutineScope = rememberCoroutineScope()

    SideEffect {
        systemUiController.setStatusBarColor(
            darkIcons = false,
            color = Color.Transparent
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    keyboardController?.show()




    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        containerColor = Color.Black
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            QuoteContent(
                quote = quote,
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                navController = navController,
                volumeControlViewModel = volumeControlViewModel,
                onCommentClicked = {
                    shouldShowCommentBottomModal = true
                    commentViewModel.loadComments(quote.id)
                }
            )

            // Back button positioned at top-left
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .statusBarsPadding()
                    .padding(16.dp)
                    .zIndex(10f)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Geri",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Comment Modal Bottom Sheet
            if (shouldShowCommentBottomModal) {

                CommentModalBottomSheet(
                    modifier = Modifier.align(Alignment.BottomCenter),

                    onDismissRequest = {
                        shouldShowCommentBottomModal = false 
                    },
                    sheetState = commentSheetState,
                    postId = quote.id,
                    viewModel = commentViewModel
                )
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun QuoteContent(
    quote: Quote,
    modifier: Modifier,
    viewModel: QuoteMainViewModel,
    navController: NavController,
    volumeControlViewModel: VolumeControlViewModel,
    onCommentClicked: () -> Unit
) {
    var isQuoteLiked by remember { mutableStateOf(quote.isLiked) }
    var isVisibleLikeAnimation by remember { mutableStateOf(false) }
    var shouldAnimateLikeButton by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Syncs the liked state with the quote data
    LaunchedEffect(quote.isLiked) {
        isQuoteLiked = quote.isLiked
    }

    Box(
        modifier = modifier
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        quote.isLiked = true
                        isQuoteLiked = true
                        isVisibleLikeAnimation = true
                        viewModel.likeQuote(quote.id)
                        shouldAnimateLikeButton = true
                    },
                    onTap = {
                        volumeControlViewModel.toggleMute()
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
        BottomControls(
            modifier = Modifier
                .padding(bottom = 125.dp, end = 10.dp)
                .align(Alignment.BottomEnd),
            isQuoteLiked = isQuoteLiked,
            shouldAnimateLikeButton = shouldAnimateLikeButton,
            onLikeClicked = {
                isQuoteLiked = it
                shouldAnimateLikeButton = true
                if (it) {
                    viewModel.likeQuote(quote.id)
                    quote.isLiked = true
                } else {
                    viewModel.removeLikeQuote(quote.id)
                    quote.isLiked = false
                }
            },
            onShareQuoteClicked = {
                coroutineScope.launch {
                    handleQuoteShare(
                        quote = quote,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            },
            onCommentClicked = onCommentClicked
        )

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
                isPlaying = true,
                prepareOnly = true,
                viewModel = volumeControlViewModel
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
    viewModel: QuoteMainViewModel,
    navController: NavController
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
    viewModel.updateSelectedQuote(
        QuoteForOrderModel(
            id = quote.id,
            quote = quote.quote,
            writer = quote.writer,
            imageUrl = quote.imageUrl,
            currentPage = 0 // Default value since we don't have paging
        )
    )
    withContext(Dispatchers.Main) {
        navController.navigate(route = Screen.QuoteShareScreen.route, args = bundle)
    }
} 