package com.erdemserhat.comment_feature.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.erdemserhat.comment_feature.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentModuleExample(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    )

    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Expanded
    )


    ModalBottomSheetLayout(
        sheetBackgroundColor = Color.Transparent,
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
                Box {
                    Column {
                        ModalBottomTitle(modifier = Modifier.align(Alignment.CenterHorizontally))
                        CommentBlock(
                            date = "5m",
                            author = "Serhat ERDEM",
                            content = "Lorem Ipsum, dizgi ve baskı endüstrisinde kullanılan mıgır metinlerdir.",
                            likeCount = 8,
                            replyCount = 1,
                            isLiked = true,
                            drawable = R.drawable.examplepp
                        )
                        CommentBlock(
                            date = "5m",
                            author = "Serhat ERDEM",
                            content = "Lorem Ipsum, dizgi ve baskı endüstrisinde kullanılan mıgır metinlerdir.",
                            likeCount = 8,
                            replyCount = 1,
                            isLiked = true,
                            drawable = R.drawable.examplepp
                        )
                        CommentBlock(
                            date = "12m",
                            author = "Serhat Erdem",
                            content = "Lorem Ipsum, dizgi ve baskı endüstrisinde kullanılan mıgır metinlerdir. Lorem Ipsum, adı bilinmeyen bir matbaacının bir hurufat numune kitabı oluşturmak üzere bir yazı galerisini alarak karıştırdığı 1500'lerden beri endüstri standardı sahte metinler olarak kullanılmıştır.",
                            likeCount = 15,
                            replyCount = 3,
                            isLiked = false,
                            drawable = R.drawable.examplepp
                        )
                        CommentBlock(
                            date = "12m",
                            author = "Serhat Erdem",
                            content = "Lorem Ipsum, dizgi ve baskı endüstrisinde kullanılan mıgır metinlerdir. Lorem Ipsum, adı bilinmeyen bir matbaacının bir hurufat numune kitabı oluşturmak üzere bir yazı galerisini alarak karıştırdığı 1500'lerden beri endüstri standardı sahte metinler olarak kullanılmıştır.",
                            likeCount = 15,
                            replyCount = 3,
                            isLiked = false,
                            drawable = R.drawable.examplepp
                        )


                    }

                    Row(modifier = Modifier.align(Alignment.BottomStart)) {

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
    CommentModuleExample()
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
private fun CommentBlock(
    modifier: Modifier = Modifier,
    date: String = "19s",
    author: String = "Serhat Erdem",
    content: String = "Bazen dış dünyada başarı ararken, kendimizi ihmal ediyoruz. Asıl yolculuk, içsel potansiyelimizin farkına varmak ve onu geliştirerek ilerlemektir. Bu söz, bana önce kendimizi tanımanın ve içsel gücümüzle hareket etmenin önemini hatırlatıyor.",
    likeCount: Int = 12,
    replyCount: Int = 2,
    isLiked: Boolean = false,
    drawable: Int

) {
    Column {
        Box(modifier = modifier.padding(vertical = 20.dp, horizontal = 5.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.Top)
                        .size(40.dp)
                        .clip(shape = RoundedCornerShape(100)),
                    painter = painterResource(id = drawable),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.size(8.dp))

                Column {
                    Row {
                        Text(
                            text = author,
                            color = Color.White,
                            fontSize = 14.sp

                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = date,
                            color = Color.Gray.copy(alpha = 0.8f),
                            fontSize = 12.sp

                        )
                    }


                    Spacer(modifier = Modifier.size(4.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            Text(
                                modifier = Modifier.fillMaxWidth(0.9f),
                                text = content,
                                color = Color.White,
                                fontSize = 13.sp,

                                )

                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = "Yanıtla",
                                color = Color.Gray.copy(alpha = 0.8f),
                                fontSize = 12.sp

                            )
                        }


                        LikeButton(
                            modifier = Modifier.align(Alignment.TopEnd),
                            isLiked = isLiked,
                            likeCount = likeCount


                        )


                    }


                    Spacer(modifier = Modifier.size(8.dp))

                    if (replyCount > 0) {
                        Row {
                            Spacer(modifier = Modifier.width(30.dp))
                            Text(
                                text = "$replyCount diğer yanıtı gör",
                                color = Color.Gray.copy(alpha = 0.8f),
                                fontSize = 12.sp

                            )
                        }
                    }

                }


            }


        }


    }
}


@Composable
fun LikeButton(
    modifier: Modifier = Modifier,
    likeCount: Int = 0,
    isLiked: Boolean = true,

    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(end = 8.dp)
    ) {
        Image(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = if (isLiked) R.drawable.likedredfilled else R.drawable.likedwhiteunfilled),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(5.dp))
        Text(likeCount.toString(), color = Color.White, fontSize = 10.sp)

    }
}



