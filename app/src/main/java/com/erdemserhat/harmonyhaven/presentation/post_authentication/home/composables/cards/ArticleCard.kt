package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.ui.theme.DefaultAppFont
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

private fun convertToTurkishDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("tr", "TR"))
        val date = inputFormat.parse(dateString)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        Log.e("DateConversion", "Error converting date: ${e.message}")
        dateString // Return original string if parsing fails
    }
}

@Composable
fun ArticleCard(
    article: ArticlePresentableUIModel,
    modifier: Modifier = Modifier,
    onReadButtonClicked: () -> Unit = {}
) {
    var isPressed by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "scale"
    )

    val elevation by animateFloatAsState(
        targetValue = if (isPressed) 2f else 8f,
        animationSpec = tween(durationMillis = 150),
        label = "elevation"
    )

    LaunchedEffect(Unit) {

        Log.d("dsadasdas", convertToTurkishDate(article.publishDate))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = Color.Black.copy(alpha = 0.55f),
                ambientColor = harmonyHavenGreen.copy(alpha = 0.05f)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Main Card with Glassmorphism Effect
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale)
                .clip(RoundedCornerShape(28.dp))

                .clickable(
                ) {
                    onReadButtonClicked()
                },
            colors = CardColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.98f),
                                Color.White.copy(alpha = 0.95f)
                            )
                        )
                    )
            ) {
                Column {
                    // Image Section with Gradient Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f)
                    ) {
                        AsyncImage(
                            model = article.imagePath,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .placeholder(
                                    visible = isLoading,
                                    highlight = PlaceholderHighlight.shimmer(
                                        highlightColor = Color.Gray.copy(0.3f)
                                    ),
                                    color = harmonyHavenComponentWhite
                                ),
                            contentScale = ContentScale.Crop,
                            onSuccess = { isLoading = false },
                            onError = { isLoading = false }
                        )

                        // Gradient Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.35f)
                                        )
                                    )
                                )
                        )

                        // Category Tag with Glass Effect
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp)
                                .background(
                                    color = harmonyHavenGreen.copy(alpha = 0.85f),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(16.dp),
                                    spotColor = Color.Black.copy(alpha = 0.1f)
                                )
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = article.category!!,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = DefaultAppFont
                            )
                        }
                    }

                    // Content Section with subtle pattern
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.99f),
                                        Color.White.copy(alpha = 0.97f)
                                    )
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            // Title with Modern Typography
                            Text(
                                text = article.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = DefaultAppFont,
                                color = harmonyHavenDarkGreenColor,
                                lineHeight = 24.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Content Preview with Better Readability
                            Text(
                                text = article.contentPreview,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = DefaultAppFont,
                                color = Color.Black.copy(alpha = 0.65f),
                                lineHeight = 20.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Bottom Bar with Gradient
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                harmonyHavenGreen.copy(alpha = 0.06f),
                                                Color.Transparent
                                            )
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Date with Icon
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.information_icon),
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = Color.Gray.copy(alpha = 0.6f)
                                    )
                                    Text(
                                        text = convertToTurkishDate(article.publishDate),
                                        fontSize = 13.sp,
                                        color = Color.Gray.copy(alpha = 0.6f),
                                        fontFamily = DefaultAppFont,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                // Read More Button with Animation
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "Devamını Oku",
                                        fontSize = 13.sp,
                                        color = harmonyHavenGreen,
                                        fontFamily = DefaultAppFont,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.arrow_back),
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = harmonyHavenGreen
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}