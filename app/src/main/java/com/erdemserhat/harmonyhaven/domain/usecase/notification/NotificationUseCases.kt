package com.erdemserhat.harmonyhaven.domain.usecase.notification

import com.erdemserhat.harmonyhaven.data.api.notification.NotificationSchedulerDto

data class NotificationUseCases(
    val getNotification: GetNotification,
    val getSchedulers: GetSchedulers,
    val deleteScheduler: DeleteScheduler,
    val scheduleNotification: ScheduleNotification,
    val updateScheduler: UpdateScheduler,
    val getNotificationCount: GetNotificationCount
)
