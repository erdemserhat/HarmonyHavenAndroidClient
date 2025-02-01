package com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
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
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.BottomSheetDefaults
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreenContent(
    article: ArticlePresentableUIModel,
    navController: NavController
) {
    var fontSize by rememberSaveable { mutableIntStateOf(ArticleScreenConstants.DEFAULT_FONT_SIZE) }
    val modalSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }


    val coroutineScope = rememberCoroutineScope()
    var selectedFontStyle by remember { mutableIntStateOf(0) }
    var selectedTextColor by remember { mutableStateOf(Color.Black) }
    var selectedBackgroundColor by remember { mutableStateOf(Color.White) }
    var selectedBackgroundColorVariance by rememberSaveable { mutableIntStateOf(0) }
    var isTopBarContentLight by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current


    // Main content
    Box(modifier = Modifier
        .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .zIndex(1f)
                .align(Alignment.CenterEnd)
                .offset(
                    x = animateDpAsState(
                        targetValue = if (showBottomSheet) 10.dp else 28.dp,
                        animationSpec = tween(durationMillis = 300)
                    ).value
                )
                .size(56.dp)
                .clip(RoundedCornerShape(topStart = 28.dp, bottomStart = 28.dp))
                .background(harmonyHavenGreen)
        ) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        showBottomSheet = !showBottomSheet
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                ,
                containerColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Customize",
                    tint = Color.White
                )
            }
        }


        Scaffold(
            modifier = Modifier
                .background(selectedBackgroundColor)
               ,
            topBar = {
                ArticleScreenTopBar(
                    isContentLight = isTopBarContentLight,
                    color = selectedBackgroundColor,
                    onTextFontMinusClicked = { if (fontSize >= ArticleScreenConstants.MIN_FONT_SIZE) fontSize-- },
                    onTextFontPlusClicked = { if (fontSize <= ArticleScreenConstants.MAX_FONT_SIZE) fontSize++ },
                    navController = navController,
                    onShareButtonClicked = {
                        val link =
                            "https://harmonyhaven.erdemserhat.com/articles/${article.id}/${article.slug}"
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, link)
                        }
                        val shareChooser = Intent.createChooser(shareIntent, "Makaleyi Paylaş")
                        context.startActivity(shareChooser)
                    }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                Log.d(
                                    "DEBUG1222",
                                    "Scaffold tapped. Sheet visible: ${modalSheetState.isVisible}"
                                )
                            }
                        }) {
                    ArticleContent(
                        selectedFontStyle = selectedFontStyle,
                        article = article,
                        fontSize = fontSize,
                        modifier = Modifier
                            .background(selectedBackgroundColor),
                        textColor = selectedTextColor
                    )

                }



            },
        )


    }


    AnimatedVisibility(showBottomSheet) {
        ModalBottomSheet(
            scrimColor = Color.Transparent,
            onDismissRequest = {
                coroutineScope.launch { showBottomSheet = false }
            },
            sheetState = modalSheetState,
            containerColor = Color.Black.copy(alpha = 0.95f),
            contentColor = Color.Red,
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Font Size Slider
                var sliderValue by rememberSaveable { mutableFloatStateOf(60f) }
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        fontFamily = Font(R.font.play).toFontFamily(),
                        text = "A-",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Slider(
                        colors = SliderDefaults.colors(
                            thumbColor = harmonyHavenGreen,
                            activeTrackColor = harmonyHavenGreen,
                            inactiveTrackColor = Color.DarkGray.copy(alpha = 0.5f)
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
                                0 -> Color(0xFF121212) to Color(0xFFE0E0E0)
                                1 -> Color(0xFF1E1E1E) to Color(0xFFB0BEC5)
                                2 -> Color(0xFF263238) to Color(0xFFCFD8DC)
                                3 -> Color(0xFF212121) to Color(0xFFF5F5F5)
                                4 -> Color(0xFF2C2C2C) to Color(0xFFE0E0E0)
                                5 -> Color(0xFFFFFFFF) to Color(0xFF212121)
                                6 -> Color(0xFFF5F5F5) to Color(0xFF212121)
                                7 -> Color(0xFFFFFDE7) to Color(0xFF424242)
                                8 -> Color(0xFFF0F4C3) to Color(0xFF2E2E2E)
                                9 -> Color(0xFFE3F2FD) to Color(0xFF1B1B1B)
                                10 -> Color(0xFFFFF3E0) to Color(0xFF3E3E3E)
                                else -> Color(0xFFF5F5F5) to Color(0xFF212121)
                            },
                            isSelected = selectedBackgroundColorVariance == index,
                            onClick = { text, bg ->
                                selectedBackgroundColorVariance = index
                                selectedTextColor = text
                                selectedBackgroundColor = bg
                                isTopBarContentLight = index in 5..10
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.size(50.dp))
            }
        }

    }

}

@Composable
private fun ColorOption(
    color: Pair<Color, Color>,
    isSelected: Boolean,
    onClick: (background: Color, textColor: Color) -> Unit
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
                onClick(color.first, color.second)
            }
    )
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
            .border(1.dp, harmonyHavenGreen, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            fontFamily = Font(R.font.play).toFontFamily(),
            fontSize = 20.sp,
            text = "A",
            color = if (isSelected) Color.White else Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

