package com.example.myapplication



// Data class to hold patient information
data class Patient(
    val id: Int = System.identityHashCode(System.currentTimeMillis()), // Simple unique ID
    val name: String,
    val age: String,
    val gender: String,
    val address: String,
    val mobile: String,
    val familyMembers: String,
    val medicalHistory: String,
    val tookVaccine: String,
    val bloodGroup: String,
    val isMarried: String,
    val kidsCount: String,
    val tookCovidVaccine: String,
    val tookPolioVaccine: String
)
