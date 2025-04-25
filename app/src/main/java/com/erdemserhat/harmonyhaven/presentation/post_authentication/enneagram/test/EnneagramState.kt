package com.erdemserhat.harmonyhaven.presentation.post_authentication.enneagram.test

import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramAnswersDto
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramQuestionDto
import com.erdemserhat.harmonyhaven.data.api.enneagram.EnneagramTestResultDetailedDto

data class EnneagramState(
    val errorMessage:String="",
    val isLoadingQuestions:Boolean = false,
    val questions:List<EnneagramQuestionDto> = listOf(),
    val answers:MutableList<EnneagramAnswersDto> = mutableListOf(),
    val isSubmittingAnswers: Boolean = false,
    val isTestCompleted: Boolean = false,
    val showInstructions: Boolean = true,
    val isTestStarted: Boolean = false,
    val currentQuestionIndex: Int = 0,
    val result:EnneagramTestResultDetailedDto? = null
)
