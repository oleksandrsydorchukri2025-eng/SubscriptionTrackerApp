package com.example.kolo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Оголошуємо стани для екрана профілю
sealed interface ProfileUiState {
    object Loading : ProfileUiState
    object Success : ProfileUiState
    object Error : ProfileUiState
}

class ProfileViewModel : ViewModel() {

    // Створюємо реактивний потік стану. За замовчуванням ставимо Success, щоб додаток показував контент
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Success)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadProfile() {
        _uiState.value = ProfileUiState.Loading
        // Тут колись буде завантаження, а поки повертаємо Success
        _uiState.value = ProfileUiState.Success
    }

    // Стан для перемикача сповіщень
    var notificationsEnabled by mutableStateOf(true)

    // Стан для вибору валюти
    val currencies = listOf("UAH", "USD", "EUR")
    var selectedCurrency by mutableStateOf(currencies[0])

    // Фіктивний курс валют для інформера
    val currentExchangeRate = "1 $ = 39.5 ₴"
}