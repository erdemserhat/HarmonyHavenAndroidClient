package com.erdemserhat.harmonyhaven.presentation.notification

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity
import com.erdemserhat.harmonyhaven.data.local.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor(
    notificationRepository: NotificationRepository,
    context: Context
) :ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    val allNotifications: LiveData<List<NotificationEntity>> = notificationRepository.allNotifications
    private val sharedPreferences = context.getSharedPreferences("PermissionPreferences", Context.MODE_PRIVATE)


    init {
        Log.d("erdem3451",notificationRepository.allNotifications.value?.size.toString())
    }

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun updatePermissionStatus(value: Boolean=false) {
        val key: String ="notificationPref"
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sharedPreferences.edit().putBoolean(key, value).apply()
            }
        }
    }

    fun isPermissionGranted(): Boolean {
        val key: String ="notificationPref"
        return sharedPreferences.getBoolean(key,false)
    }





}