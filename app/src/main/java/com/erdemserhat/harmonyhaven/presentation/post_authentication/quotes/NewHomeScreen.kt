package com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes

import android.graphics.drawable.Icon
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientWhite


data class Quote(
    var quote: String,
    var writer: String
)

val quoteList = listOf(
    Quote("The only way to do great work is to love what you do.", "Steve Jobs"),
    Quote("In the end, it's not the years in your life that count. It's the life in your years.", "Abraham Lincoln"),
    Quote("Life is what happens when you're busy making other plans.", "John Lennon"),
    Quote("Life is not about finding yourself. Life is about creating yourself.", "George Bernard Shaw"),
    Quote("Life is 10% what happens to us and 90% how we react to it.", "Charles R. Swindoll"),
    Quote("Success is not final, failure is not fatal: It is the courage to continue that counts.", "Winston Churchill"),
    Quote("The only impossible journey is the one you never begin.", "Tony Robbins"),
    Quote("The best way to predict the future is to create it.", "Peter Drucker"),
    Quote("The only limit to our realization of tomorrow will be our doubts of today.", "Franklin D. Roosevelt"),
    Quote("The future belongs to those who believe in the beauty of their dreams.", "Eleanor Roosevelt"),
    Quote("Happiness is not something ready-made. It comes from your own actions.", "Dalai Lama"),
    Quote("The purpose of our lives is to be happy.", "Dalai Lama"),
    Quote("Life is a succession of lessons which must be lived to be understood.", "Helen Keller"),
    Quote("Life is either a daring adventure or nothing at all.", "Helen Keller"),
    Quote("The only thing necessary for the triumph of evil is for good men to do nothing.", "Edmund Burke"),
    Quote("Life is like riding a bicycle. To keep your balance, you must keep moving.", "Albert Einstein")
)


@Composable
fun QuotesScreen() {

    QuotesContent(quoteList)


}

@Composable
fun QuotesContent(quote: List<Quote>) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,

        )
    {
        Image(
            painter = painterResource(R.drawable.background
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )




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
                modifier = Modifier
                    .padding(30.dp),
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center

            )

            Text(
                text = quote.writer
            )

        }




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
    }


}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    QuotesContent(quoteList)

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuoteVerticalList(
    quoteList: List<Quote>,
    modifier: Modifier
) {
    if (quoteList.isNotEmpty()) {


        val pagerState = rememberPagerState(pageCount = {
            quoteList.size
        })

        Box(modifier = modifier) {
            VerticalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                state = pagerState
            ) { page ->
                val quote = quoteList[page]

                Quote(quote = quote)


            }


        }


    }
}
