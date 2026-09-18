package com.expensemanager.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.expensemanager.app.ui.theme.Dimens
import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.sqrt

/**
 * An HSV color wheel: hue by angle, saturation by radial distance from center,
 * plus a brightness slider. Lets the user pick any custom color, not just a fixed swatch.
 */
@Composable
fun ColorWheelDialog(
    initialColor: Color,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val initialHsv = remember(initialColor) {
        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(
            android.graphics.Color.rgb(
                (initialColor.red * 255).toInt(),
                (initialColor.green * 255).toInt(),
                (initialColor.blue * 255).toInt()
            ),
            hsv
        )
        hsv
    }
    var hue by remember { mutableStateOf(initialHsv[0]) }
    var saturation by remember { mutableStateOf(initialHsv[1]) }
    var value by remember { mutableStateOf(if (initialHsv[2] <= 0f) 1f else initialHsv[2]) }

    val currentColor = remember(hue, saturation, value) {
        Color(android.graphics.Color.HSVToColor(floatArrayOf(hue, saturation, value)))
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Custom Color") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ColorWheel(
                    hue = hue,
                    saturation = saturation,
                    value = value,
                    onColorChange = { h, s -> hue = h; saturation = s },
                    modifier = Modifier.size(220.dp)
                )
                Spacer(Modifier.height(Dimens.SpacingLg))
                Text(
                    "Brightness",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Slider(
                    value = value,
                    onValueChange = { value = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(Dimens.SpacingSm))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(currentColor)
                        .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(String.format("#%06X", 0xFFFFFF and currentColor.toArgb()))
            }) { Text("Use Color", fontWeight = FontWeight.SemiBold) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun ColorWheel(
    hue: Float,
    saturation: Float,
    value: Float,
    onColorChange: (hue: Float, saturation: Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val hueColors = remember {
        (0..360 step 30).map { Color(android.graphics.Color.HSVToColor(floatArrayOf(it.toFloat(), 1f, 1f))) }
    }

    Canvas(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures { offset -> updateFromOffset(offset, size.width, size.height, onColorChange) }
        }.pointerInput(Unit) {
            detectDragGestures { change, _ ->
                updateFromOffset(change.position, size.width, size.height, onColorChange)
            }
        }
    ) {
        val radius = min(size.width, size.height) / 2f
        val center = Offset(size.width / 2f, size.height / 2f)

        // Hue ring via sweep gradient
        drawCircle(
            brush = Brush.sweepGradient(hueColors, center = center),
            radius = radius,
            center = center
        )
        // Saturation: fade to white towards center
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.White, Color.White.copy(alpha = 0f)),
                center = center,
                radius = radius
            ),
            radius = radius,
            center = center
        )
        // Brightness: darken uniformly based on (1 - value)
        if (value < 1f) {
            drawCircle(
                color = Color.Black.copy(alpha = 1f - value),
                radius = radius,
                center = center
            )
        }

        // Selector marker
        val angleRad = Math.toRadians(hue.toDouble())
        val r = saturation * radius
        val markerX = center.x + (r * kotlin.math.cos(angleRad)).toFloat()
        val markerY = center.y + (r * kotlin.math.sin(angleRad)).toFloat()
        drawCircle(
            color = Color.White,
            radius = 8.dp.toPx(),
            center = Offset(markerX, markerY),
            style = Stroke(width = 2.dp.toPx())
        )
        drawCircle(
            color = Color.Black.copy(alpha = 0.4f),
            radius = 9.dp.toPx(),
            center = Offset(markerX, markerY),
            style = Stroke(width = 1.dp.toPx())
        )
    }
}

private fun updateFromOffset(
    offset: Offset,
    width: Int,
    height: Int,
    onColorChange: (hue: Float, saturation: Float) -> Unit
) {
    val radius = min(width, height) / 2f
    val center = Offset(width / 2f, height / 2f)
    val dx = offset.x - center.x
    val dy = offset.y - center.y
    val distance = sqrt(dx * dx + dy * dy)
    val saturation = (distance / radius).coerceIn(0f, 1f)
    var angle = Math.toDegrees(atan2(dy, dx).toDouble()).toFloat()
    if (angle < 0f) angle += 360f
    onColorChange(angle, saturation)
}
