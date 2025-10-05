package com.example.myapplication


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

// This ViewModel will hold the list of patients.
// In a real app, this would interact with a Room database.
class PatientViewModel : ViewModel() {
    private val _patients = mutableStateListOf<Patient>()
    val patients: List<Patient> = _patients

    fun addPatient(patient: Patient) {
        _patients.add(patient)
    }

    fun getPatient(id: Int): Patient? {
        return _patients.find { it.id == id }
    }

    fun updatePatient(updatedPatient: Patient) {
        val index = _patients.indexOfFirst { it.id == updatedPatient.id }
        if (index != -1) {
            _patients[index] = updatedPatient
        }
    }
}
