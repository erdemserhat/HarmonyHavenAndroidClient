package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentModalBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: CommentViewModel = hiltViewModel(),
    sheetState: ModalBottomSheetState,
    postId: Int

) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val maxCommentLength = 1000 // Maksimum karakter sayısı

    val vibrator = context.getSystemService(Vibrator::class.java)



    LaunchedEffect(sheetState.isVisible) {
        if (sheetState.isVisible) {
            if(viewModel.lastPostId.value!=postId){
                viewModel.loadComments(postId)

            }else{
                viewModel.loadFromCache()
            }

        } else {
            keyboardController?.hide()
            viewModel.setLastPostId(postId)
            viewModel.resetList()
            viewModel.commitApiCallsWithoutDelay()
        }
    }


    var commentText by rememberSaveable {
        mutableStateOf("")
    }
    val isLoading by viewModel.isLoading.collectAsState()
    val comments by viewModel.comments.collectAsState()
    Box(modifier = modifier.fillMaxSize()) {

        ModalBottomSheetLayout(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(WindowInsets.statusBars.asPaddingValues()),
            sheetBackgroundColor = Color.Transparent,
            scrimColor = Color.Black.copy(alpha = 0.4f),
            sheetState = sheetState,
            sheetContent = {


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.Black, shape = RoundedCornerShape(
                                topEnd = 15.dp,
                                topStart = 15.dp
                            )
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
                                        verticalArrangement = Arrangement.spacedBy(0.dp), // Öğeler arasında boşluk
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
                                                            // Eğer beğenilmemişse beğen
                                                            viewModel.likeComment(clickedCommentId, postId)
                                                            comment.isLiked = true
                                                            Log.d("LikeClicked", "Comment ID: $clickedCommentId liked.")
                                                        } else {
                                                            // Eğer beğenilmişse beğeniyi geri al
                                                            viewModel.removeLikeFromComment(clickedCommentId, postId)
                                                            comment.isLiked = false
                                                            Log.d("LikeClicked", "Comment ID: $clickedCommentId like removed.")
                                                        }
                                                    } else {
                                                        Log.d("LikeClicked", "Invalid comment ID: $clickedCommentId")
                                                    }

                                                    if (vibrator?.hasVibrator() == true) {
                                                        vibrator.vibrate(
                                                            VibrationEffect.createOneShot(
                                                                25, // Süre (ms)
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
                                                                25, // Süre (ms)
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
                            // Emoji Seçimi
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 15.dp, vertical = 10.dp)
                                    .horizontalScroll(rememberScrollState())
                                    .background(Color.Black) // Enables horizontal scrolling
                            ) {
                                val emojis = listOf(
                                    "❤\uFE0F",
                                    "\uD83D\uDE4C",
                                    "\uD83D\uDD25",
                                    "\uD83D\uDC4F",
                                    "\uD83D\uDE22",
                                    "\uD83D\uDE0D",
                                    "\uD83D\uDE2E",
                                    "\uD83D\uDE02",
                                    "\uD83E\uDD73",
                                    "\uD83D\uDE0A",
                                    "\uD83D\uDC4C",
                                    "\uD83D\uDE0E",
                                    "\uD83D\uDE31",
                                    "\uD83D\uDE18",
                                    "\uD83D\uDE09",
                                    "\uD83D\uDE1C",
                                    "\uD83D\uDE14",
                                    "\uD83D\uDE12",
                                    "\uD83D\uDE1F",
                                    "\uD83D\uDE28",
                                    "\uD83D\uDE17",
                                    "\uD83D\uDE29",
                                    "\uD83D\uDE2F",
                                    "\uD83D\uDE05"
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

                            // Yorum Yapma Alanı
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
                                    colors = TextFieldDefaults.textFieldColors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        backgroundColor = Color.Black,
                                        cursorColor = Color.Gray
                                    ),
                                    textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
                                )

                                ClickableImage(
                                    onClick = {
                                        if (commentText.isNotEmpty()){
                                            viewModel.postComment(postId = postId, comment = commentText)
                                            vibrator.vibrate(
                                                VibrationEffect.createOneShot(
                                                    25, // Süre (ms)
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


            },
            content = {

            }

        )


        //AnimatedVisibility(
        //   modifier = Modifier.align(Alignment.BottomCenter),
        //   visible = sheetState.isVisible,
        //  enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
        //     initialOffsetY = { fullHeight -> fullHeight }
        // ),
        // exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(
        //      targetOffsetY = { fullHeight -> fullHeight }
        //  )
        //  ) {
        if (sheetState.isVisible) {

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
        Divider(
            color = Color.Gray, // Çizginin rengi
            thickness = 2.dp, // Çizginin kalınlığı
            modifier = Modifier
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(5.dp))
                .size(width = 45.dp, height = 5.dp)
                .background(color = Color.White)
        )
        Text(
            text = "Yorumlar",
            color = Color.White,
            modifier = Modifier.padding(8.dp),
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
