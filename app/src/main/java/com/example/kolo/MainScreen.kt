package com.example.kolo

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import com.example.subscriptiontracker.R // ДОДАНО: Імпорт правильного класу ресурсів проєкту
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun MainScreen(
    profileViewModel: ProfileViewModel,
    addSubscriptionViewModel: AddSubscriptionViewModel
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var isMenuVisible by remember { mutableStateOf(false) }
    var showNotification by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isMenuVisible = true
        delay(3000)
        showNotification = true
        delay(4000)
        showNotification = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = isMenuVisible,
                    enter = slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight },
                        animationSpec = tween(durationMillis = 800)
                    ) + fadeIn(animationSpec = tween(durationMillis = 800))
                ) {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                            label = { Text(stringResource(R.string.menu_subscription_list)) },
                            selected = selectedTabIndex == 0,
                            onClick = { selectedTabIndex = 0 }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.AddCircle, contentDescription = null) },
                            label = { Text(stringResource(R.string.menu_add_subscription)) },
                            selected = selectedTabIndex == 1,
                            onClick = { selectedTabIndex = 1 }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Star, contentDescription = null) },
                            label = { Text(stringResource(R.string.menu_analytics)) }, // ВИПРАВЛЕНО ТУТ!
                            selected = selectedTabIndex == 2,
                            onClick = { selectedTabIndex = 2 }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                            label = { Text(stringResource(R.string.menu_settings)) },
                            selected = selectedTabIndex == 3,
                            onClick = { selectedTabIndex = 3 }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                when (selectedTabIndex) {
                    0 -> SubscriptionListScreen()
                    1 -> AddSubscriptionScreen(viewModel = addSubscriptionViewModel)
                    2 -> AnalyticsScreen()
                    3 -> ProfileScreen(viewModel = profileViewModel)
                }
            }
        }

        TopNotificationBar(
            message = "Нагадування: Завтра оплата Netflix (200 ₴)",
            isVisible = showNotification,
            onDismiss = { showNotification = false }
        )
    }
}

@Composable
fun TopNotificationBar(message: String, isVisible: Boolean, onDismiss: () -> Unit) {
    // Якщо сповіщення невидиме, просто не малюємо його
    if (!isVisible) return

    // Малюємо робочу плашку
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
            .clickable { onDismiss() } // Закриваємо при кліку
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

@Composable
fun SubscriptionListScreen() {
    // Тимчасові дані для красивого відображення (поки не підключите базу даних)
    val dummySubscriptions = listOf(
        "Netflix" to "200 ₴",
        "Spotify Premium" to "150 ₴",
        "YouTube Premium" to "99 ₴",
        "Google One" to "45 ₴"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Мої підписки",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
        )

        // Сам список підписок, який можна скролити
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dummySubscriptions) { sub ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = sub.first, // Назва сервісу
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = sub.second, // Ціна
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}