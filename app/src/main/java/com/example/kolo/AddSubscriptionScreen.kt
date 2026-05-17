package com.example.kolo

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSubscriptionScreen(viewModel: AddSubscriptionViewModel) {

    // Автоматично ховаємо сповіщення через 3 секунди
    if (viewModel.showSuccessNotification) {
        LaunchedEffect(Unit) {
            delay(3000)
            viewModel.showSuccessNotification = false
        }
    }

    // Box дозволяє накладати елементи один на одного (плашку поверх форми)
    Box(modifier = Modifier.fillMaxSize()) {

        // Тут наша основна форма (усе без змін)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.screen_add_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = viewModel.name,
                onValueChange = {
                    viewModel.name = it
                    viewModel.nameError = false
                },
                label = { Text(stringResource(id = R.string.add_sub_name_label)) },
                isError = viewModel.nameError,
                supportingText = {
                    if (viewModel.nameError) {
                        Text("Назва не може бути порожньою", color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.price,
                    onValueChange = {
                        viewModel.price = it
                        viewModel.priceError = false
                    },
                    label = { Text(stringResource(id = R.string.add_sub_price_label)) },
                    isError = viewModel.priceError,
                    supportingText = {
                        if (viewModel.priceError) {
                            Text("Некоректна ціна", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.weight(1f)
                )

                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.width(110.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.selectedCurrency,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        viewModel.currencies.forEach { currency ->
                            DropdownMenuItem(
                                text = { Text(text = currency) },
                                onClick = {
                                    viewModel.selectedCurrency = currency
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Колір картки",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                viewModel.availableColors.forEach { color ->
                    val isSelected = viewModel.selectedColor == color
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(color)
                            .clickable { viewModel.selectedColor = color }
                            .border(
                                width = if (isSelected) 3.dp else 0.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.onSurface else Color.Transparent,
                                shape = CircleShape
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Іконка",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                viewModel.availableIcons.forEach { icon ->
                    val isSelected = viewModel.selectedIcon == icon
                    IconButton(
                        onClick = { viewModel.selectedIcon = icon },
                        modifier = Modifier
                            .background(
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { viewModel.validateAndSave() },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(stringResource(id = R.string.add_sub_save_btn))
            }
        }

        // ДОДАНО: Наша кастомна плашка (Top Banner), яка виїжджає зверху
        AnimatedVisibility(
            visible = viewModel.showSuccessNotification,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp) // Відступ від верхнього краю екрана
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer, // Використовуємо контрастний колір
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Успіх"
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Підписку успішно додано!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}