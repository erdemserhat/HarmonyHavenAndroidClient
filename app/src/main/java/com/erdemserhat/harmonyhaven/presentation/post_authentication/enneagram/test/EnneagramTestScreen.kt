package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.test

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramQuestionDto
import com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.profil.UserProfileScreenViewModel
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen
import com.erdemserhat.harmonyhaven.ui.theme.ptSansFont

@Composable
fun EnneagramTestScreen(
    navController: NavController,
    enneagramViewModel: EnneagramViewModel = hiltViewModel(),
    sharedViewModel: UserProfileScreenViewModel
) {
    val enneagramState by enneagramViewModel.enneagramState
    val context = LocalContext.current

    Box(modifier = Modifier
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White,
                    Color(0xFFF5F7F9)
                )
            )
        )
        .fillMaxSize()
        .padding(16.dp)
    ) {
        when {
            // Show test completion
            enneagramState.isTestCompleted -> {
                TestCompletedScreen(navController)
            }
            
            // Show loading indicator
            enneagramState.isLoadingQuestions || enneagramState.isSubmittingAnswers -> {
                LoadingScreen()
            }
            
            // Show instructions before starting the test
            enneagramState.showInstructions -> {
                TestInstructionsScreen(onStartTest = { enneagramViewModel.startTest() })
            }
            
            // Show the test questions
            enneagramState.isTestStarted && enneagramState.questions.isNotEmpty() -> {
                TestQuestionsScreen(
                    questions = enneagramState.questions,
                    currentIndex = enneagramState.currentQuestionIndex,
                    answers = enneagramState.answers,
                    onAnswerSelected = { questionId, score ->
                        enneagramViewModel.updateAnswer(questionId, score)
                    },
                    onSubmitAnswers = {
                        enneagramViewModel.submitAnswers(
                            onCompleted = {
                                sharedViewModel.checkTestResult(onCompleted = {
                                    sharedViewModel.resetScrollState()
                                })
                            }
                        )
                    }
                )
            }
            
            // Error state
            enneagramState.errorMessage.isNotEmpty() -> {
                ErrorScreen(errorMessage = enneagramState.errorMessage) {
                    enneagramViewModel.getQuestions()
                }
            }
            
            // Initial state - show button to get questions
            else -> {
                InitialScreen {
                    enneagramViewModel.showInstructions()
                }
            }
        }
    }
}

@Composable
fun TestInstructionsScreen(onStartTest: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = harmonyHavenGreen.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Information",
                tint = harmonyHavenGreen,
                modifier = Modifier.size(40.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Enneagram Kişilik Testi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            fontFamily = ptSansFont
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        InstructionItem(text = "Bu test ortalama 10-15 dakika sürecektir.")
        InstructionItem(text = "Testteki soruları olmasını istediğiniz gibi değil, olduğunuz gibi cevaplamanız gerekmektedir.")
        InstructionItem(text = "Test boyunca istediğiniz cevabı geri dönüp değiştirebilirsiniz.")
        InstructionItem(text = "Mizaç tesbitinizin isabetli olabilmesi vereceğiniz cevapların doğruluğuna bağlıdır.")
        InstructionItem(text = "Testi tamamlamanız durumunda Harmonia sizi daha iyi tanıyacak ve size özel olarak sizinle sohbet edecektir.")
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onStartTest,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = harmonyHavenGreen,
                contentColor = Color.White
            )
        ) {
            Text(
                "Teste Başla", 
                fontSize = 16.sp,
                fontFamily = ptSansFont,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun InstructionItem(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = harmonyHavenGreen.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check",
                    tint = harmonyHavenGreen,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = text,
                fontSize = 15.sp,
                color = Color.DarkGray,
                fontFamily = ptSansFont
            )
        }
    }
}

@Composable
fun TestQuestionsScreen(
    questions: List<EnneagramQuestionDto>,
    currentIndex: Int,
    answers: List<com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramAnswersDto>,
    onAnswerSelected: (String, Int) -> Unit,
    onSubmitAnswers: () -> Unit
) {
    val viewModel = hiltViewModel<EnneagramViewModel>()
    var localCurrentIndex by remember { mutableStateOf(currentIndex) }
    val currentQuestion = questions.getOrNull(localCurrentIndex)
    
    // Update the state when current index changes
    LaunchedEffect(localCurrentIndex) {
        viewModel.updateCurrentQuestionIndex(localCurrentIndex)
    }
    
    // Map of question indices to IDs
    val questionIndices = remember(questions) {
        questions.mapIndexed { index, question -> 
            index to question.id 
        }.toMap()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header with progress
        Column {
            Spacer(modifier = Modifier.height(8.dp))

            // Enhanced Progress Bar
            ProgressBarWithSegments(
                totalQuestions = questions.size,
                currentIndex = localCurrentIndex,
                answeredQuestions = answers.map { it.questionId },
                questionsMap = questions.associate { it.id to it },
                onQuestionSelected = { index ->
                    // Check if the current question is answered before allowing navigation
                    val currentQuestionId = questions.getOrNull(localCurrentIndex)?.id
                    val isCurrentQuestionAnswered = answers.any { it.questionId == currentQuestionId }

                    // If current question is answered, allow navigation, else show a toast
                    if (isCurrentQuestionAnswered || index < localCurrentIndex) {
                        localCurrentIndex = index
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
        
        // Question content
        currentQuestion?.let { question ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Soru metnini "Örneğin" kelimesinden sonrası için farklı stil uygula
                    val styledQuestionText = remember(question.content) {
                        buildAnnotatedString {
                            val content = question.content
                            val orneginIndex = content.indexOf("Örneğin", ignoreCase = true)
                            
                            if (orneginIndex != -1) {
                                // "Örneğin" kelimesinden önceki kısım
                                append(content.substring(0, orneginIndex))
                                
                                // "Örneğin" kelimesinden sonraki kısım - daha küçük ve şeffaf
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 14.sp,
                                        color = Color.Black.copy(alpha = 0.6f),
                                        fontWeight = FontWeight.Normal
                                    )
                                ) {
                                    append(content.substring(orneginIndex))
                                }
                            } else {
                                // "Örneğin" kelimesi yoksa normal stil
                                append(content)
                            }
                        }
                    }
                    
                    Text(
                        text = styledQuestionText,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = ptSansFont,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        lineHeight = 24.sp
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Açıklayıcı metin
                    Text(
                        text = "Yukarıdaki ifade sizi ne kadar yansıtıyor?",
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = ptSansFont
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Answer options
                    val selectedAnswer = answers.find { it.questionId == question.id }?.score
                    
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        AnswerOption(
                            text = "Hiç yansıtmıyor",
                            score = 0,
                            isSelected = selectedAnswer == 0,
                            onSelected = { onAnswerSelected(question.id, 0) }
                        )
                        
                        AnswerOption(
                            text = "Biraz yansıtıyor",
                            score = 1,
                            isSelected = selectedAnswer == 1,
                            onSelected = { onAnswerSelected(question.id, 1) }
                        )
                        
                        AnswerOption(
                            text = "Kısmen yansıtıyor",
                            score = 2,
                            isSelected = selectedAnswer == 2,
                            onSelected = { onAnswerSelected(question.id, 2) }
                        )
                        
                        AnswerOption(
                            text = "Tam olarak yansıtıyor ",
                            score = 3,
                            isSelected = selectedAnswer == 3,
                            onSelected = { onAnswerSelected(question.id, 3) }
                        )
                    }
                }
            }
        }
        
        // Check if current question is answered
        val currentQuestionId = currentQuestion?.id
        val isCurrentQuestionAnswered = currentQuestionId != null && answers.any { it.questionId == currentQuestionId }
        
        // Navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Back button
            OutlinedButton(
                onClick = {
                    if (localCurrentIndex > 0) {
                        localCurrentIndex--
                    }
                },
                enabled = localCurrentIndex > 0,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(52.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = harmonyHavenGreen
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Önceki",
                    fontFamily = ptSansFont,
                    fontWeight = FontWeight.Medium
                )
            }
            
            // Next/Submit button
            Button(
                onClick = {
                    if (localCurrentIndex < questions.size - 1 && isCurrentQuestionAnswered) {
                        localCurrentIndex++
                    } else if (localCurrentIndex == questions.size - 1 && answers.size == questions.size) {
                        // If all questions are answered, submit
                        onSubmitAnswers()
                    }
                },
                enabled = isCurrentQuestionAnswered,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = harmonyHavenGreen,
                    contentColor = Color.White,
                    disabledContainerColor = harmonyHavenGreen.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (localCurrentIndex < questions.size - 1) {
                    Text(
                        text = "Sonraki",
                        fontFamily = ptSansFont,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next"
                    )
                } else {
                    Text(
                        text = "Tamamla",
                        fontFamily = ptSansFont,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        
        // Submit warning text if not all questions are answered
        if (localCurrentIndex == questions.size - 1 && answers.size < questions.size) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tüm soruları cevaplamanız gerekmektedir!",
                color = Color.Red,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = ptSansFont
            )
        }
    }
}

@Composable
fun ProgressBarWithSegments(
    totalQuestions: Int,
    currentIndex: Int,
    answeredQuestions: List<String>,
    questionsMap: Map<String, EnneagramQuestionDto>,
    onQuestionSelected: (Int) -> Unit
) {
    // Debug log'ları
    Log.d("ProgressBar", "Toplam soru: $totalQuestions")
    Log.d("ProgressBar", "Mevcut indeks: $currentIndex")
    Log.d("ProgressBar", "Cevaplanan sorular: $answeredQuestions")
    
    Column(modifier = Modifier.fillMaxWidth()) {
        // Soru numarası gösterimi
        Text(
            text = "Soru ${currentIndex + 1}/$totalQuestions",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = ptSansFont
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Daha büyük ve görünür bir ilerleme çubuğu
        LinearProgressIndicator(
            progress = if (totalQuestions > 0) 
                (currentIndex + 1) / totalQuestions.toFloat() 
            else 0f,
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = harmonyHavenGreen,
            trackColor = Color.LightGray.copy(alpha = 0.3f)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Tamamlanma yüzdesi
        val completionPercentage = if (totalQuestions > 0) {
            ((currentIndex + 1) * 100) / totalQuestions
        } else 0
        
        Text(
            text = "%$completionPercentage tamamlandı",
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = ptSansFont
        )
    }
}

@Composable
fun AnswerOption(
    text: String,
    score: Int,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .selectable(
                selected = isSelected,
                onClick = onSelected
            )
            .background(
                color = if (isSelected) harmonyHavenGreen.copy(alpha = 0.1f) else Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = harmonyHavenGreen,
                unselectedColor = Color.Gray
            ),
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = text,
            fontSize = 14.sp,
            color = if (isSelected) harmonyHavenDarkGreenColor else Color.DarkGray,
            modifier = Modifier.weight(1f),
            fontFamily = ptSansFont,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
        
        if (isSelected) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Seçildi",
                tint = harmonyHavenGreen,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = harmonyHavenGreen,
            modifier = Modifier.size(56.dp),
            strokeWidth = 4.dp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Lütfen bekleyin...",
            fontSize = 18.sp,
            fontFamily = ptSansFont,
            color = harmonyHavenDarkGreenColor
        )
    }
}

@Composable
fun ErrorScreen(errorMessage: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hata Oluştu",
            fontSize = 22.sp,
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontFamily = ptSansFont
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = errorMessage,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontFamily = ptSansFont,
            color = Color.DarkGray
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onRetry,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = harmonyHavenGreen
            )
        ) {
            Text(
                "Tekrar Dene",
                fontFamily = ptSansFont,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun InitialScreen(onStartTest: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enneagram Kişilik Testi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            textAlign = TextAlign.Center,
            fontFamily = ptSansFont
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Kişiliğinizi daha iyi anlamak için bu testi tamamlayın",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
            fontFamily = ptSansFont,
            color = Color.DarkGray
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Button(
            onClick = onStartTest,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = harmonyHavenGreen,
                contentColor = Color.White
            )
        ) {
            Text(
                "Teste Başla",
                fontSize = 16.sp,
                fontFamily = ptSansFont,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun TestCompletedScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = harmonyHavenGreen.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Completed",
                tint = harmonyHavenGreen,
                modifier = Modifier.size(60.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Test Tamamlandı!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            fontFamily = ptSansFont
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Yanıtlarınız başarıyla kaydedildi. Artık Harmonia sizi daha iyi tanıyor!",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontFamily = ptSansFont,
            color = Color.DarkGray
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = harmonyHavenGreen,
                contentColor = Color.White
            )
        ) {
            Text(
                "Ana Sayfaya Dön",
                fontSize = 16.sp,
                fontFamily = ptSansFont,
                fontWeight = FontWeight.Medium
            )
        }
    }
}