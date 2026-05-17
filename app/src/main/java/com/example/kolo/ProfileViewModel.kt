package com.example.kolo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    // Стан для перемикача сповіщень
    var notificationsEnabled by mutableStateOf(true)

    // Стан для вибору валюти
    val currencies = listOf("UAH", "USD", "EUR")
    var selectedCurrency by mutableStateOf(currencies[0])

    // Фіктивний курс валют для інформера
    val currentExchangeRate = "1 $ = 39.5 ₴"
}