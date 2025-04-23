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

            }catch (e:Exception){
                Log.d("ENNEAGRAM_USECASE",e.message?:"Error message was null")

            }


        }
    }

}