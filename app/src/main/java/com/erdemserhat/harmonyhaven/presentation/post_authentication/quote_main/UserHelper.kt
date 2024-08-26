package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import LocalGifImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun UxScrollInformer(modifier: Modifier = Modifier, onClick: () -> Unit) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)), contentAlignment = Alignment.Center
    ) {

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
                    R.drawable.scroll_anim,
                    modifier
                        .width(60.dp)
                        .height(100.dp)
                )


            }

            Text(
                text = "Alıntıları okumak için kaydır",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(end = 5.dp, start = 5.dp),
                textAlign = TextAlign.Center,
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

@Composable
fun UxScrollInformer2(modifier: Modifier = Modifier, onClick: () -> Unit) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)), contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .width(350.dp)
                .clip(RoundedCornerShape(25.dp))//
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            // İçeriği merkezde hizala
        ) {

            Spacer(modifier = Modifier.size(20.dp))


            Text(
                text = "İçeriklere göz atmak, bildirimlerini okumak ve hesap ayarların için sağ alt köşedeki butona tıklayınız.",
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                onClick = { onClick() },
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .width(300.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = harmonyHavenGreen
                )

            ) {
                Text(text = "Anladım!", fontSize = 20.sp, color = Color.White)

            }
            Spacer(modifier = Modifier.size(20.dp))


        }


    }


}


@Composable
fun UxAuthHelper(modifier: Modifier = Modifier, onClick: () -> Unit) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)), contentAlignment = Alignment.Center
    ) {

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
                Spacer(modifier = Modifier.size(15.dp))
                Image(
                    painter = painterResource(id = R.drawable.noint1),
                    contentDescription = null, modifier = Modifier.size(80.dp),

                    )


            }

            Text(
                text = "Lütfen internet bağlantınızı kontrol edin",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(end = 5.dp, start = 5.dp),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
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
                Text(text = "Tekrar Dene!", fontSize = 20.sp, color = Color.White)

            }


        }

    }


}

@Composable
fun UxSessionExp(modifier: Modifier = Modifier, onClick: () -> Unit) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)), contentAlignment = Alignment.Center
    ) {

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
                Spacer(modifier = Modifier.size(15.dp))
                Image(
                    painter = painterResource(id = R.drawable.sessionex),
                    contentDescription = null, modifier = Modifier.size(80.dp),

                    )


            }

            Text(
                text = "Oturumunuzun süresi doldu lütfen tekrar giriş yapın.",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(end = 5.dp, start = 5.dp),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
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
                Text(text = "Tekrar Giriş Yap", fontSize = 20.sp, color = Color.White)

            }


        }

    }


}