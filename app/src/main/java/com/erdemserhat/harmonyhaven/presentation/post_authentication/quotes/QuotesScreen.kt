package com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.dto.responses.Quote


@Composable
fun QuotesScreen(
    viewmodel: QuotesViewModel = hiltViewModel(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val quotes = viewmodel.quotes.collectAsState()



    Box(modifier = modifier.fillMaxSize()) {


        QuotesContent(quotes.value)
    }


}

@Composable
fun QuotesContent(quote: List<Quote>) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        //.background(harmonyHavenComponentWhite),
        contentAlignment = Alignment.Center,

        ) {

        QuoteVerticalList(quoteList = quote, modifier = Modifier.align(Alignment.Center))

    }


    //Image(painter = painterResource(id = R.drawable.sun), contentDescription = null, modifier = Modifier.align(
    //    Alignment.TopEnd))

    // Image(painter = painterResource(id = R.drawable.forest), contentDescription = null, modifier = Modifier.align(
    //   Alignment.BottomCenter).aspectRatio(1.6f))


}


val imageUrls = arrayOf(
    "https://fotolifeakademi.com/uploads/2020/04/manzara-fotografi-cekmek.jpg",
    "https://images.pexels.com/photos/1172064/pexels-photo-1172064.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
    "https://media.istockphoto.com/id/1297349747/tr/foto%C4%9Fraf/t%C3%BCrkiyede-botan-kanyonu-%C3%BCzerinde-u%C3%A7an-s%C4%B1cak-hava-balonlar%C4%B1.jpg?s=612x612&w=0&k=20&c=cB1OwAy1ndPfcjp_Mt7n0rLub2hiSzgMj-qBXHSrprU="
)


@Composable
fun FullScreenImage(imageUrl: String, modifier: Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Full screen image",
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop // Resmi kapsama oranını ayarlar
    )
}


@Composable
fun Quote(quote: Quote, modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxSize()

    ) {
      //  FullScreenImage(
          //  quote.imageUrl,
         //   modifier = Modifier

      //  )

        if(!quote.imageUrl.endsWith(".mp4")){
              FullScreenImage(
              quote.imageUrl,
               modifier = Modifier
              )

        }else{

           // FullScreenVideoPlayer(quote.imageUrl)

        }


        Box(
            modifier = Modifier
                .padding(15.dp) // Kenar boşlukları
                .wrapContentSize()
                .align(Alignment.Center)
                .background(
                    color = Color.White.copy(alpha = 0.5f), // Beyaz arka plan ve şeffaflık
                    shape = RoundedCornerShape(16.dp) // Köşe yuvarlama
                )

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.wrapContentSize()
            ) {
                Text(
                    text = quote.quote,
                    modifier = Modifier.padding(30.dp),
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = quote.writer,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(15.dp))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuoteVerticalList(
    quoteList: List<Quote>, modifier: Modifier
) {

    if (quoteList.isNotEmpty()) {


        val pagerState = rememberPagerState(pageCount = {
            quoteList.size
        })


        Box(modifier = modifier) {
            VerticalPager(
                modifier = Modifier
                    .fillMaxSize()
                    //.background(Color.Cyan)
                    .align(Alignment.Center), state = pagerState
            ) { page ->
                val quote = quoteList[page]
                Quote(quote = quote, modifier = Modifier.zIndex(2f))


            }


        }


    }
}
