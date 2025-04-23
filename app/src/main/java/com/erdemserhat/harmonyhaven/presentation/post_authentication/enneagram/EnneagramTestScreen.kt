package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramQuestionDto
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenComponentWhite
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenDarkGreenColor
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenGreen

@Composable
fun EnneagramTestScreen(navController: NavController, enneagramViewModel: EnneagramViewModel = hiltViewModel()) {
    val enneagramState by enneagramViewModel.enneagramState
    val context = LocalContext.current

    Box(modifier = Modifier
        .background(harmonyHavenComponentWhite)
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
                        enneagramViewModel.submitAnswers()
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
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Information",
            tint = harmonyHavenGreen,
            modifier = Modifier.size(56.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Enneagram Kişilik Testi",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor
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
                .height(50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Teste Başla")
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
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Check",
                tint = harmonyHavenGreen,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun TestQuestionsScreen(
    questions: List<EnneagramQuestionDto>,
    currentIndex: Int,
    answers: List<com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramAnswersDto>,
    onAnswerSelected: (Int, Int) -> Unit,
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
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = question.content,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Açıklayıcı metin
                    Text(
                        text = "Yukarıdaki ifade sizi ne kadar yansıtıyor?",
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Answer options
                    val selectedAnswer = answers.find { it.questionId == question.id }?.score
                    
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
        
        // Check if current question is answered
        val currentQuestionId = currentQuestion?.id
        val isCurrentQuestionAnswered = currentQuestionId != null && answers.any { it.questionId == currentQuestionId }
        

        
        // Navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
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
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Önceki")
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
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                if (localCurrentIndex < questions.size - 1) {
                    Text(text = "Sonraki")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next"
                    )
                } else {
                    Text(text = "Tamamla")
                }
            }
        }
        
        // Submit warning text if not all questions are answered
        if (localCurrentIndex == questions.size - 1 && answers.size < questions.size) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tüm soruları cevaplamanız gerekmektedir!",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ProgressBarWithSegments(
    totalQuestions: Int,
    currentIndex: Int,
    answeredQuestions: List<Int>,
    questionsMap: Map<Int, EnneagramQuestionDto>,
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
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Daha büyük ve görünür bir ilerleme çubuğu
        LinearProgressIndicator(
            progress = if (totalQuestions > 0) 
                (currentIndex + 1) / totalQuestions.toFloat() 
            else 0f,
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp)),
            color = harmonyHavenGreen,
            trackColor = Color.LightGray.copy(alpha = 0.5f)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Tamamlanma yüzdesi
        val completionPercentage = if (totalQuestions > 0) {
            ((currentIndex + 1) * 100) / totalQuestions
        } else 0
        
        Text(
            text = "%$completionPercentage tamamlandı",
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
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
            .padding(vertical = 4.dp)
            .selectable(
                selected = isSelected,
                onClick = onSelected
            )
            .background(
                color = if (isSelected) harmonyHavenGreen.copy(alpha = 0.1f) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelected
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) harmonyHavenDarkGreenColor else Color.DarkGray,
            modifier = Modifier.weight(1f)
        )
        
        if (isSelected) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Seçildi",
                tint = harmonyHavenGreen,
                modifier = Modifier.size(20.dp)
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
            modifier = Modifier.size(56.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Lütfen bekleyin...",
            style = MaterialTheme.typography.bodyLarge
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
            style = MaterialTheme.typography.titleLarge,
            color = Color.Red
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(onClick = onRetry) {
            Text("Tekrar Dene")
        }
    }
}

@Composable
fun InitialScreen(onStartTest: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enneagram Kişilik Testi",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Kişiliğinizi daha iyi anlamak için bu testi tamamlayın",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onStartTest,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Teste Başla")
        }
    }
}

@Composable
fun TestCompletedScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Completed",
            tint = harmonyHavenGreen,
            modifier = Modifier.size(80.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Test Tamamlandı!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = harmonyHavenDarkGreenColor
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Yanıtlarınız başarıyla kaydedildi. Artık Harmonia sizi daha iyi tanıyor!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Ana Sayfaya Dön")
        }
    }
}