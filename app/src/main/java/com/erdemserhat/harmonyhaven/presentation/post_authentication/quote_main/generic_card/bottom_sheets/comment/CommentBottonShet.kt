package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentModalBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: CommentViewModel = hiltViewModel(),
    sheetState: ModalBottomSheetState,
    postId: Int

) {
    LaunchedEffect(sheetState.isVisible) {
        if (sheetState.isVisible) {
            viewModel.loadComments(postId)
        }else{
            viewModel.resetList()
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
                .background(Color.Transparent),
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
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 110.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp), // Öğeler arasında boşluk
                                    contentPadding = PaddingValues(16.dp) // Kenar boşlukları
                                ) {
                                    // items fonksiyonu doğru şekilde kullanılıyor
                                    items(items = comments) { comment ->
                                        AnimatedVisibility(true) {
                                            CommentBlock(
                                                hasOwnerShip = comment.hasOwnership,
                                                date = comment.date,
                                                author = comment.author,
                                                content = comment.content,
                                                likeCount = comment.likeCount,
                                                profilePhotoUrl = comment.authorProfilePictureUrl,
                                                _isLiked = comment.isLiked,
                                                onLikedClicked = {
                                                    viewModel.likeComment(comment.id,postId)

                                                },
                                                onUnlikeClicked = {
                                                    viewModel.removeLikeFromComment(comment.id,postId)


                                                },
                                                onDeleteComment = {
                                                    viewModel.deleteComment(comment.id,postId)
                                                }

                                            )

                                        }

                                    }
                                }


                            }


                        }
                        Column(
                            modifier = Modifier
                                .background(Color.Black)
                                .align(Alignment.BottomCenter)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 15.dp, vertical = 10.dp)
                                    .horizontalScroll(rememberScrollState())
                                    .background(Color.Black) // Enables horizontal scrolling
                            ) {
                                val emojis = listOf(
                                    "❤\uFE0F", "\uD83D\uDE4C", "\uD83D\uDD25", "\uD83D\uDC4F", "\uD83D\uDE22", "\uD83D\uDE0D",
                                    "\uD83D\uDE2E", "\uD83D\uDE02", "\uD83E\uDD73", "\uD83D\uDE0A", "\uD83D\uDC4C",
                                    "\uD83D\uDE0E", "\uD83D\uDE31", "\uD83D\uDE18", "\uD83D\uDE09", "\uD83D\uDE1C",
                                    "\uD83D\uDE14", "\uD83D\uDE12", "\uD83D\uDE1F", "\uD83D\uDE28", "\uD83D\uDE17",
                                    "\uD83D\uDE29", "\uD83D\uDE2F", "\uD83D\uDE05"
                                )

                                emojis.forEach { emoji ->
                                    Text(
                                        text = emoji,
                                        fontSize = 25.sp,
                                        modifier = Modifier
                                            .padding(end = 15.dp)
                                            .clickable {
                                                commentText += emoji
                                            }
                                    )
                                }
                            }

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
                                    onValueChange = { commentText = it },
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
                                        viewModel.postComment(postId = postId, comment = commentText)
                                        commentText = "" // Yorum gönderildikten sonra alanı temizler
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

    // Modify the image size based on the click state
    val scale by animateFloatAsState(targetValue = if (clicked) 1.2f else 1f)

    // Handle the image scaling back after a delay
    LaunchedEffect(clicked) {
        if (clicked) {
            delay(300) // Wait for 300ms before scaling back to the original size
            clicked = false // Reset the state to scale it back
        }
    }

    Image(
        modifier = Modifier
            .size(35.dp)
            .clip(RoundedCornerShape(100))
            .graphicsLayer(scaleX = scale, scaleY = scale) // Apply scale animation
            .clickable {
                // Toggle the clicked state to trigger the animation
                clicked = true
                onClick()
            },
        painter = painterResource(id = R.drawable.comment_arrow_up),
        contentDescription = null
    )
}