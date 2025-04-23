package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram

import android.util.Log
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramAnswersDto
import com.erdemserhat.harmonyhaven.domain.usecase.EnneagramUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class EnneagramViewModel @Inject constructor(
    private val enneagramUseCase: EnneagramUseCase
) : ViewModel() {
    private var _enneagramState = mutableStateOf(EnneagramState())
    val enneagramState: State<EnneagramState> = _enneagramState

    fun getQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _enneagramState.value = _enneagramState.value.copy(isLoadingQuestions = true)

                val questions = enneagramUseCase.getQuestions()

                if (questions == null) {
                    _enneagramState.value = _enneagramState.value.copy(
                        isLoadingQuestions = false,
                        errorMessage = "Sorular yüklenirken bir hata oluştu"
                    )
                } else {
                    _enneagramState.value = _enneagramState.value.copy(
                        errorMessage = "",
                        isLoadingQuestions = false,
                        questions = questions
                    )
                }

            } catch (e: Exception) {
                Log.d("ENNEAGRAM_USECASE", e.message ?: "Error message was null")
                _enneagramState.value = _enneagramState.value.copy(
                    isLoadingQuestions = false,
                    errorMessage = e.message ?: "Bir hata oluştu"
                )
            }
        }
    }

    fun updateAnswer(questionId: Int, score: Int) {
        Log.d("EnneagramViewModel", "Updating answer for questionId: $questionId with score: $score")
        val currentAnswers = _enneagramState.value.answers.toMutableList()
        
        // Check if this question already has an answer
        val existingAnswerIndex = currentAnswers.indexOfFirst { it.questionId == questionId }
        
        if (existingAnswerIndex != -1) {
            // Update existing answer
            Log.d("EnneagramViewModel", "Updating existing answer at index: $existingAnswerIndex")
            currentAnswers[existingAnswerIndex] = EnneagramAnswersDto(questionId, score)
        } else {
            // Add new answer
            Log.d("EnneagramViewModel", "Adding new answer")
            currentAnswers.add(EnneagramAnswersDto(questionId, score))
        }
        
        Log.d("EnneagramViewModel", "Current answers: ${currentAnswers.map { "${it.questionId}:${it.score}" }}")
        _enneagramState.value = _enneagramState.value.copy(answers = currentAnswers)
    }

    fun submitAnswers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _enneagramState.value = _enneagramState.value.copy(isSubmittingAnswers = true)
                
                enneagramUseCase.sendAnswers(_enneagramState.value.answers)
                
                _enneagramState.value = _enneagramState.value.copy(
                    isSubmittingAnswers = false,
                    isTestCompleted = true,
                    errorMessage = ""
                )
                
            } catch (e: Exception) {
                Log.d("ENNEAGRAM_USECASE", e.message ?: "Error message was null")
                _enneagramState.value = _enneagramState.value.copy(
                    isSubmittingAnswers = false,
                    errorMessage = e.message ?: "Cevaplar gönderilirken bir hata oluştu"
                )
            }
        }
    }
    
    fun resetTest() {
        _enneagramState.value = EnneagramState()
    }
    
    fun startTest() {
        _enneagramState.value = _enneagramState.value.copy(
            showInstructions = false,
            isTestStarted = true
        )
        getQuestions()
    }
    
    fun showInstructions() {
        _enneagramState.value = _enneagramState.value.copy(showInstructions = true)
    }

    fun updateCurrentQuestionIndex(index: Int) {
        _enneagramState.value = _enneagramState.value.copy(currentQuestionIndex = index)
    }
}