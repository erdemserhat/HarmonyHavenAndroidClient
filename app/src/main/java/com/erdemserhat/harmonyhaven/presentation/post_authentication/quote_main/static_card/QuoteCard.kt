package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.static_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun QuoteText(
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

        Box(modifier = Modifier.fillMaxSize().background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.8f),
                        Color.Black.copy(alpha = 0.4f)
                    )
                ))){}

        // Show the quote sentence and writer if the sentence is not empty
        if (quoteSentence.isNotBlank()) {
            QuoteText(
                quote = quoteSentence,
                quoteWriter = quoteWriter,
                modifier = Modifier.align(Alignment.Center),
                isFirmLogoVisible = isFirmLogoVisible
            )
        }
    }
}
