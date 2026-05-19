package com.example.kolo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()
    private val addSubscriptionViewModel: AddSubscriptionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Використовуємо базову системну тему, яка 100% є і ніколи не впаде
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showSplash by remember { mutableStateOf(true) }

                    LaunchedEffect(Unit) {
                        delay(2000) // Повісимо заставку на 2 секунди
                        showSplash = false
                    }

                    if (showSplash) {
                        // Звичайний текст замість XML-картинок, щоб уникнуть NotFoundException
                        Surface(modifier = Modifier.fillMaxSize()) {
                            Text("Завантаження Kolo (Subs.ua)...")
                        }
                    } else {
                        // Наш головний екран програми
                        MainScreen(profileViewModel, addSubscriptionViewModel)
                    }
                }
            }
        }
    }
}