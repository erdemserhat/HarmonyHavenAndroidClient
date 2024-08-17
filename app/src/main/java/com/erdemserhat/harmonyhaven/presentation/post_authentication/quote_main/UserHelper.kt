package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import LocalGifImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenIndicatorColor

@Composable
fun UxScrollInformer(modifier: Modifier = Modifier,onClick:()->Unit ){

    Box(modifier = modifier.fillMaxSize().background(color = Color.Black.copy(alpha = 0.5f)), contentAlignment = Alignment.Center) {

        Box(
            modifier = Modifier
                .width(350.dp)
                .height(250.dp)
                .clip(RoundedCornerShape(25.dp))//
                .background(Color.White),
            contentAlignment = Alignment.Center
            // İçeriği merkezde hizala
        ) {

            Column(modifier = Modifier.align(Alignment.TopCenter)) {
                Spacer(modifier = Modifier.size(5.dp))
                LocalGifImage(
                    R.drawable.scroll_anim,modifier.width(60.dp).height(100.dp))


            }

            Text(
                text = "Alıntıları okumak için kaydır",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 18.sp
            )
            Button(
                onClick = { onClick() },
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .width(300.dp)
                    .height(50.dp)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = harmonyHavenGreen
                )

            ) {
                Text(text = "Anladım!", fontSize = 20.sp, color = Color.White)

            }


        }

    }


}


@Preview
@Composable
private fun Prev() {
   // UxScrollInformer()

}