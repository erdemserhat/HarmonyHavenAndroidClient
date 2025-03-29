package com.erdemserhat.harmonyhaven.presentation.post_authentication.chat

import android.os.VibrationEffect
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
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
    
    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(state.value.messages.size) {
        if (state.value.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.value.messages.size - 1)
        }
    }

    // Auto-scroll to bottom when keyboard appears
    LaunchedEffect(isKeyboardVisible) {
        if (isKeyboardVisible && state.value.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.value.messages.size - 1)
        }
    }

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
        // Custom TopBar with back button
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent),
            title = {
                Text("Harmonia")
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp()   }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Geri"
                    )
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            if (state.value.messages.isEmpty()) {
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
            
            if (state.value.isLoading) {
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
                Spacer(modifier = Modifier.height(8.dp))
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

@Composable
fun WelcomeMessage(onExampleClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.send_icon),
            contentDescription = "Welcome",
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 16.dp)
        )
        
        Text(
            text = "Merhaba! Size nasıl yardımcı olabilirim?",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.DarkGray
            )
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Herhangi bir konuda soru sorabilirsiniz",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Örnek Sorular:",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = harmonyHavenGreen.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null,
                tint = harmonyHavenGreen,
                modifier = Modifier.size(18.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = message,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.DarkGray
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
            .padding(horizontal = 16.dp , vertical = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp)
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
                        .clip(RoundedCornerShape(24.dp))
                        .onFocusChanged { focusState ->
                            onFocusChange(focusState.isFocused)
                        },
                    value = text,
                    onValueChange = onTextValueChanged,
                    placeholder = {
                        Text(
                            text = "Mesajınızı buraya girin",
                            color = Color.Gray
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
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            if (text.isNotBlank() && !isLoading) harmonyHavenGreen
                            else Color.Gray.copy(alpha = 0.5f)
                        )
                        .clickable(enabled = text.isNotBlank() && !isLoading) {
                            onSend(text)
                        }
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
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
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 64.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 16.dp,
                bottomEnd = 16.dp,
                bottomStart = 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            val customStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black,
            )

            MarkdownText(
                markdown = message,
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
            .padding(start = 64.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 4.dp,
                bottomEnd = 16.dp,
                bottomStart = 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = harmonyHavenGreen
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Text(
                text = message,
                color = Color.White,
                modifier = Modifier.padding(12.dp),
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

@Composable
fun TypingIndicator() {
    Row(
        modifier = Modifier
            .padding(end = 64.dp)
            .padding(start = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
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