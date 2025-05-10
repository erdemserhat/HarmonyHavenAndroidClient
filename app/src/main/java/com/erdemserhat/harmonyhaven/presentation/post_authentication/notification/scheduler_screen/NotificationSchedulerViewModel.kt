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
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotificationSchedulerViewModel @Inject constructor(
    private val notificationUseCases: NotificationUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(SchedulerState())
    val state: StateFlow<SchedulerState> = _state


    fun scheduleNotification(scheduler: NotificationSchedulerDto) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isSchedulingNotificationInProgress = true)
                notificationUseCases.scheduleNotification.executeRequest(scheduler)
                val newSchedulers = notificationUseCases.getSchedulers.executeRequest()
                _state.value = _state.value.copy(
                    isSchedulingNotificationInProgress = false,
                    notificationScheduler = newSchedulers

                )
            }catch (e:Exception){

            }


        }

    }


    fun deleteScheduler(schedulerId: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isDeletingSchedulerInProgress = true)
                val result = notificationUseCases.deleteScheduler.executeRequest(schedulerId)
                val newSchedulers = notificationUseCases.getSchedulers.executeRequest()
                _state.value = _state.value.copy(
                    isDeletingSchedulerInProgress = false,
                    notificationScheduler = newSchedulers

                )

            } catch (e: Exception) {
                Log.d("scheduler", e.message.toString())

            }

        }

    }

    fun getSchedulers() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoadingSchedulers = true)
                val schedulers = notificationUseCases.getSchedulers.executeRequest()
                _state.value = _state.value.copy(
                    isLoadingSchedulers = false,
                    notificationScheduler = schedulers
                )
            } catch (e: Exception) {
                Log.d("scheduler", e.message.toString())

            }
        }
    }
}