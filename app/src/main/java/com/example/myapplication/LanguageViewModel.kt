// In LanguageViewModel.kt
package com.example.myapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.Locale

class LanguageViewModel : ViewModel() {
    // Default language is English
    val locale = mutableStateOf(Locale.ENGLISH)

    fun onLanguageChange(newLocale: Locale) {
        locale.value = newLocale
    }
}
