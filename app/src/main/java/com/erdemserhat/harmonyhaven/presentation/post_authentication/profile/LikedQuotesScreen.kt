package com.erdemserhat.harmonyhaven.presentation.post_authentication.profile

import android.media.MediaMetadataRetriever
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.dto.responses.Quote
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.QuoteMainViewModel
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card.VolumeControlViewModel
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.foundation.Image
import android.graphics.Bitmap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikedQuotesScreen(
    navController: NavController,
    viewModel: QuoteMainViewModel? = hiltViewModel(),
    volumeControlViewModel: VolumeControlViewModel? = null
) {
    // Mock data for liked quotes
    val mockLikedQuotes = listOf(
        Quote(
            id = 1,
            quote = "Hayatta en büyük zafer, her düştüğünde tekrar ayağa kalkabilmektir.",
            writer = "Nelson Mandela",
            imageUrl = "https://www.harmonyhavenapp.com/sources/quote_assets/1soru.1cevap/1soru.1cevap-20240915-0001.mp4",
            quoteCategory = 1,
            isLiked = true
        ),
        Quote(
            id = 2,
            quote = "Başarı, son değil; başarısızlık, ölümcül değil: önemli olan devam etme cesaretindir.",
            writer = "Winston Churchill",
            imageUrl = "https://www.harmonyhavenapp.com/sources/quote_assets/30112024/avahapky/avahapky-20241130-0001.mp4",
            quoteCategory = 2,
            isLiked = true
        ),
        Quote(
            id = 3,
            quote = "Geleceği tahmin etmenin en iyi yolu onu yaratmaktır.",
            writer = "Peter Drucker",
            imageUrl = "https://www.harmonyhavenapp.com/sources/article_images/bariyerler_olmadan_dusun.png",
            quoteCategory = 1,
            isLiked = true
        ),
        Quote(
            id = 4,
            quote = "Değişim acı verebilir, ama değişmemek daha da acı vericidir.",
            writer = "Tony Robbins",
            imageUrl = "https://harmonyhavenapp.com/sources/quotes/4.jpg",
            quoteCategory = 3,
            isLiked = true
        ),
        Quote(
            id = 5,
            quote = "Hayallerinizi gerçekleştirmek için önce uyanmanız gerekir.",
            writer = "Josiah Gilbert Holland",
            imageUrl = "https://harmonyhavenapp.com/sources/quotes/5.jpg",
            quoteCategory = 2,
            isLiked = true
        ),
        Quote(
            id = 6,
            quote = "Başarı, hazırlık ile fırsatın buluştuğu andır.",
            writer = "Bobby Unser",
            imageUrl = "https://harmonyhavenapp.com/sources/quotes/6.jpg",
            quoteCategory = 1,
            isLiked = true
        ),
        Quote(
            id = 7,
            quote = "Kendinize inanın ve ne istediğinizi biliyorsanız, başaracaksınız.",
            writer = "Johann Wolfgang von Goethe",
            imageUrl = "https://harmonyhavenapp.com/sources/quotes/7.jpg",
            quoteCategory = 2,
            isLiked = true
        ),
        Quote(
            id = 8,
            quote = "Büyük işler yapmak için büyük riskler almak gerekir.",
            writer = "Heraclitus",
            imageUrl = "https://harmonyhavenapp.com/sources/quotes/8.jpg",
            quoteCategory = 3,
            isLiked = true
        ),
        Quote(
            id = 9,
            quote = "Mükemmellik bir hedefe değil, bir yolculuğa dairdir.",
            writer = "Arthur Ashe",
            imageUrl = "https://harmonyhavenapp.com/sources/quotes/9.jpg",
            quoteCategory = 1,
            isLiked = true
        )
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Beğendiğim Sözler",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = harmonyHavenDarkGreenColor,
                        fontFamily = ptSansFont
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = harmonyHavenDarkGreenColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(paddingValues)
        ) {
            // Quote count info
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${mockLikedQuotes.size} beğenilen söz",
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    fontFamily = ptSansFont
                )
            }

            // Grid of liked quotes
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(mockLikedQuotes) { index, quote ->
                    SimplifiedQuoteCard(
                        quote = quote,
                        onClick = {
                            // Navigate to full Quote screen
                            navController.navigate(Screen.QuoteMain.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SimplifiedQuoteCard(
    quote: Quote,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    var videoThumbnail by remember { mutableStateOf<Bitmap?>(null) }
    val isVideo = quote.imageUrl.endsWith(".mp4", ignoreCase = true)
    
    // Extract video thumbnail for mp4 files
    LaunchedEffect(quote.imageUrl) {
        if (isVideo) {
            try {
                withContext(Dispatchers.IO) {
                    val retriever = MediaMetadataRetriever()
                    try {
                        retriever.setDataSource(quote.imageUrl, HashMap<String, String>())
                        val bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                        videoThumbnail = bitmap
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        retriever.release()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    Box(
        modifier = Modifier
            .height(200.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() }
    ) {
        // Show video thumbnail or image
        if (isVideo && videoThumbnail != null) {
            // Show video thumbnail
            Image(
                bitmap = videoThumbnail!!.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else if (!isVideo) {
            // Show image directly
            AsyncImage(
                model = quote.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Loading state for video thumbnail
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Yükleniyor...",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = ptSansFont
                )
            }
        }
        
        // Dark overlay for better text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )
        
        // Quote text - only show for non-video files
        if (!isVideo) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = quote.quote,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = ptSansFont,
                    textAlign = TextAlign.Center,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
            }
        }
        
        // Video indicator for mp4 files
        if (isVideo) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "▶",
                    color = Color.White,
                    fontSize = 10.sp
                )
            }
        }
    }
} 