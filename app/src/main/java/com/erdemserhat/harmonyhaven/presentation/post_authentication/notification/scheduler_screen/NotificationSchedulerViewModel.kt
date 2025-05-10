package com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.scheduler_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationApiService
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationSchedulerDto
import com.erdemserhat.harmonyhaven.domain.usecase.notification.NotificationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

// Class to track deletion state for each item
data class DeletionState(
    val isDeleting: Boolean = false,
    val isSuccess: Boolean? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class NotificationSchedulerViewModel @Inject constructor(
    private val notificationUseCases: NotificationUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(SchedulerState())
    val state: StateFlow<SchedulerState> = _state

    // Track deletion states for individual items
    private val _deletionStates = MutableStateFlow<Map<String, DeletionState>>(emptyMap())
    val deletionStates: StateFlow<Map<String, DeletionState>> = _deletionStates

    // Keep track of temporary schedulers
    private val pendingSchedulers = mutableMapOf<String, NotificationSchedulerDto>()

    fun scheduleNotification(scheduler: NotificationSchedulerDto) {
        viewModelScope.launch {
            try {
                // Create a temporary ID to track this scheduler
                val tempId = UUID.randomUUID().toString()
                
                // Create a temporary copy with the temp ID
                val tempScheduler = scheduler.copy(id = tempId)
                
                // Add to pending map
                pendingSchedulers[tempId] = tempScheduler
                
                // Add to UI immediately with the temporary ID
                val currentList = _state.value.notificationScheduler.toMutableList()
                currentList.add(tempScheduler)
                
                _state.update { it.copy(
                    isSchedulingNotificationInProgress = true,
                    notificationScheduler = currentList
                )}
                
                // Make the actual API request
                notificationUseCases.scheduleNotification.executeRequest(scheduler)
                
                // Get updated scheduler list from server
                val newSchedulers = notificationUseCases.getSchedulers.executeRequest()
                
                // Update state with server data
                _state.update { it.copy(
                    isSchedulingNotificationInProgress = false,
                    notificationScheduler = newSchedulers
                )}
                
                // Clear from pending
                pendingSchedulers.remove(tempId)
                
            } catch (e: Exception) {
                Log.e("NotificationScheduler", "Error scheduling notification: ${e.message}")
                // Keep the UI showing error state
                _state.update { it.copy(
                    isSchedulingNotificationInProgress = false
                )}
            }
        }
    }


    fun deleteScheduler(schedulerId: String) {
        viewModelScope.launch {
            try {
                // Update global deletion state
                _state.update { it.copy(isDeletingSchedulerInProgress = true) }
                
                // Set this specific item's deletion state to in-progress
                updateDeletionState(schedulerId, DeletionState(isDeleting = true))


                
                // Make the actual API request
                val result = notificationUseCases.deleteScheduler.executeRequest(schedulerId)
                
                if (result) {
                    // If successful, update the deletion state
                    updateDeletionState(schedulerId, DeletionState(isDeleting = false, isSuccess = true))
                    
                    // Fetch the latest data after deletion
                    val newSchedulers = notificationUseCases.getSchedulers.executeRequest()
                    
                    _state.update { it.copy(
                        isDeletingSchedulerInProgress = false,
                        notificationScheduler = newSchedulers
                    )}
                } else {
                    // API returned false, update deletion state to failed
                    updateDeletionState(
                        schedulerId, 
                        DeletionState(isDeleting = false, isSuccess = false, errorMessage = "İşlem başarısız oldu")
                    )
                    
                    // Revert the UI by getting fresh data
                    val updatedSchedulers = notificationUseCases.getSchedulers.executeRequest()
                    _state.update { it.copy(
                        isDeletingSchedulerInProgress = false,
                        notificationScheduler = updatedSchedulers
                    )}
                }
            } catch (e: Exception) {
                Log.d("scheduler", e.message.toString())
                
                // Update deletion state to failed with error message
                updateDeletionState(
                    schedulerId, 
                    DeletionState(isDeleting = false, isSuccess = false, errorMessage = e.message)
                )
                
                // On error, revert to fetching the full list again
                try {
                    val updatedSchedulers = notificationUseCases.getSchedulers.executeRequest()
                    _state.update { it.copy(
                        isDeletingSchedulerInProgress = false,
                        notificationScheduler = updatedSchedulers
                    )}
                } catch (e: Exception) {
                    _state.update { it.copy(isDeletingSchedulerInProgress = false) }
                }
            }
        }
    }

    fun getSchedulers() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoadingSchedulers = true) }
                val schedulers = notificationUseCases.getSchedulers.executeRequest()
                _state.update { it.copy(
                    isLoadingSchedulers = false,
                    notificationScheduler = schedulers
                )}
            } catch (e: Exception) {
                Log.d("scheduler", e.message.toString())
                _state.update { it.copy(isLoadingSchedulers = false) }
            }
        }
    }
    
    // Updates the deletion state for a specific scheduler ID
    private fun updateDeletionState(schedulerId: String, state: DeletionState) {
        val currentStates = _deletionStates.value.toMutableMap()
        currentStates[schedulerId] = state
        _deletionStates.value = currentStates
    }
    
    // Helper method to get deletion state for a specific item
    fun getDeletionState(schedulerId: String?): DeletionState {
        return if (schedulerId != null) {
            _deletionStates.value[schedulerId] ?: DeletionState()
        } else {
            DeletionState()
        }
    }
    
    // Helper method to check if a scheduler is pending
    fun isSchedulerPending(id: String): Boolean {
        return pendingSchedulers.containsKey(id)
    }
    
    // Clear deletion state for an item
    fun clearDeletionState(schedulerId: String) {
        val currentStates = _deletionStates.value.toMutableMap()
        currentStates.remove(schedulerId)
        _deletionStates.value = currentStates
    }
}