package com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.Window
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
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
import androidx.compose.material3.Text
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.markdowntext.MarkdownText
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreenContent(
    article: ArticlePresentableUIModel,
    navController: NavController,
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
    val activity = context as? Activity
    val window = activity?.window!!
    var isFirstComposition by rememberSaveable { mutableStateOf(true) }
    var topBarHeight by remember { mutableStateOf(0) }
    val systemUiController = rememberSystemUiController()



    var isTopBarVisible by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()
    var lastScrollOffset by remember { mutableStateOf(0) }



// Track scroll direction
    LaunchedEffect(scrollState.value) {
        val delta = scrollState.value - lastScrollOffset // Difference between new & old position
        if (delta > 5) {
            isTopBarVisible = false // Scrolling down → Hide top bar
            //systemUiController.isSystemBarsVisible = false
        } else if (delta < -5) {
            isTopBarVisible = true  // Scrolling up → Show top bar
            //systemUiController.isSystemBarsVisible = true
        }
        lastScrollOffset = scrollState.value // Update last scroll position
    }



    LaunchedEffect(
        key1 = selectedBackgroundColorVariance,
        key2 = isFirstComposition,
        key3 = showBottomSheet,
    )
    {

        window.let {
            WindowCompat.setDecorFitsSystemWindows(
                it,
                false
            ) // content fill the system navbar- status bar
            val insetsController = WindowCompat.getInsetsController(it, it.decorView)

            it.statusBarColor = Color.Transparent.toArgb()
            it.navigationBarColor = selectedBackgroundColor.toArgb()

            insetsController.isAppearanceLightStatusBars =
                listOf(0, 1, 2, 3, 4).contains(selectedBackgroundColorVariance)
            insetsController.isAppearanceLightNavigationBars =
                listOf(0, 1, 2, 3, 4).contains(selectedBackgroundColorVariance) && !showBottomSheet


        }

        if (isFirstComposition) {
            isFirstComposition = false
        }


    }


    // Main content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(selectedBackgroundColor)
    ) {



        /////////////////////////
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
                    .fillMaxSize(),
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
        /////////////////////////
        val systemNavigationBarPadding =
            WindowInsets.systemBars.asPaddingValues().calculateTopPadding().value
        AnimatedVisibility(showBottomSheet) {
            ModalBottomSheet(
                scrimColor = Color.Transparent,
                onDismissRequest = {
                    coroutineScope.launch { showBottomSheet = false }
                },
                dragHandle = { BottomSheetDefaults.DragHandle() },
                sheetState = modalSheetState,
                containerColor = Color.Black.copy(alpha = 0.95f),
                contentColor = Color.Red,
                tonalElevation = 8.dp,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                modifier = Modifier
                    .wrapContentSize()
                    .offset(y = (2 * systemNavigationBarPadding).dp)

// Extends to system navigation bar
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // Font Size Slider
                    var sliderValue by rememberSaveable { mutableFloatStateOf(60f) }
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
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
                            modifier = Modifier.padding(10.dp),
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
                        text = "Arkaplan",
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
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

                    Spacer(modifier = Modifier.size((3 * systemNavigationBarPadding).dp))
                }
            }

        }
        var isLoading by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .padding(end = 16.dp, start = 16.dp, top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            Spacer(modifier = Modifier.size(50.dp))

                AsyncImage(
                    model = article.imagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .clip(RoundedCornerShape(8.dp))
                        .placeholder(
                            visible = isLoading,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.Gray.copy(0.3f)
                            ),
                            color = harmonyHavenComponentWhite
                        ),

                    contentScale = ContentScale.Fit,
                    onSuccess = { isLoading = false },
                    onError = { isLoading = false }
                )
                Spacer(modifier = Modifier.height(16.dp))





                // Define your custom font using FontFamily
                val customFont =
                    if (selectedFontStyle==0) (Font(R.font.opensans))
                    else if (selectedFontStyle==1) (Font(R.font.roboto))
                    else if (selectedFontStyle==2) (Font(R.font.kumbh_sans_light))
                    else if (selectedFontStyle==3) (Font(R.font.monster))
                    else (Font(R.font.lato))

                // Define the TextStyle with the custom font
                val customStyle = TextStyle(
                    fontSize=fontSize.sp,
                    color = selectedTextColor,
                    fontFamily = customFont.toFontFamily(),  // Applying custom font
                )

                if (article.content.isNotEmpty()) {
                    MarkdownText(
                        syntaxHighlightColor = Color.Black.copy(0.18f),
                        style = customStyle,
                        markdown = article.content,
                        isTextSelectable = true,
                    )
                }










        }



        AnimatedVisibility(
            visible = isTopBarVisible,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(), // Slide from top
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()  // Slide to top
        ) {
            ArticleScreenTopBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(10f),
                isContentLight = isTopBarContentLight,
                color = selectedBackgroundColor,
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

