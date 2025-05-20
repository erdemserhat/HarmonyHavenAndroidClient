package com.erdemserhat.harmonyhaven.presentation.post_authentication.chat

import android.app.Activity
import android.os.VibrationEffect
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.markdowntext.MarkdownText
import com.erdemserhat.harmonyhaven.presentation.navigation.Screen
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment.ClickableImage
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.WindowInsets

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel(), navController: NavController) {
    val state = viewModel.chatState.collectAsState()
    var text by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isKeyboardVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current
    val activity = context as? Activity
    val window = activity?.window!!
    
    // State for customization dialog
    var showCustomizationDialog by remember { mutableStateOf(false) }

    // Auto-scroll to bottom when new messages arrive or current message updates
    LaunchedEffect(state.value.messages.size, state.value.currentMessage) {
        if (state.value.messages.isNotEmpty() || state.value.currentMessage.isNotEmpty()) {
            val messageCount = state.value.messages.size
            val hasPartialMessage = state.value.currentMessage.isNotEmpty()

            // Add 1 to account for the partial message item if it exists
            val targetIndex = if (messageCount > 0) {
                messageCount - 1 + (if (hasPartialMessage) 1 else 0)
            } else 0

            if (targetIndex > 0) {
                listState.animateScrollToItem(targetIndex)
            }
        }
    }

    // Auto-scroll to bottom when keyboard appears
    LaunchedEffect(isKeyboardVisible) {
        if (isKeyboardVisible) {
            val messageCount = state.value.messages.size
            val hasPartialMessage = state.value.currentMessage.isNotEmpty()
            val targetIndex = if (messageCount > 0) {
                messageCount - 1 + (if (hasPartialMessage) 1 else 0)
            } else 0

            if (targetIndex > 0) {
                listState.animateScrollToItem(targetIndex)
            }
        }
    }
    
    // Customization dialog
    if (showCustomizationDialog) {
        CustomizationDialog(
            onDismiss = { showCustomizationDialog = false },
            onNavigateToCustomization = {
                showCustomizationDialog = false
                navController.navigate(Screen.ChatExperienceCustomizationScreen.route)
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF5F7F9)
                    )
                )
            )
            .navigationBarsPadding()
            .imePadding()
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text("Harmonia")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Geri"
                        )
                    }
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        // Settings icon button
                        IconButton(
                            onClick = { showCustomizationDialog = true },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Harmonia'yı Özelleştir",
                                tint = harmonyHavenDarkGreenColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        
                        // Save current chat button (only show if there are messages)
                        if (state.value.messages.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    // In a real app, this would save the current chat to history
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Sohbeti Kaydet",
                                    tint = harmonyHavenDarkGreenColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Chat history icon button
                        IconButton(
                            onClick = {
                                // Navigate to ChatHistoryScreen
                                navController.navigate(Screen.ChatHistoryScreen.route)
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Sohbet Geçmişi",
                                tint = harmonyHavenDarkGreenColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            // Input field - WindowInsets'i doğru şekilde yapılandırıyoruz
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding() // Navigasyon çubuğu padding'i burada
            ) {
                InputField(
                    text = text,
                    onTextValueChanged = { text = it },
                    onFocusChange = { focused ->
                        isKeyboardVisible = focused
                        if (focused && state.value.messages.isNotEmpty()) {
                            scope.launch {
                                listState.animateScrollToItem(state.value.messages.size - 1)
                            }
                        }
                    },
                    onSend = {
                        if (text.isNotBlank()) {
                            val currentText = text // Geçici bir değişkende sakla
                            text = "" // UI'ı hemen güncelle
                            
                            // Mesajı gönder
                            scope.launch {
                                viewModel.sendMessage(message = currentText)
                                
                                // Mesaj listesinin boyutu değiştiğinde scroll
                                delay(150) // Küçük bir gecikme ekleyerek mesajın listeye eklenmesini bekle
                                val newSize = state.value.messages.size
                                if (newSize > 0) {
                                    listState.animateScrollToItem(newSize - 1)
                                }
                            }
                        }
                    },
                    isLoading = state.value.isLoading
                )
            }
        },
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->
        // Messages
        LazyColumn(
            modifier = Modifier
                .background( Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF5F7F9)
                    )
                ))
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            if (state.value.messages.isEmpty() && state.value.currentMessage.isEmpty()) {
                item {
                    WelcomeMessage(
                        onExampleClick = { exampleMessage ->
                            viewModel.sendMessage(exampleMessage)
                        }
                    )
                }
            }

            itemsIndexed(state.value.messages) { index, message ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically { it },
                ) {
                    when (message) {
                        is ChatMessage.User -> UserMessageBox(message = message.text)
                        is ChatMessage.Bot -> BotMessageBox(message = message.text)
                    }
                }
            }

            // Show current partial message if it exists
            if (state.value.currentMessage.isNotEmpty()) {
                item {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically { it },
                    ) {
                        BotMessageBox(message = state.value.currentMessage)
                    }
                }
            } else if (state.value.isLoading) {
                item {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically { it },
                    ) {
                        TypingIndicator()
                    }
                }
            }


        }


    }

}

@Composable
fun WelcomeMessage(onExampleClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF5F7F9)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Harmonia icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(harmonyHavenGreen.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.send_icon),
                contentDescription = "Welcome",
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Merhaba! Size nasıl yardımcı olabilirim?",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = harmonyHavenDarkGreenColor,
                fontFamily = ptSansFont
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Herhangi bir konuda soru sorabilirsiniz",
            style = TextStyle(
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontFamily = ptSansFont
            )
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Örnek Sorular:",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = harmonyHavenDarkGreenColor,
                fontFamily = ptSansFont
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Example messages that user can click on
        ExampleMessageChip("Bugün kendimi kötü hissediyorum.", onExampleClick)
        ExampleMessageChip("Motivasyonumu artırmak için ne yapabilirim?", onExampleClick)
        ExampleMessageChip("Stresle nasıl başa çıkabilirim?", onExampleClick)
        ExampleMessageChip(
            "Günlük rutinime ekleyebileceğim faydalı alışkanlıklar nelerdir?",
            onExampleClick
        )
    }
}

@Composable
fun ExampleMessageChip(message: String, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(message) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.7f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null,
                tint = harmonyHavenGreen,
                modifier = Modifier.size(16.dp)
            )
            
            Spacer(modifier = Modifier.width(10.dp))
            
            Text(
                text = message,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = harmonyHavenDarkGreenColor,
                    fontFamily = ptSansFont
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InputField(
    text: String,
    onTextValueChanged: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onSend: (String) -> Unit,
    isLoading: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val sendButtonScale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(text.isNotBlank()) {
        if (text.isNotBlank()) {
            sendButtonScale.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(100)
            )
            sendButtonScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(100)
            )
        }
    }

    // InputField için SafeArea oluşturuyoruz
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding(), // Ek ime padding burada da sağlıyoruz
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .onFocusChanged { focusState ->
                                onFocusChange(focusState.isFocused)
                            },
                        value = text,
                        onValueChange = onTextValueChanged,
                        placeholder = {
                            Text(
                                text = "Mesajınızı buraya girin",
                                color = Color.Gray,
                                fontFamily = ptSansFont,
                                fontSize = 15.sp
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = harmonyHavenGreen,
                            selectionColors = TextSelectionColors(
                                handleColor = harmonyHavenGreen,
                                backgroundColor = harmonyHavenGreen.copy(alpha = 0.2f)
                            )
                        ),
                        textStyle = TextStyle(
                            color = Color.DarkGray,
                            fontSize = 15.sp,
                            fontFamily = ptSansFont
                        ),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                if (text.isNotBlank() && !isLoading) harmonyHavenGreen
                                else Color.Gray.copy(alpha = 0.5f)
                            )
                            .clickable(enabled = text.isNotBlank() && !isLoading) {
                                scope.launch {
                                    // Önce mesajı gönder
                                    onSend(text)
                                    
                                    // Küçük bir gecikmeyle klavyeyi kapat
                                    delay(100)
                                    keyboardController?.hide()
                                }
                            }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(-45f)
                                .scale(sendButtonScale.value)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BotMessageBox(message: String, modifier: Modifier = Modifier) {
    // Markdown metni için satır düzenlemelerini koru
    val processedMessage = message

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 64.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
        ) {
            val customStyle = TextStyle(
                fontSize = 15.sp,
                color = Color.DarkGray,
                lineHeight = 22.sp,
                fontFamily = ptSansFont
            )

            // Markdown text renderlama
            MarkdownText(
                markdown = processedMessage,
                modifier = Modifier.padding(12.dp),
                style = customStyle
            )
        }
    }
}

@Composable
fun UserMessageBox(message: String = "", modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 64.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 4.dp,
                bottomEnd = 12.dp,
                bottomStart = 12.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = harmonyHavenGreen
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
        ) {
            Text(
                text = message,
                color = Color.White,
                modifier = Modifier.padding(12.dp),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = ptSansFont,
                    lineHeight = 22.sp
                )
            )
        }
    }
}

@Composable
fun TypingIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 64.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BouncingDots()
            }
        }
    }
}

@Composable
fun BouncingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "bouncingDots")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(3) { index ->
            val delay = index * 150

            val offsetY by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0f at 0 + delay
                        -10f at 300 + delay
                        0f at 600 + delay
                        0f at 1200 + delay
                    },
                    repeatMode = RepeatMode.Restart
                ),
                label = "dot$index"
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(8.dp)
                    .offset(y = offsetY.dp)
                    .background(
                        color = harmonyHavenGreen.copy(alpha = 0.7f),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun CustomizationDialog(
    onDismiss: () -> Unit,
    onNavigateToCustomization: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with icon
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(harmonyHavenGreen.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Customize",
                        tint = harmonyHavenGreen,
                        modifier = Modifier.size(30.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Title
                Text(
                    text = "Harmonia'yı Özelleştir",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = harmonyHavenDarkGreenColor,
                        textAlign = TextAlign.Center,
                        fontFamily = ptSansFont
                    )
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Description
                Text(
                    text = "Kişiselleştirme size daha özel bir deneyim sunar. Birkaç basit soru yanıtlayarak Harmonia'nın size daha iyi hizmet etmesini sağlayabilirsiniz.",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        fontFamily = ptSansFont,
                        lineHeight = 22.sp
                    )
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Benefits list
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    CustomizationBenefitItem("Kişiselleştirilmiş tavsiyeler")
                    CustomizationBenefitItem("Size özgü yanıtlar")
                    CustomizationBenefitItem("Daha uyumlu bir sohbet deneyimi")
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, harmonyHavenGreen),
                        contentPadding = PaddingValues(horizontal = 10.dp)
                    ) {
                        Text(
                            text = "Daha Sonra",
                            color = harmonyHavenGreen,
                            fontFamily = ptSansFont,
                            fontSize = 14.sp,
                            maxLines = 1
                        )
                    }
                    
                    Button(
                        onClick = onNavigateToCustomization,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = harmonyHavenGreen
                        )
                    ) {
                        Text(
                            text = "Özelleştir",
                            color = Color.White,
                            fontFamily = ptSansFont,
                            fontSize = 14.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomizationBenefitItem(text: String) {
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
                .background(harmonyHavenGreen)
        )
        
        Spacer(modifier = Modifier.width(10.dp))
        
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.DarkGray,
            fontFamily = ptSansFont
        )
    }
}