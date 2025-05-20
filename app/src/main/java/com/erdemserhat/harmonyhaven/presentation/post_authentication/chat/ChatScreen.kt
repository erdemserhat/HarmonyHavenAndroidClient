package com.erdemserhat.harmonyhaven.presentation.post_authentication.chat

import android.os.VibrationEffect
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.R
import com.erdemserhat.harmonyhaven.markdowntext.MarkdownText
import com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.generic_card.bottom_sheets.comment.ClickableImage
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel(), navController: NavController) {
    val state = viewModel.chatState.collectAsState()
    var text by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isKeyboardVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var showChatHistory by remember { mutableStateOf(false) }
    
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

    when {
        showChatHistory -> {
            ChatHistoryScreen(
                onBackClick = { showChatHistory = false },
                viewModel = viewModel
            )
        }
        else -> {
            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFF8F9FA),
                                Color(0xFFE9ECEF)
                            )
                        )
                    )
                    .fillMaxSize()
                    .imePadding()
                    .systemBarsPadding()
                    .statusBarsPadding()
            ) {
                // Custom TopBar with back button and chat history button
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent),
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
                                onClick = { showChatHistory = true },
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

                // Messages
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp, bottom = 80.dp)
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
                    
                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                // Input field
                InputField(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .zIndex(1f),
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
                            viewModel.sendMessage(message = text)
                            text = ""
                            scope.launch {
                                listState.animateScrollToItem(state.value.messages.size)
                            }
                        }
                    },
                    isLoading = state.value.isLoading
                )
            }
        }
    }
}

@Composable
fun WelcomeMessage(onExampleClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
        ExampleMessageChip("Günlük rutinime ekleyebileceğim faydalı alışkanlıklar nelerdir?", onExampleClick)
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
            containerColor = harmonyHavenGreen.copy(alpha = 0.1f)
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

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    text: String,
    onTextValueChanged: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onSend: (String) -> Unit,
    isLoading: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val sendButtonScale = remember { Animatable(1f) }
    
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

    Box(
        modifier = modifier
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
                            onSend(text)
                            keyboardController?.hide()
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
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 12.dp,
                bottomEnd = 12.dp,
                bottomStart = 12.dp
            ),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryScreen(onBackClick: () -> Unit, viewModel: ChatViewModel = hiltViewModel()) {
    // Search text state
    var searchText by remember { mutableStateOf("") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF5F7F9)
                    )
                )
            )
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Top bar with back button and title
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onBackClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = harmonyHavenDarkGreenColor
                    )
                }
                
                Text(
                    text = "Sohbet Geçmişi",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = harmonyHavenDarkGreenColor,
                    fontFamily = ptSansFont,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Search bar and New Chat button in same row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Search bar
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .weight(1f),
                    placeholder = { Text("Sohbet geçmişinde ara...") },
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search, 
                            contentDescription = "Ara",
                            tint = Color.Gray
                        )
                    },
                    singleLine = true
                )
                
                // New Chat button (smaller)
                IconButton(
                    onClick = { 
                        // Reset chat state to start a new conversation
                        viewModel.resetChat()
                        // Close chat history
                        onBackClick() 
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Yeni Sohbet",
                        tint = harmonyHavenGreen,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            // Section divider
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.LightGray.copy(alpha = 0.5f),
                thickness = 1.dp
            )
            
            // Chat history list with grouping by date
            LazyColumn {
                items(20) { index ->
                    val isToday = index < 3
                    val isYesterday = index in 3..5
                    
                    if (index == 0) {
                        Text(
                            text = "Bugün",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = harmonyHavenDarkGreenColor,
                            fontFamily = ptSansFont,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else if (index == 3) {
                        Text(
                            text = "Dün",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = harmonyHavenDarkGreenColor,
                            fontFamily = ptSansFont,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else if (index == 6) {
                        Text(
                            text = "Geçen Hafta",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = harmonyHavenDarkGreenColor,
                            fontFamily = ptSansFont,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    
                    // Chat history item with more details
                    DetailedChatHistoryItem(
                        title = when {
                            index % 5 == 0 -> "Kişisel Gelişim Tavsiyeleri"
                            index % 4 == 0 -> "Günlük Motivasyon"
                            index % 3 == 0 -> "İlişki Tavsiyeleri"
                            index % 2 == 0 -> "Stres Yönetimi"
                            else -> "Kariyer Gelişimi"
                        },
                        timestamp = when {
                            isToday -> "Bugün, ${14 - index}:${30 + index}"
                            isYesterday -> "Dün, ${20 - index}:${15 + index}"
                            else -> "${index - 4} gün önce"
                        },
                        preview = when {
                            index % 5 == 0 -> "Kendini geliştirmek için yeni bir kitap okumayı deneyebilirsin..."
                            index % 4 == 0 -> "Bugün yapman gereken en önemli şey kendine vakit ayırmak..."
                            index % 3 == 0 -> "İlişkinde iletişim kurma şeklini değiştirmeyi deneyebilirsin..."
                            index % 2 == 0 -> "Stres seviyeni azaltmak için derin nefes alma egzersizleri..."
                            else -> "Kariyer hedeflerine ulaşmak için yapman gereken adımlar..."
                        },
                        messageCount = (index % 5) + 1,
                        onClick = { 
                            // Load sample conversation based on the chat title
                            val chatTitle = when {
                                index % 5 == 0 -> "Kişisel Gelişim Tavsiyeleri"
                                index % 4 == 0 -> "Günlük Motivasyon"
                                index % 3 == 0 -> "İlişki Tavsiyeleri"
                                index % 2 == 0 -> "Stres Yönetimi"
                                else -> "Kariyer Gelişimi"
                            }
                            
                            // Reset current chat and load sample messages
                            viewModel.resetChat()
                            
                            // Load different sample messages based on chat type
                            when (chatTitle) {
                                "Kişisel Gelişim Tavsiyeleri" -> {
                                    viewModel.sendMessage("Kendimi geliştirmek için neler yapabilirim?")
                                }
                                "Günlük Motivasyon" -> {
                                    viewModel.sendMessage("Bugün motivasyonum düşük, bana yardımcı olur musun?")
                                }
                                "İlişki Tavsiyeleri" -> {
                                    viewModel.sendMessage("İlişkimde iletişimi nasıl geliştirebilirim?")
                                }
                                "Stres Yönetimi" -> {
                                    viewModel.sendMessage("Stresle nasıl başa çıkabilirim?")
                                }
                                else -> {
                                    viewModel.sendMessage("Kariyerimde ilerlemek için ne yapmalıyım?")
                                }
                            }
                            
                            // Close history view
                            onBackClick()
                        }
                    )
                    
                    if (index < 19) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = Color.LightGray.copy(alpha = 0.5f),
                            thickness = 0.5.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailedChatHistoryItem(
    title: String,
    timestamp: String,
    preview: String,
    messageCount: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side dot indicator
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(harmonyHavenGreen)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Right side content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Title and time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
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
            
            // Preview text with message count
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = preview,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = ptSansFont,
                    modifier = Modifier.weight(1f)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Message count indicator
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(color = harmonyHavenGreen.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = messageCount.toString(),
                        fontSize = 12.sp,
                        color = harmonyHavenGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}