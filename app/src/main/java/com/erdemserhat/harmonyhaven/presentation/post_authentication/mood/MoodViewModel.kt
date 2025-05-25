package com.erdemserhat.harmonyhaven.presentation.post_authentication.mood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.domain.model.rest.Mood
import com.erdemserhat.harmonyhaven.domain.usecase.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoodViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoodUiState())
    val uiState: StateFlow<MoodUiState> = _uiState.asStateFlow()

    init {
        loadMoods()
        loadCurrentUserMood()
    }

    fun loadMoods() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val moods = userUseCases.getAllMoods.executeRequest()
                _uiState.value = _uiState.value.copy(
                    moods = moods,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun loadCurrentUserMood() {
        viewModelScope.launch {
            try {
                val moodResponse = userUseCases.getUserMood.executeRequest()
                val currentMoodId = moodResponse?.get("moodId")
                _uiState.value = _uiState.value.copy(
                    currentMoodId = currentMoodId
                )
            } catch (e: Exception) {
                // Handle error silently for current mood loading
            }
        }
    }

    fun updateUserMood(moodId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isUpdating = true)
            try {
                val success = userUseCases.updateUserMood.executeRequest(moodId)
                if (success) {
                    _uiState.value = _uiState.value.copy(
                        currentMoodId = moodId,
                        isUpdating = false,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isUpdating = false,
                        error = "Failed to update mood"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isUpdating = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class MoodUiState(
    val moods: List<Mood> = emptyList(),
    val currentMoodId: String? = null,
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false,
    val error: String? = null
) 