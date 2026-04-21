package com.example.subscriptiontracker.api

data class AddSubscriptionRequest(
    val name: String,
    val price: Double,
    val currency: String
)