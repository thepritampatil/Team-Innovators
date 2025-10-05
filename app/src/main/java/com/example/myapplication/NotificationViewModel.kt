package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class Notification(val message: String, val id: Int = System.identityHashCode(System.currentTimeMillis()))

class NotificationViewModel : ViewModel() {
    private val _notifications = mutableStateListOf<Notification>()
    val notifications: List<Notification> = _notifications
    var unreadCount = mutableStateOf(0)
        private set

    fun addNotification(message: String) {
        _notifications.add(0, Notification(message = message))
        unreadCount.value++
    }

    fun clearUnreadCount() {
        unreadCount.value = 0
    }
}
