package com.erdemserhat.harmonyhaven.presentation.quotes

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGradientWhite


data class Quote(
    var quote: String,
    var writer: String
)

val quoteList = listOf(
    Quote("Hayatta en hakiki mürşit ilimdir, fendir.", "Hz. Ali"),
    Quote("Bütün renkler beyazın içindedir.", "Necip Fazıl Kısakürek"),
    Quote("Hayat bir gölgedir ki, kaybolup gitmektedir.", "William Shakespeare"),
    Quote(
        "Güzel olan her şeyin doğru olan her şeye yakıştığı gibi, doğru olan her şeyin de güzel olan her şeye yakıştığı bir dünya hayal ediyorum.",
        "Platon"
    ),
    Quote("Hayatta en büyük zafer insanın kendi nefsiyle yaptığı mücadeledir.", "Platon"),
    Quote("Dünyada en hakiki mürşit ilimdir, fendir.", "Hz. Ali"),
    Quote("Bir insanı tanımanın yolu, onunla seyahat etmektir.", "Amerikan atasözü"),
    Quote("Kendini tanımanın yolu, dünyayı görmektir.", "Goethe"),
    Quote("Hayatı sevin, çünkü aynı çiçek bir daha açmayabilir.", "Muhammed Ali"),
    Quote(
        "Gelecek, görmek isteyenler için değil, onu kendi elleriyle kuranlar içindir.",
        "Jules Verne"
    ),
    Quote(
        "Yaşamak, hatırlamak, düşünmek, hayal kurmak, yaratmak için değil, yaşamak için yaşamalı.",
        "Hermann Hesse"
    )
)

@Composable
fun QuotesScreen() {

    QuotesContent(quoteList)


}

@Composable
fun QuotesContent(quote: List<Quote>) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(

                Color.White
            ),
        contentAlignment = Alignment.Center,

        )
    {
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
