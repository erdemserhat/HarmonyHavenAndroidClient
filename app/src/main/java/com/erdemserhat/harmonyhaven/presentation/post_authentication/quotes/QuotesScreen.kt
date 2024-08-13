package com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes

import LocalGifImage
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.presentation.navigation.AnimatedGif


@Composable
fun QuotesScreen(viewmodel: QuotesViewModel = hiltViewModel()) {
    val quotes = viewmodel.quotes.collectAsState()

    QuotesContent(quotes.value)


}

@Composable
fun QuotesContent(quote: List<com.erdemserhat.harmonyhaven.dto.responses.Quote>) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,

        ) {

        Box(modifier = Modifier
            .align(Alignment.Center)
            .height(300.dp)){
            AnimatedGif(R.drawable.bird2,true, duration = 10000, modifier = Modifier.align(Alignment.Center))
            AnimatedGif(R.drawable.bird,false, duration = 15000,modifier = Modifier.align(Alignment.Center))
            LocalGifImage(R.drawable.examplecloud, modifier = Modifier
                .align(Alignment.Center)
                .size(700.dp))

        }

        Image(painter = painterResource(id = R.drawable.sun), contentDescription = null, modifier = Modifier.align(
            Alignment.TopEnd))

        Image(painter = painterResource(id = R.drawable.forest), contentDescription = null, modifier = Modifier.align(
            Alignment.BottomCenter).aspectRatio(1.6f))
        QuoteVerticalList(quoteList = quote, modifier = Modifier.align(Alignment.Center))
        



    }


}


@Composable
fun Quote(quote: Quote) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = quote.quote,
                modifier = Modifier.padding(30.dp),
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center

            )

            Text(
                text = quote.writer
            )

        }


        /*

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
            ) {

                Image(
                    imageVector = Icons.Outlined.ThumbUp,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(20.dp)
                        .size(32.dp)
                )


                Image(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(20.dp)
                        .size(32.dp)
                )
            }

        }
        */

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
                    .align(Alignment.Center), state = pagerState
            ) { page ->
                val quote = quoteList[page]

                Quote(quote = quote)


            }


        }


    }
}
