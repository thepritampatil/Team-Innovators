package com.example.myapplication

import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    val patientViewModel = PatientViewModel()
    val notificationViewModel = NotificationViewModel()
}
