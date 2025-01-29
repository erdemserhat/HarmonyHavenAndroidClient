package com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
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
    var selectedFontStyle by remember { mutableStateOf(0) } // 0: Normal, 1: Bold, 2: Light, 3:

    val context = LocalContext.current

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Font Size Slider
                Text(
                    text = "A",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                
                Slider(
                    value = fontSize.toFloat(),
                    onValueChange = { fontSize = it.toInt() },
                    valueRange = ArticleScreenConstants.MIN_FONT_SIZE.toFloat()..ArticleScreenConstants.MAX_FONT_SIZE.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                // Font Style Options
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    items(4) { index ->
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
                    items(5) { index ->
                        ColorOption(
                            color = when(index) {
                                0 -> Color.White
                                1 -> Color(0xFFF5F5F5)
                                2 -> Color(0xFFE8F5E9)
                                3 -> Color(0xFFFFF3E0)
                                else -> Color(0xFFE3F2FD)
                            },
                            isSelected = false,
                            onClick = { /* Handle color selection */ }
                        )
                    }
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                modifier = Modifier.background(Color.White),
                topBar = {
                    ArticleScreenTopBar(
                        onTextFontMinusClicked = { if (fontSize >= ArticleScreenConstants.MIN_FONT_SIZE) fontSize-- },
                        onTextFontPlusClicked = { if (fontSize <= ArticleScreenConstants.MAX_FONT_SIZE) fontSize++ },
                        navController = navController,
                        onShareButtonClicked = {
                            // Makalenin linkini oluştur
                            val link = "https://harmonyhaven.erdemserhat.com/articles/${article.id}/${article.slug}"

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
                        article = article, 
                        fontSize = fontSize,
                        modifier = Modifier.background(Color.White)
                    )
                },
                floatingActionButton = {
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
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        backgroundColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Customize",
                            tint = Color.White
                        )
                    }
                }
            )
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
            .size(40.dp)
            .clip(CircleShape)
            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "A",
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun ColorOption(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
    )
}
