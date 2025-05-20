package com.erdemserhat.harmonyhaven.presentation.post_authentication.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.os.Bundle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.platform.LocalConfiguration
import com.erdemserhat.harmonyhaven.presentation.post_authentication.settings.LocalGifImage
import kotlin.math.ceil

@Composable
fun ChatIntroScreen(navController: NavController) {
    // Color scheme
    val primaryColor = harmonyHavenGreen
    val darkPrimaryColor = harmonyHavenDarkGreenColor
    val lightGreen = harmonyHavenGreen.copy(alpha = 0.1f)
    val backgroundColor = Color.White
    val textColor = Color(0xFF333333)
    val cardColor = Color(0xFFF9F9F9)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF5F7F9)
                    )
                )
            ),
        bottomBar = {
            // Fixed bottom button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = { navController.navigate(Screen.ChatScreen.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Text(
                        text = "Sohbete Başla",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        fontFamily = ptSansFont
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Start",
                        tint = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Harmonia ile ihtiyacınız olan her konuda sohbet edin",
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = ptSansFont
                )
            }
        }
    ) { paddingValues ->
        // Main scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF5F7F9)
                    )
                ))
                .padding(paddingValues)
                .padding(top = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top section with title and icon
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(lightGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(R.drawable.ex),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Serhat, Harmonia Yanında",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkPrimaryColor,
                        textAlign = TextAlign.Center,
                        fontFamily = ptSansFont
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Size destek olmak ve tavsiye vermek için tasarlanmış özel asistanınız",
                    fontSize = 16.sp,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = ptSansFont
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            // İletişim Seçenekleri başlığı
            Text(
                text = "İletişim Seçenekleri",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = darkPrimaryColor,
                    textAlign = TextAlign.Start,
                    fontFamily = ptSansFont
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // İletişim seçenekleri kartları
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Metin ile Konuş kartı
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { navController.navigate(Screen.ChatScreen.route) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(lightGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.chat_message),
                                contentDescription = "Text Chat",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "Metin ile Konuş",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = darkPrimaryColor,
                                textAlign = TextAlign.Center,
                                fontFamily = ptSansFont
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = "Yazarak iletişim kur",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center,
                                fontFamily = ptSansFont
                            )
                        )
                    }
                }
                
                // Sesli Konuşma kartı
                Card(
                    modifier = Modifier
                        .weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        // Card content
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(lightGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.speaker),
                                    contentDescription = "Voice Chat",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Text(
                                text = "Sesli Konuş",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = darkPrimaryColor.copy(alpha = 0.7f),
                                    textAlign = TextAlign.Center,
                                    fontFamily = ptSansFont
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Text(
                                text = "Sesli komutlar gönder",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center,
                                    fontFamily = ptSansFont
                                )
                            )
                        }
                        
                        // Yakında etiketi
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = harmonyHavenDarkGreenColor
                        ) {
                            Text(
                                text = "Yakında",
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontFamily = ptSansFont
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Harmonia Özellikleri section
            Text(
                text = "Harmonia Özellikleri",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = darkPrimaryColor,
                    textAlign = TextAlign.Start,
                    fontFamily = ptSansFont
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Single feature card centered
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    BulletPoint(
                        "Enneagram kişilik analizi altyapısı",
                        primaryColor,
                        textColor
                    )
                    BulletPoint(
                        "Kişisel gelişim ve motivasyon konularında destek",
                        primaryColor,
                        textColor
                    )
                    BulletPoint(
                        "Günlük yaşam için pratik tavsiyeler ve yönlendirmeler",
                        primaryColor,
                        textColor
                    )
                    BulletPoint(
                        "Sorularınıza akıllı ve kişiselleştirilmiş yanıtlar",
                        primaryColor,
                        textColor
                    )
                }
            }

            // Add extra spacing at the bottom to account for the fixed button
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
private fun BulletPoint(text: String, bulletColor: Color, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(bulletColor)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 15.sp,
            color = textColor,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = ptSansFont
        )
    }
}

@Composable
private fun BulletPointSmall(text: String, bulletColor: Color, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(bulletColor)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            color = textColor,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = ptSansFont
        )
    }
}

@Composable
private fun ChatHistoryItem(title: String, timestamp: String, preview: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Sohbeti açma işlevi */ }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Sol taraftaki renkli nokta göstergesi
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(harmonyHavenGreen)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Sağ taraftaki içerik
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Başlık ve zaman bilgisi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontFamily = ptSansFont
                )

                Text(
                    text = timestamp,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = ptSansFont
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Önizleme metni
            Text(
                text = preview,
                fontSize = 14.sp,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = ptSansFont
            )
        }
    }
}

@Composable
fun FeatureItem(
    icon: ImageVector,
    title: String,
    description: String,
    primaryColor: Color,
    surfaceColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = surfaceColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(primaryColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = primaryColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = primaryColor,
                    fontFamily = ptSansFont
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.7f),
                    lineHeight = 20.sp,
                    fontFamily = ptSansFont
                )
            }
        }
    }
}

@Composable
fun ContinuousScrollingRow(
    topics: List<String>,
    isRightToLeft: Boolean,
    speed: Float,
    cardColor: Color,
    textColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "scroll")
    
    // Fixed spacing for train-like effect
    val cardSpacing = 8.dp
    val cardSpacingPx = with(LocalDensity.current) { cardSpacing.toPx() }
    
    // Set fixed card width
    val cardWidth = 120.dp
    val cardWidthPx = with(LocalDensity.current) { cardWidth.toPx() }
    
    // Calculate total width of one cycle
    val totalItemWidthPx = cardWidthPx + cardSpacingPx
    val totalWidthPx = topics.size * totalItemWidthPx
    
    // Calculate screen width to know how many copies we need
    val screenWidthPx = LocalConfiguration.current.screenWidthDp * with(LocalDensity.current) { 1.dp.toPx() }
    val requiredCopies = ceil(screenWidthPx / totalWidthPx + 2).toInt().coerceAtLeast(2)
    
    // Animation state
    val animatedOffset by infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = totalWidthPx.toInt(),
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = (totalWidthPx * 100 / speed).toInt(),
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "scrolling"
    )
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clipToBounds()
    ) {
        // We'll create multiple copies of the row to ensure perfect looping
        for (i in 0 until requiredCopies) {
            val offsetX = if (isRightToLeft) {
                // For right-to-left: start at right edge, move left
                -animatedOffset + (i * totalWidthPx).toInt()
            } else {
                // For left-to-right: start at left edge, move right
                animatedOffset - totalWidthPx.toInt() + (i * totalWidthPx).toInt()
            }
            
            Row(
                modifier = Modifier
                    .offset { IntOffset(offsetX, 0) }
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.spacedBy(cardSpacing)
            ) {
                topics.forEach { topic ->
                    Topic(
                        topic = topic,
                        cardColor = cardColor,
                        textColor = textColor,
                        cardWidth = cardWidth
                    )
                }
            }
        }
    }
}

@Composable
fun Topic(
    topic: String,
    cardColor: Color,
    textColor: Color,
    cardWidth: Dp
) {
    Card(
        modifier = Modifier
            .height(46.dp)
            .width(cardWidth)
            .clickable { /* Potentially navigate to chat with this topic */ },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = topic,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    fontFamily = ptSansFont
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TopicCard(
    topic: String,
    cardColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .height(46.dp)
            .widthIn(min = 100.dp)
            .wrapContentWidth()
            .clickable { /* Potentially navigate to chat with this topic */ },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = topic,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    fontFamily = ptSansFont
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}