// 1. ПАКЕТ: Це адреса файлу. Вона має бути першим рядком.
package com.example.kolo

// 2. ІМПОРТИ: Це перелік бібліотек, які ми використовуємо.
// Android Studio сама їх підтягне, якщо ти вставиш цей блок.
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

// 3. МОДЕЛЬ ДАНИХ: Опис того, як виглядає одна категорія витрат.
data class CategoryExpense(
    val name: String,
    val amount: Float,
    val icon: ImageVector
)

// 4. ОСНОВНИЙ ЕКРАН: Функція, яка малює все, що ми бачимо.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen() {
    // Це наші тимчасові дані, щоб ми бачили результат на екрані
    val dummyData = listOf(
        CategoryExpense("Кіно та ТБ", 450f, Icons.Default.PlayArrow),
        CategoryExpense("Музика", 150f, Icons.Default.Star),
        CategoryExpense("Ігри", 300f, Icons.Default.Build),
        CategoryExpense("Робота", 200f, Icons.Default.AccountBox)
    )

    // Готуємо дані для графіка (назва -> сума)
    val chartData = dummyData.associate { it.name to it.amount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Аналітика") }, // Назва зверху
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ТУТ МАЄ БУТИ ГРАФІК (Step 1 з попереднього завдання)
            // Якщо ти ще не створив ExpensesPieChart, цей рядок може світитися червоним.
            ExpensesPieChart(
                data = chartData,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Деталізація витрат",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 16.dp)
            )

            // Список категорій у вигляді карток
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(dummyData) { item ->
                    ExpenseCategoryCard(item)
                }
            }
        }
    }
}

// 5. ДОПОМІЖНА ФУНКЦІЯ: Як виглядає одна окрема картка в списку.
@Composable
fun ExpenseCategoryCard(item: CategoryExpense) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant, // Кольори Material 3
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.name,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = "${item.amount.toInt()} ₴",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}