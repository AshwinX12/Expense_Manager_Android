package com.expensemanager.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.expensemanager.app.ui.theme.*
import java.math.BigDecimal
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ChartCard(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CardShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation)
    ) {
        Column(modifier = Modifier.padding(Dimens.CardPadding)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(Dimens.SpacingMd))
            content()
        }
    }
}

/**
 * Simple pie chart drawn with Compose Canvas — each category is a fully filled wedge.
 */
@Composable
fun PieChart(
    data: List<Pair<String, BigDecimal>>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .size(Dimens.ChartHeight)
            .padding(16.dp)
    ) {
        val total = data.sumOf { it.second }.toFloat()
        if (total <= 0) return@Canvas

        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)
        var startAngle = -90f

        data.forEachIndexed { index, (_, value) ->
            val sweepAngle = (value.toFloat() / total) * 360f
            drawArc(
                color = colors[index % colors.size],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
            startAngle += sweepAngle
        }
    }
}

/**
 * Simple line chart drawn with Compose Canvas.
 */
@Composable
fun LineChart(
    data: List<Float>,
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.primary,
    fillAlpha: Float = 0.1f
) {
    Canvas(modifier = modifier.fillMaxWidth().height(Dimens.ChartHeight)) {
        if (data.size < 2) return@Canvas

        val maxVal = data.max().coerceAtLeast(1f)
        val minVal = 0f
        val range = maxVal - minVal

        val stepX = size.width / (data.size - 1)
        val points = data.mapIndexed { i, value ->
            Offset(
                x = i * stepX,
                y = size.height - ((value - minVal) / range * size.height * 0.85f) - size.height * 0.05f
            )
        }

        // Fill
        val fillPath = Path().apply {
            moveTo(points.first().x, size.height)
            points.forEach { lineTo(it.x, it.y) }
            lineTo(points.last().x, size.height)
            close()
        }
        drawPath(fillPath, lineColor.copy(alpha = fillAlpha))

        // Line
        val linePath = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (i in 1 until points.size) {
                lineTo(points[i].x, points[i].y)
            }
        }
        drawPath(
            linePath,
            lineColor,
            style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Dots
        points.forEach { point ->
            drawCircle(lineColor, radius = 4f, center = point)
        }
    }
}

/**
 * Simple bar chart drawn with Compose Canvas.
 */
@Composable
fun BarChart(
    data: List<Pair<String, Float>>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxWidth().height(Dimens.ChartHeight)) {
        if (data.isEmpty()) return@Canvas

        val maxVal = data.maxOf { it.second }.coerceAtLeast(1f)
        val barWidth = size.width / (data.size * 2f)
        val spacing = barWidth

        data.forEachIndexed { index, (_, value) ->
            val barHeight = (value / maxVal) * size.height * 0.85f
            val x = index * (barWidth + spacing) + spacing / 2
            val y = size.height - barHeight

            drawRoundRect(
                color = colors[index % colors.size],
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
            )
        }
    }
}
