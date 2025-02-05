package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.R
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CommentModalBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: CommentViewModel,
    sheetState: SheetState,
    postId: Int,
    onDismissRequest: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val maxCommentLength = 1000 // Maximum character count


    val vibrator = context.getSystemService(Vibrator::class.java)


    var commentText by rememberSaveable { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()
    val comments by viewModel.comments.collectAsState()
    val isImeVisible = WindowInsets.isImeVisible
    var isScrolledUp by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(isImeVisible) {
        if (isImeVisible) {
            isScrolledUp = true
        }
    }

    val heightFraction by animateFloatAsState(
        targetValue = if (isScrolledUp) 1f else 0.65f,
        animationSpec = tween(
            durationMillis = 400, // Duration of the animation
            delayMillis = 0, // Optional delay before the animation starts
        )
    )




    ModalBottomSheet(
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { _, dragAmount ->
                            // Detect the scroll direction based on drag amount
                            if (dragAmount > 0) {
                                isScrolledUp = false
                                if(heightFraction ==0.65f){
                                    coroutineScope.launch {
                                        sheetState.hide()
                                        onDismissRequest()

                                    }
                                }

                            } else if (dragAmount < 0) {
                                // The user is scrolling up (negative value)
                                isScrolledUp = true

                            }
                        }
                    },
                contentAlignment = Alignment.Center,
                content = {
                    BottomSheetDefaults.DragHandle()
                }
            )
        },
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .fillMaxHeight(fraction = heightFraction)
            .imePadding(),
        containerColor = Color.Black,
        scrimColor = Color.Black.copy(alpha = 0.4f),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        // Detect the scroll direction based on drag amount
                        if (dragAmount > 0) {
                            // The user is scrolling down (positive value)
                            isScrolledUp = false
                            if(heightFraction ==0.65f){
                                coroutineScope.launch {
                                    sheetState.hide()
                                    onDismissRequest()


                                }
                            }
                        } else if (dragAmount < 0) {
                            // The user is scrolling up (negative value)
                            isScrolledUp = true

                        }
                    }
                }
                .background(
                    Color.Black,
                    shape = RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp)
                )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ModalBottomTitle(modifier = Modifier.align(Alignment.CenterHorizontally))
                    if (isLoading) {
                        repeat(10) {
                            CommentBlockShimmer()
                        }
                    } else {
                        if (comments.isNotEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .padding(
                                        bottom = 120.dp,
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 16.dp
                                    ),
                                verticalArrangement = Arrangement.spacedBy(0.dp)
                            ) {
                                comments.forEach { comment ->
                                    CommentBlock(
                                        commentId = comment.id,
                                        hasOwnerShip = comment.hasOwnership,
                                        date = comment.date,
                                        author = comment.author,
                                        sending = comment.id == -1,
                                        content = comment.content,
                                        likeCount = comment.likeCount,
                                        profilePhotoUrl = comment.authorProfilePictureUrl,
                                        isLiked = comment.isLiked,
                                        onLikedClicked = { clickedCommentId ->
                                            if (clickedCommentId != -1) {
                                                if (!comment.isLiked) {
                                                    viewModel.likeComment(clickedCommentId, postId)
                                                    comment.isLiked = true
                                                    Log.d(
                                                        "LikeClicked",
                                                        "Comment ID: $clickedCommentId liked."
                                                    )
                                                } else {
                                                    viewModel.removeLikeFromComment(
                                                        clickedCommentId,
                                                        postId
                                                    )
                                                    comment.isLiked = false
                                                    Log.d(
                                                        "LikeClicked",
                                                        "Comment ID: $clickedCommentId like removed."
                                                    )
                                                }
                                            } else {
                                                Log.d(
                                                    "LikeClicked",
                                                    "Invalid comment ID: $clickedCommentId"
                                                )
                                            }

                                            if (vibrator?.hasVibrator() == true) {
                                                vibrator.vibrate(
                                                    VibrationEffect.createOneShot(
                                                        25,
                                                        VibrationEffect.DEFAULT_AMPLITUDE
                                                    )
                                                )
                                            }
                                        },
                                        onDeleteComment = {
                                            viewModel.deleteComment(comment.id, postId)
                                            if (vibrator?.hasVibrator() == true) {
                                                vibrator.vibrate(
                                                    VibrationEffect.createOneShot(
                                                        25,
                                                        VibrationEffect.DEFAULT_AMPLITUDE
                                                    )
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        } else {
                            Text(
                                "Henüz bir yorum yok...",
                                color = Color.White,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(25.dp)
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .background(Color.Black)
                        .align(Alignment.BottomCenter)
                ) {
                    // Emoji Selection
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 10.dp)
                            .horizontalScroll(rememberScrollState())
                            .background(Color.Black)
                    ) {
                        val emojis = listOf(
                            "❤️",
                            "🙌",
                            "🔥",
                            "👏",
                            "😢",
                            "😍",
                            "😮",
                            "😂",
                            "🥳",
                            "😊",
                            "👌",
                            "😎",
                            "😱",
                            "😘",
                            "😉",
                            "😜",
                            "😔",
                            "😒",
                            "😟",
                            "😨",
                            "😗",
                            "😩",
                            "😯",
                            "😅"
                        )
                        emojis.forEach { emoji ->
                            Text(
                                text = emoji,
                                fontSize = 25.sp,
                                modifier = Modifier
                                    .padding(end = 15.dp)
                                    .clickable {
                                        if (commentText.length < maxCommentLength) {
                                            commentText += emoji
                                        }
                                    }
                            )
                        }
                    }

                    // Comment Input Area
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        AsyncImage(
                            model = viewModel.profilePhotoPath,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(100)),
                            contentDescription = null
                        )

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .background(Color.Black),
                            onValueChange = { newText ->
                                if (newText.length <= maxCommentLength) {
                                    commentText = newText
                                }
                            },
                            value = commentText,
                            placeholder = {
                                Text(
                                    text = "Yorum yaz",
                                    color = Color.Gray
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.Black,
                                unfocusedContainerColor = Color.Black,
                                cursorColor = Color.Gray,
                                selectionColors = TextSelectionColors(
                                    handleColor = Color.Gray,
                                    backgroundColor = harmonyHavenGreen
                                )
                            ),
                            textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
                        )

                        ClickableImage(
                            onClick = {
                                if (commentText.isNotEmpty()) {
                                    viewModel.postComment(postId = postId, comment = commentText)
                                    vibrator.vibrate(
                                        VibrationEffect.createOneShot(
                                            25,
                                            VibrationEffect.EFFECT_TICK
                                        )
                                    )
                                }
                                commentText = ""
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ModulePreview() {
    //CommentModalBottomSheet()
}

@Composable
private fun ModalBottomTitle(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Yorumlar",
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 15.sp
        )
    }
}


@Composable
fun ClickableImage(onClick: () -> Unit) {
    // Track whether the image is clicked
    var clicked by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(true) } // Track whether the button is enabled
    val coroutineScope = rememberCoroutineScope()

    // Modify the image size based on the click state
    val scale by animateFloatAsState(targetValue = if (clicked) 1.2f else 1f)

    // Handle the image scaling back after a delay
    LaunchedEffect(clicked) {
        if (clicked) {
            delay(300) // Wait for 300ms before scaling back to the original size
            clicked = false // Reset the state to scale it back
        }
    }

    val startTimer = {
        coroutineScope.launch {
            delay(3000) // Adjust the delay duration as needed
            isEnabled = true
        }
    }

    Image(
        modifier = Modifier
            .size(35.dp)
            .clip(RoundedCornerShape(100))
            .graphicsLayer(scaleX = scale, scaleY = scale) // Apply scale animation
            .clickable(enabled = isEnabled) { // Use the isEnabled flag
                clicked = true
                isEnabled = false
                onClick()
                startTimer.invoke()
            },
        painter = painterResource(id = R.drawable.comment_arrow_up),
        contentDescription = null,
        colorFilter = if (!isEnabled) {
            ColorFilter.tint(Color.Gray) // Apply gray color when disabled
        } else {
            null // No color filter when enabled
        }
    )
}
