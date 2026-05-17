package com.example.kolo

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

@Composable
fun ExpensesPieChart(
    data: Map<String, Float>,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) return

    val colorPalette = listOf(
        Color(0xFFE57373),
        Color(0xFF81C784),
        Color(0xFF64B5F6),
        Color(0xFFFFD54F),
        Color(0xFFBA68C8),
        Color(0xFF4DB6AC)
    )

    val slices = data.entries.mapIndexed { index, entry ->
        PieChartData.Slice(
            label = entry.key,
            value = entry.value,
            color = colorPalette[index % colorPalette.size]
        )
    }

    val pieChartData = PieChartData(
        slices = slices,
        plotType = co.yml.charts.common.model.PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = true,
        animationDuration = 1000,
        activeSliceAlpha = 0.9f
    )

    PieChart(
        modifier = modifier
            .width(300.dp)
            .height(300.dp),
        pieChartData = pieChartData,
        pieChartConfig = pieChartConfig
    )
}