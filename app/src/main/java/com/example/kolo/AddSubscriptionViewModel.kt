package com.example.kolo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow // ДОДАНО для списку
import kotlinx.coroutines.flow.StateFlow // ДОДАНО для списку
import kotlinx.coroutines.flow.asStateFlow // ДОДАНО для списку

class AddSubscriptionViewModel : ViewModel() {
    var name by mutableStateOf("")
    var price by mutableStateOf("")

    val currencies = listOf("UAH", "USD", "EUR", "PLN")
    var selectedCurrency by mutableStateOf(currencies[0])

    val availableColors = listOf(
        Color(0xFFE57373),
        Color(0xFF81C784),
        Color(0xFF64B5F6),
        Color(0xFFFFD54F),
        Color(0xFFBA68C8)
    )
    var selectedColor by mutableStateOf(availableColors[0])

    val availableIcons = listOf(
        Icons.Default.ShoppingCart,
        Icons.Default.Phone,
        Icons.Default.Email,
        Icons.Default.Home,
        Icons.Default.Star
    )
    var selectedIcon by mutableStateOf(availableIcons[0])

    var nameError by mutableStateOf(false)
    var priceError by mutableStateOf(false)

    // ДОДАНО: Змінна, яка керує тим, чи показувати сповіщення
    var showSuccessNotification by mutableStateOf(false)

    // ДОДАНО: Наш список (Пам'ять програми)
    private val _subscriptions = MutableStateFlow<List<Subscription>>(emptyList())
    val subscriptions: StateFlow<List<Subscription>> = _subscriptions.asStateFlow()

    fun validateAndSave() {
        nameError = name.isBlank()

        val priceValue = price.toDoubleOrNull() ?: 0.0
        priceError = price.isBlank() || priceValue <= 0.0

        val isValid = !nameError && !priceError
        if (isValid) {
            // ДОДАНО: Пакуємо дані у нашу "папку" Subscription і зберігаємо у список
            val newSub = Subscription(
                name = name,
                price = price,
                currency = selectedCurrency,
                color = selectedColor,
                icon = selectedIcon
            )
            _subscriptions.value = _subscriptions.value + newSub

            // Очищаємо поля після збереження
            name = ""
            price = ""
            selectedCurrency = currencies[0]
            selectedColor = availableColors[0]
            selectedIcon = availableIcons[0]

            // Після успішного збереження кажемо плашці "З'явись!"
            showSuccessNotification = true
        }
    }
}