package com.erdemserhat.harmonyhaven.presentation.notification

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity
import com.erdemserhat.harmonyhaven.data.local.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor(
    notificationRepository: NotificationRepository
) :ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    val allNotifications: LiveData<List<NotificationEntity>> = notificationRepository.allNotifications

    init {
        Log.d("erdem3451",notificationRepository.allNotifications.value?.size.toString())
    }

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }



}