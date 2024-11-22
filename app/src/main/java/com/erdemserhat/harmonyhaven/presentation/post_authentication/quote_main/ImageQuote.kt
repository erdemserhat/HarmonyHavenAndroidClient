package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quotes.FullScreenImage

@Composable
fun ImageQuote(
    modifier: Modifier = Modifier,
    quoteSentence: String,
    quoteURL: String,
    quoteWriter: String,
    shouldAnimate: Boolean = true,
    isFirmLogoVisible: Boolean = false
) {

    Box(modifier = modifier) {

        FullScreenImage(
            quoteURL,
            modifier = Modifier.align(Alignment.Center),
            shouldAnimate = shouldAnimate
        )

        if (quoteSentence != "") {
            QuoteSentence(
                quote = quoteSentence,
                quoteWriter = quoteWriter,
                modifier = Modifier.align(Alignment.Center),
                isFirmLogoVisible = isFirmLogoVisible
            )

        }


    }


}