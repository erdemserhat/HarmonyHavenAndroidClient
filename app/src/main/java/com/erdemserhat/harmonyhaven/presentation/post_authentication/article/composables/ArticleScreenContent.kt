package com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.article.ArticleScreenConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.draw.clip
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleScreenContent(
    article: ArticlePresentableUIModel,
    navController: NavController
) {
    var fontSize by rememberSaveable { mutableIntStateOf(ArticleScreenConstants.DEFAULT_FONT_SIZE) }
    val modalSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true

    )
    val coroutineScope = rememberCoroutineScope()
    var selectedFontStyle by remember { mutableIntStateOf(0) } // 0: Normal, 1: Bold, 2: Light, 3:

    var selectedTextColor by remember {
        mutableStateOf(Color.Black)
    }

    var selectedBackgroundColor by remember {
        mutableStateOf(Color.White)
    }

    var selectedBackgroundColorVariance by rememberSaveable {
        mutableIntStateOf(0)
    }

    var isTopBarContentLight by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    ModalBottomSheetLayout(
        scrimColor = Color.Transparent,
        sheetBackgroundColor = Color.Black.copy(alpha = 0.95f),
        sheetState = modalSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Font Size Slider

                var sliderValue by rememberSaveable {
                    mutableFloatStateOf(60f)
                }

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        fontFamily = Font(R.font.play).toFontFamily(),
                        text = "A-",
                        color = Color.White,
                        fontSize = 20.sp
                    )

                    Slider(
                        colors = SliderDefaults.colors(
                            thumbColor = harmonyHavenGreen, // Color of the slider thumb
                            activeTrackColor = harmonyHavenGreen, // Color of the active part of the track
                            inactiveTrackColor = Color.DarkGray.copy(alpha = 0.5f) // Color of the inactive part of the track
                        ),
                        value = sliderValue,
                        onValueChange = {
                            sliderValue = it
                            fontSize = (ArticleScreenConstants.MIN_FONT_SIZE +
                                    (it / 100f) * (ArticleScreenConstants.MAX_FONT_SIZE - ArticleScreenConstants.MIN_FONT_SIZE)).toInt()

                        },
                        valueRange = 0f..100f,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(vertical = 8.dp)
                    )

                    Text(
                        fontFamily = Font(R.font.play).toFontFamily(),
                        text = "A+",
                        color = Color.White,
                        fontSize = 30.sp
                    )
                }



                // Font Style Options
                LazyRow(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    items(5) { index ->
                        Spacer(modifier = Modifier.size(6.dp))
                        FontStyleOption(
                            isSelected = selectedFontStyle == index,
                            onClick = { selectedFontStyle = index }
                        )
                    }
                }

                // Background Color Options
                Text(
                    text = "Background Color",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(11) { index ->
                        ColorOption(
                            color = when (index) {
                                0 -> Color(0xFF121212) to Color(0xFFE0E0E0) // Dark Gray with Light Gray Text
                                1 -> Color(0xFF1E1E1E) to Color(0xFFB0BEC5) // Charcoal with Muted Gray Text
                                2 -> Color(0xFF263238) to Color(0xFFCFD8DC) // Blue-Gray with Light Cyan-Gray Text
                                3 -> Color(0xFF212121) to Color(0xFFF5F5F5) // Rich Black with Off-White Text
                                4 -> Color(0xFF2C2C2C) to Color(0xFFE0E0E0) // Deep Warm Gray with Soft Light Gray Text
                                5 -> Color(0xFFFFFFFF) to Color(0xFF212121) // Pure White with Dark Gray Text
                                6 -> Color(0xFFF5F5F5) to Color(0xFF212121) // Light Gray with Dark Gray Text
                                7 -> Color(0xFFFFFDE7) to Color(0xFF424242) // Soft Yellow with Charcoal Gray Text
                                8 -> Color(0xFFF0F4C3) to Color(0xFF2E2E2E) // Pale Lime with Deep Gray Text
                                9 -> Color(0xFFE3F2FD) to Color(0xFF1B1B1B) // Pale Blue with Dark Blue-Gray Text
                                10 -> Color(0xFFFFF3E0) to Color(0xFF3E3E3E) // Pale Orange with Dark Warm Gray Text
                                else -> Color(0xFFF5F5F5) to Color(0xFF212121) // Default Light Gray with Dark Gray Text
                            },
                            isSelected = selectedBackgroundColorVariance.toInt() ==index,
                            onClick = {text,bg->
                                selectedBackgroundColorVariance=index
                                selectedTextColor = text
                                selectedBackgroundColor = bg
                                if(listOf(5,6,7,8,9,10).contains(index)){
                                    isTopBarContentLight = true
                                }else{
                                    isTopBarContentLight = false

                                }



                            }
                        )
                    }
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                modifier = Modifier.background(selectedBackgroundColor),
                topBar = {
                    ArticleScreenTopBar(
                        isContentLight = isTopBarContentLight,
                        color = selectedBackgroundColor,
                        onTextFontMinusClicked = { if (fontSize >= ArticleScreenConstants.MIN_FONT_SIZE) fontSize-- },
                        onTextFontPlusClicked = { if (fontSize <= ArticleScreenConstants.MAX_FONT_SIZE) fontSize++ },
                        navController = navController,
                        onShareButtonClicked = {
                            // Makalenin linkini oluştur
                            val link =
                                "https://harmonyhaven.erdemserhat.com/articles/${article.id}/${article.slug}"

                            // Paylaşım Intent'i oluştur
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain" // Paylaşılacak içeriğin türü (metin)
                                putExtra(Intent.EXTRA_TEXT, link) // Paylaşılacak metin (link)
                            }

                            // Paylaşım seçeneklerini göster
                            val shareChooser = Intent.createChooser(shareIntent, "Makaleyi Paylaş")

                            // Intent'i başlat (Activity'den Context kullanarak)
                            context.startActivity(shareChooser)


                        }
                    )
                },
                content = {
                    ArticleContent(
                        selectedFontStyle = selectedFontStyle,
                        article = article,
                        fontSize = fontSize,
                        modifier = Modifier.background(selectedBackgroundColor),
                        textColor = selectedTextColor
                    )
                },
                floatingActionButton = {

                }
            )



                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .offset(x = animateDpAsState(
                                targetValue = if (modalSheetState.isVisible) 10.dp else 28.dp, // 50% offset when invisible
                                animationSpec = tween(durationMillis = 300) // Smooth animation
                            ).value)
                            .size(56.dp) // Match the size of the FloatingActionButton
                            .clip(RoundedCornerShape(
                                topStart = 28.dp, // 50% of the button's height
                                bottomStart = 28.dp, // 50% of the button's height
                                topEnd = 0.dp, // Square right side
                                bottomEnd = 0.dp // Square right side
                            ))
                            .background(harmonyHavenGreen) // Set the background color
                    ) {
                        FloatingActionButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (modalSheetState.isVisible) {
                                        modalSheetState.hide()
                                    } else {
                                        modalSheetState.show()
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxSize() // Fill the Box
                                .align(Alignment.CenterEnd), // Align the button inside the Box
                            containerColor = Color.Transparent, // Make the button transparent
                            elevation = FloatingActionButtonDefaults.elevation(0.dp) // Remove default elevation
                        ) {
                            Icon(
                                modifier = Modifier.size(25.dp),
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Customize",
                                tint = Color.White
                            )
                        }
                    }
        }
    }
}

@Composable
private fun FontStyleOption(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(45.dp)
            .clip(CircleShape)
            .background(if (isSelected) harmonyHavenGreen else Color.Transparent)
            .border(1.dp, harmonyHavenGreen , CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            fontFamily = Font(R.font.play).toFontFamily(),
            fontSize = 20.sp,
            text = "A",
            color = if (isSelected) Color.White else  Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun ColorOption(
    color: Pair<Color,Color>,
    isSelected: Boolean,
    onClick: (background:Color, textColor:Color) -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color.second)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) harmonyHavenGreen else Color.DarkGray.copy(alpha = 0.5f),
                shape = CircleShape
            )
            .clickable {
                onClick(color.first,color.second)
            }
    )
}
fun makeColorDarker(color: Color, factor: Float = 0.8f): Color {
    return Color(
        red = (color.red * factor).coerceIn(0f, 1f),
        green = (color.green * factor).coerceIn(0f, 1f),
        blue = (color.blue * factor).coerceIn(0f, 1f),
        alpha = color.alpha // Keep alpha unchanged
    )
}


fun mixColors(color1: Color, color2: Color, ratio: Float = 0.5f): Color {
    val clampedRatio = ratio.coerceIn(0f, 1f) // Ensure ratio is between 0 and 1

    val red = (color1.red * clampedRatio) + (color2.red * (1 - clampedRatio))
    val green = (color1.green * clampedRatio) + (color2.green * (1 - clampedRatio))
    val blue = (color1.blue * clampedRatio) + (color2.blue * (1 - clampedRatio))
    val alpha = (color1.alpha * clampedRatio) + (color2.alpha * (1 - clampedRatio))

    return Color(red, green, blue, alpha)
}