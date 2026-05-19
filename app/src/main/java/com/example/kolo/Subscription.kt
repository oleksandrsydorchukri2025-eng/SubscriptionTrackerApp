package com.example.kolo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.util.UUID

// Тепер наша підписка вміщує абсолютно всі дані з вашого екрана
data class Subscription(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: String,
    val currency: String,
    val color: Color,
    val icon: ImageVector
)