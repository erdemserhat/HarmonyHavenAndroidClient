package com.erdemserhat.comment_feature.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.erdemserhat.comment_feature.R

@Composable
fun CommentBlock(
    modifier: Modifier = Modifier,
    date: String = "19s",
    author: String = "Serhat Erdem",
    content: String = "Bazen dış dünyada başarı ararken, kendimizi ihmal ediyoruz. Asıl yolculuk, içsel potansiyelimizin farkına varmak ve onu geliştirerek ilerlemektir. Bu söz, bana önce kendimizi tanımanın ve içsel gücümüzle hareket etmenin önemini hatırlatıyor.",
    likeCount: Int = 12,
    replyCount: Int = 2,
    _isLiked: Boolean = false,
    profilePhotoUrl: String = "R.drawable.examplepp",
    isMainComment: Boolean = true,

    ) {

    var shouldShowSubComments by rememberSaveable {
        mutableStateOf(false)
    }

    var isLiked by rememberSaveable {
        mutableStateOf(_isLiked)
    }


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = modifier.padding(vertical = 20.dp, horizontal = 5.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                AsyncImage(
                    model = profilePhotoUrl,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.Top)
                        .size(if (isMainComment) 40.dp else 30.dp)
                        .clip(shape = RoundedCornerShape(100)),
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
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .padding(end = 5.dp),
                                text = content,
                                color = Color.White,
                                fontSize = 13.sp,

                                )

                            Spacer(modifier = Modifier.size(5.dp))
                        }


                        LikeButton(
                            modifier = Modifier.align(Alignment.TopEnd),
                            isLiked = isLiked,
                            likeCount = likeCount,
                            onLikeClicked = { isLiked = !isLiked }


                        )


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
    onLikeClicked: (Boolean) -> Unit = {}
) {
    // Animasyon için scale değeri
    val scale by animateFloatAsState(
        targetValue = if (isLiked) 1.2f else 1f, // liked durumuna göre büyütme
        animationSpec = tween(
            durationMillis = 300,  // Animasyon süresi
            easing = FastOutSlowInEasing  // Easing ile animasyon geçişi
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(end = 8.dp)
    ) {
        Image(
            modifier = Modifier
                .size(15.dp) // Buton boyutu
                .scale(scale) // Animasyonu uygula
                .clickable { onLikeClicked(!isLiked) }, // Butona tıklayınca like state'ini değiştir
            painter = painterResource(id = if (isLiked) R.drawable.likedredfilled else R.drawable.likedwhiteunfilled),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(5.dp))
        Text(likeCount.toString(), color = Color.White, fontSize = 10.sp)
    }
}


@Composable
fun CommentBlockShimmer(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = modifier.padding(vertical = 20.dp, horizontal = 5.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                ShimmerEffect(
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.Top)
                        .size(40.dp)
                        .clip(shape = RoundedCornerShape(100))
                )





                Spacer(modifier = Modifier.size(8.dp))

                Column {
                    Row {
                        ShimmerEffect(
                            modifier = Modifier
                                .fillMaxWidth(0.45f)
                                .height(12.dp)
                                .clip(RoundedCornerShape(50))
                        )
                    }


                    Spacer(modifier = Modifier.size(6.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column {

                            ShimmerEffect(
                                modifier = Modifier
                                    .height(12.dp)
                                    .fillMaxWidth(0.9f)
                                    .clip(RoundedCornerShape(50))

                            )

                        }



                    }




                }


            }


        }


    }

}