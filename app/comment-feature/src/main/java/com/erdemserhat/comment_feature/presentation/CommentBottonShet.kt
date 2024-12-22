package com.erdemserhat.comment_feature.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.erdemserhat.comment_feature.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentModalBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: CommentViewModel = hiltViewModel(),
    sheetState: ModalBottomSheetState

) {
    LaunchedEffect(Unit) {
        viewModel.loadComments()
    }
    var commentText by rememberSaveable {
        mutableStateOf("")
    }
    val isLoading  by viewModel.isLoading.collectAsState()
    val comments by viewModel.comments.collectAsState()



    ModalBottomSheetLayout(
        modifier = modifier
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
                        if(isLoading){
                            repeat(5){
                                CommentBlockShimmer()
                            }


                        }else{
                            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                                comments.forEach{
                                    CommentBlock(
                                        date = it.date,
                                        author = it.author,
                                        content = it.content,
                                        likeCount = it.likeCount,
                                        replyCount = it.replyCount,
                                        profilePhotoUrl = it.authorProfilePictureUrl,

                                        )

                                }
                            }

                        }



                    }

                    Column(modifier = Modifier.align(Alignment.BottomStart)) {
                        Row(modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp).horizontalScroll(
                            rememberScrollState()
                        ).background(Color.Black), // Enables horizontal scrolling
                        ) {
                            Text("❤\uFE0F", fontSize = 25.sp)
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE4C", fontSize = 25.sp)
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDD25", fontSize = 25.sp)
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDC4F", fontSize = 25.sp)
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE22", fontSize = 25.sp)
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE0D", fontSize = 25.sp)
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE2E", fontSize = 25.sp)
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE02", fontSize = 25.sp)
                            // Additional Emojis
                            Text("\uD83E\uDD73", fontSize = 25.sp) // Face with hand over mouth (surprised or shy)
                            Spacer(modifier = Modifier.size(20.dp))

                            Text("\uD83D\uDE0A", fontSize = 25.sp) // Smiling face with smiling eyes
                            Spacer(modifier = Modifier.size(20.dp))

                            Text("\uD83D\uDC4C", fontSize = 25.sp) // OK hand sign
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE0E", fontSize = 25.sp) // Smiling face with sunglasses
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE31", fontSize = 25.sp) // Face screaming in fear
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE18", fontSize = 25.sp) // Face throwing a kiss
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE09", fontSize = 25.sp) // Winking face
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE1C", fontSize = 25.sp) // Face with stuck-out tongue and winking eye
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE14", fontSize = 25.sp) // Pensive face
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE12", fontSize = 25.sp) // Face with no mouth
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE1F", fontSize = 25.sp) // Face with steam from nose
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE28", fontSize = 25.sp) // Fearful face
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE17", fontSize = 25.sp) // Kiss mark
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE29", fontSize = 25.sp) // Weary face
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE2F", fontSize = 25.sp) // Face with open mouth and cold sweat
                            Spacer(modifier = Modifier.size(20.dp))
                            Text("\uD83D\uDE05", fontSize = 25.sp) // Face with tears of joy

                        }

                        Row() {
                            Image(
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 5.dp , top = 5.dp, bottom = 5.dp)
                                    .align(Alignment.Top)
                                    .size(40.dp)
                                    .clip(shape = RoundedCornerShape(100)),
                                painter = painterResource(id = R.drawable.examplepp),
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
                                        "yorum yaz",
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.CenterVertically) // Yerleşim düzenlemesi
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedLabelColor = Color.Transparent,
                                    focusedLabelColor = Color.Gray,
                                    cursorColor = Color.Gray,
                                    placeholderColor = Color.Gray,
                                ),
                                textStyle = TextStyle(color = Color.White, fontSize = 16.sp), // TextStyle ile hizalamayı düzenleyebilirsiniz
                            )


                            ClickableImage()



                        }

                    }


                }


            }
        },
        content = {

        }

    )

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
fun ClickableImage() {
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
            },
        painter = painterResource(id = R.drawable.comment_arrow_up),
        contentDescription = null
    )
}