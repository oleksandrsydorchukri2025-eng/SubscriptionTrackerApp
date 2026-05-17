package com.example.subscriptiontracker.api

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)