package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.static_card

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun QuoteCard(
    modifier: Modifier = Modifier,
    quoteSentence: String,
    quoteURL: String,
    quoteWriter: String,
    shouldAnimate: Boolean = true,
    isFirmLogoVisible: Boolean = false
) {
    Box(modifier = modifier) {
        // Display the full-screen image
        QuoteTextCardBackground(
            imageUrl = quoteURL,
            modifier = Modifier.align(Alignment.Center),
            shouldAnimate = shouldAnimate
        )

        // Show the quote sentence and writer if the sentence is not empty
        if (quoteSentence.isNotBlank()) {
            QuoteCard(
                quote = quoteSentence,
                quoteWriter = quoteWriter,
                modifier = Modifier.align(Alignment.Center),
                isFirmLogoVisible = isFirmLogoVisible
            )
        }
    }
}
