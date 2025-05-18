package com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel
import com.erdemserhat.harmonyhaven.domain.model.rest.Category
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.navigation.navigate
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards.ArticleCard
import com.erdemserhat.harmonyhaven.presentation.post_authentication.home.composables.cards.ArticleSearchBarCard
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContentNew(
    isCategoryReady: Boolean,
    isArticlesReady: Boolean,
    navController: NavController,
    articles: List<ArticlePresentableUIModel>,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    allArticles: List<ArticlePresentableUIModel>,
    onRefreshed: (() -> Unit) -> Unit
) {


    var isRefreshing by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val activity = context as? Activity

    val window = activity?.window!!

    val scrollState = rememberScrollState()
    val enneagramArticles = articles.filter { it.category == "Enneagram" }
    val nonEnneagramArticles = articles.filter { it.category != "Enneagram" }

    // Get latest 4 articles for auto-slider
    val latestArticles = articles.take(4)

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            // Trigger refresh logic
            coroutineScope.launch {
                onRefreshed {
                    isRefreshing = false
                }
            }
        },
        modifier = Modifier.fillMaxSize() // Ensure it takes up the entire screen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8)) // Soft background color
                .verticalScroll(scrollState), // Make the main column scrollable
            horizontalAlignment = Alignment.Start,
        ) {
            HomeScreenIntroCard()
            
            // Latest Content Auto-Slider
            Spacer(modifier = Modifier.height(16.dp))
            AutoSlidingLatestContent(
                articles = latestArticles,
                navController = navController
            )

            // Enneagram Section
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.enneagram_black),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(Modifier.size(10.dp))
                Text(
                    text = "Enneagramı Keşfet",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333)
                )
            }

            LazyRow(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(enneagramArticles) { article ->
                    HorizontalArticleCard(
                        article = article,
                        onArticleClick = {
                            val bundle = Bundle()
                            bundle.putParcelable("article", article)
                            Log.d("articleCase", article.id.toString())
                            navController.navigate(
                                route = Screen.Article.route,
                                args = bundle
                            )
                        }
                    )
                }
            }

            // Popular Articles Section
            Spacer(modifier = Modifier.height(24.dp))

                Spacer(Modifier.size(10.dp))
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "İlginizi Çekebilecek İçerikler",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333)
                )

            // Vertical articles section - using Column instead of LazyColumn for nested scrolling
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                nonEnneagramArticles.forEach { article ->
                    VerticalArticleCard(
                        article = article,
                        onArticleClick = {
                            val bundle = Bundle()
                            bundle.putParcelable("article", article)
                            Log.d("articleCase", article.id.toString())
                            navController.navigate(
                                route = Screen.Article.route,
                                args = bundle
                            )
                        }
                    )
                }
                
                // Add some space at the bottom
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSlidingLatestContent(
    articles: List<ArticlePresentableUIModel>,
    navController: NavController
) {
    if (articles.isEmpty()) return
    
    Column {
        Spacer(Modifier.size(10.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Son Yayınlanan İçerikler",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF333333)
            )

        
        val pagerState = rememberPagerState(pageCount = { articles.size })
        val coroutineScope = rememberCoroutineScope()
        
        // Auto-sliding logic with softer transitions
        LaunchedEffect(Unit) {
            while (true) {
                delay(4000) // Wait 4 seconds before starting transition
                val nextPage = (pagerState.currentPage + 1) % articles.size
                // Smoother, longer animation
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(
                        durationMillis = 1000, // Longer animation (1 second)
                        easing = FastOutSlowInEasing // Smoother easing curve
                    )
                )
            }
        }
        
        Box(
                    modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(220.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                // Add page spacing for better visual separation
                pageSpacing = 8.dp
            ) { page ->
                val article = articles[page]
                
                // Calculate page offset for animation
                val pageOffset = (
                    (pagerState.currentPage - page) + pagerState
                        .currentPageOffsetFraction
                ).absoluteValue
                
                // Animate card properties based on offset
                AutoSlideCard(
                    article = article,
                    pageOffset = pageOffset,
                    onArticleClick = {
                        val bundle = Bundle()
                        bundle.putParcelable("article", article)
                        navController.navigate(
                            route = Screen.Article.route,
                            args = bundle
                        )
                    }
                )
            }
            
            // Page indicators
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                articles.forEachIndexed { index, _ ->
                    // Animated indicator size and color
                    val selected = pagerState.currentPage == index
                    val size by animateFloatAsState(
                        targetValue = if (selected) 10f else 8f,
                        animationSpec = tween(300, easing = FastOutSlowInEasing),
                        label = "indicator size"
                    )
                    
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(size.dp)
                            .clip(CircleShape)
                            .background(
                                if (selected) 
                                    harmonyHavenGreen 
                                else 
                                    Color.LightGray.copy(alpha = 0.5f)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun AutoSlideCard(
    article: ArticlePresentableUIModel,
    pageOffset: Float,
    onArticleClick: () -> Unit
) {
    // Calculate smoother animation values
    val scale = lerp(
        start = 0.90f, // Less scaling difference
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
    val alpha = lerp(
        start = 0.7f, // Higher minimum opacity
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
                // Add subtle rotation for more dynamic feel
                rotationY = pageOffset * 8f // Max 8 degree rotation
            }
            .clip(RoundedCornerShape(16.dp))
            .clickable { onArticleClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image
            AsyncImage(
                model = article.imagePath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            
            // Content overlay with semi-transparent background - softer gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.1f),
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    // Category badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(harmonyHavenGreen.copy(alpha = 0.9f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = article.category ?: "Genel",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Title
                    Text(
                        text = article.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Preview text
                    Text(
                        text = article.contentPreview ?: "",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(
    icon: Int,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
        Spacer(Modifier.size(10.dp))
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF333333)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalArticleCard(
    article: ArticlePresentableUIModel,
    onArticleClick: () -> Unit
) {
    ElevatedCard(
        onClick = { onArticleClick() },
        modifier = Modifier
            .width(220.dp)
            .height(240.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Article Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                AsyncImage(
                    model = article.imagePath,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                
                // Category badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(harmonyHavenGreen.copy(alpha = 0.8f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = article.category ?: "Genel",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            // Article Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                ) {
                    // Title
                    Text(
                        text = article.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp
                    )
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    // Content Preview
                    Text(
                        text = article.contentPreview ?: "",
                        fontSize = 12.sp,
                        color = Color(0xFF666666),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 15.sp
                    )
                }
                
                // Read more button at the bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                ) {
                    Text(
                        text = "Okumaya Devam Et →",
                        fontSize = 12.sp,
                        color = harmonyHavenGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalArticleCard(
    article: ArticlePresentableUIModel,
    onArticleClick: () -> Unit
) {
    ElevatedCard(
        onClick = { onArticleClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 6.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Article Image - Full width at the top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                AsyncImage(
                    model = article.imagePath,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                
                // Category badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(harmonyHavenGreen.copy(alpha = 0.8f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = article.category ?: "Genel",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            // Content area with fixed layout positions
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Content at the top
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                ) {
                    // Title
                    Text(
                        text = article.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Content Preview
                    Text(
                        text = article.contentPreview ?: "",
                        fontSize = 13.sp,
                        color = Color(0xFF666666),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 16.sp
                    )
                }
                
                // Button explicitly at the bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomEnd)
                ) {
                    // "Read more" button
                    Card(
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.BottomEnd),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF5F5F5)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Okumaya Devam Et →",
                            fontSize = 12.sp,
                            color = harmonyHavenGreen,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShimmerArticleCard(onItemClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(bottom = 30.dp)
            .fillMaxWidth(0.9f)
            .height(350.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable { onItemClick() }
            .placeholder(
                visible = true,
                color = Color.LightGray.copy(alpha = 0.4f),
                shape = RoundedCornerShape(12.dp),
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.LightGray.copy(alpha = 0.9f)
                )
            ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Content of the card can be added here if needed
    }
}


