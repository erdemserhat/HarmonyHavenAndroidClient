package com.erdemserhat.harmonyhaven.presentation.post_authentication.notification.scheduler_screen

import com.erdemserhat.harmonyhaven.data.api.notification.NotificationSchedulerDto

data class SchedulerState(
    val isSchedulingNotificationInProgress:Boolean = false,
    val isLoadingSchedulers:Boolean = false,
    val isDeletingSchedulerInProgress:Boolean = false,
    val notificationScheduler:List<NotificationSchedulerDto> = emptyList()
)