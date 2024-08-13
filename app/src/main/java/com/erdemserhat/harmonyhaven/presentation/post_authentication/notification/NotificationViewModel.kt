package com.erdemserhat.harmonyhaven.presentation.post_authentication.notification

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.dto.responses.NotificationDto
import com.erdemserhat.harmonyhaven.domain.usecase.notification.NotificationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private val pageSize = 5
    var isLoading = false
    var hasMoreData = true

    //load notification via offset
    fun loadNotifications() {
        if (isLoading || !hasMoreData) return
        isLoading = true
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
                isLoading = false
            }
        }

        Log.d("testNotification",_notifications.value.toString())
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