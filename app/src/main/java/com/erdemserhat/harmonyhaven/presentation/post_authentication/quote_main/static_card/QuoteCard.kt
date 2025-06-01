package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.static_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

/**
 * A composable that displays a quote card with background image, gradient overlay, and quote text.
 * The card can be animated and optionally show a firm logo.
 *
 * @param modifier Modifier for customizing the layout
 * @param quoteSentence The main quote text to display
 * @param quoteURL URL of the background image
 * @param quoteWriter Name of the quote's author
 * @param shouldAnimate Whether to animate the card's appearance
 * @param isFirmLogoVisible Whether to show the firm logo
 */
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
        // Background image with optional animation
        QuoteTextCardBackground(
            imageUrl = quoteURL,
            modifier = Modifier.align(Alignment.Center),
            shouldAnimate = shouldAnimate
        )

        // Gradient overlay for better text readability
        GradientOverlay()

        // Quote text and writer name
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

/**
 * Displays a radial gradient overlay to improve text readability.
 */
@Composable
private fun GradientOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 1f),
                        Color.Black.copy(alpha = 0.38f),
                    )
                )
            )
    )
}
