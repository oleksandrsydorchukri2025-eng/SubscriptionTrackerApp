package com.example.subscriptiontracker.api

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String? // Додаємо поле для токену
)