package com.example.kolo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import com.example.kolo.ui.theme.KoloTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()
    private val addSubscriptionViewModel: AddSubscriptionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoloTheme {
                // Стан, який визначає, чи показувати зараз екран завантаження
                var showSplash by remember { mutableStateOf(true) }

                // Запускаємо таймер на 2 секунди
                LaunchedEffect(Unit) {
                    delay(2000) // 2000 мілісекунд = 2 секунди
                    showSplash = false // Вимикаємо Splash Screen
                }

                // Логіка перемикання екранів
                if (showSplash) {
                    SplashScreen()
                } else {
                    MainScreen(profileViewModel, addSubscriptionViewModel)
                }
            }
        }
    }
}