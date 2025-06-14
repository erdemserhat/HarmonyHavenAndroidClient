package com.erdemserhat.harmonyhaven.presentation.post_authentication.notification

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationDefinedType
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationSchedulerDto
import com.erdemserhat.harmonyhaven.data.api.notification.NotificationType
import com.erdemserhat.harmonyhaven.data.api.notification.PredefinedReminderSubject
import com.erdemserhat.harmonyhaven.domain.usecase.notification.NotificationUseCases
import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationUseCases: NotificationUseCases,
    context: Context
) : ViewModel() {
    private val _notifications = MutableStateFlow<List<NotificationDto>>(emptyList())
    val notifications: StateFlow<List<NotificationDto>> = _notifications

    // Offset variables
    private var currentPage = 1
    private val pageSize = 10

    var hasMoreData = true
    val isLoading = MutableStateFlow(false)

    //load notification via offset
    fun loadNotifications() {
        if (isLoading.value || !hasMoreData) return
        isLoading.value = true
        viewModelScope.launch {
            try {
                val newNotifications =
                    notificationUseCases.getNotification.executeRequest(currentPage, pageSize)
                if (newNotifications.isNotEmpty()) {
                    _notifications.value += newNotifications
                    currentPage++
                } else {
                    hasMoreData = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }

        Log.d("testNotification", _notifications.value.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshNotification(onRefreshed: () -> Unit) {

        viewModelScope.launch {
            try {
                val newNotifications =
                    notificationUseCases.getNotification.executeRequest(1, pageSize)
                if (newNotifications.isNotEmpty()) {
                    _notifications.value = newNotifications
                } else {
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                onRefreshed()
                hasMoreData = true

            }


        }

    }


    //Permission operations
    private val sharedPreferences =
        context.getSharedPreferences("PermissionPreferences", Context.MODE_PRIVATE)


    fun updatePermissionStatus(value: Boolean = false) {
        val key: String = "notificationPref"
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sharedPreferences.edit().putBoolean(key, value).apply()
            }
        }
    }

    fun isPermissionGranted(): Boolean {
        val key: String = "notificationPref"
        return sharedPreferences.getBoolean(key, false)
    }


}