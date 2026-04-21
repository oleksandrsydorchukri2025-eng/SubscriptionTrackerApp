package com.example.subscriptiontracker.api // Пакет має бути саме такий

data class Subscription(
    val id: Int,
    val name: String,
    val price: Double,
    val currency: String,
    val color: String
)