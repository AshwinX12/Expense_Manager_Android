package com.expensemanager.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expensemanager.app.ui.theme.Dimens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NumericKeypad(
    onDigit: (String) -> Unit,
    onDecimal: () -> Unit,
    onBackspace: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current

    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf(".", "0", "⌫")
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.KeypadSpacing)
    ) {
        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.KeypadSpacing)
            ) {
                row.forEach { key ->
                    if (key == "⌫") {
                        // Backspace: tap = delete one, hold = continuous smooth delete
                        BackspaceButton(
                            modifier = Modifier.weight(1f),
                            onBackspace = {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onBackspace()
                            },
                            onHoldBackspace = {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onBackspace()
                            },
                            onLongPressStart = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            }
                        )
                    } else {
                        KeypadButton(
                            text = key,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                when (key) {
                                    "." -> onDecimal()
                                    else -> onDigit(key)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Backspace button with hold-to-repeat: fires continuously every 80ms while held,
 * with an initial 400ms delay before repeat kicks in (like a hardware keyboard).
 */
@Composable
private fun BackspaceButton(
    modifier: Modifier = Modifier,
    onBackspace: () -> Unit,
    onHoldBackspace: () -> Unit,
    onLongPressStart: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.92f else 1f,
        animationSpec = tween(80),
        label = "backspaceScale"
    )

    Box(
        modifier = modifier
            .height(Dimens.KeypadButtonSize)
            .scale(scale)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.35f))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { _ ->
                        pressed = true
                        // Fire first delete immediately on press
                        onBackspace()
                        var holding = true
                        // Launch hold-to-repeat in background
                        val holdJob = coroutineScope.launch {
                            delay(380) // initial delay before repeat
                            onLongPressStart()
                            while (holding) {
                                onHoldBackspace()
                                delay(70) // repeat every 70ms — smooth continuous delete
                            }
                        }
                        // Wait for release
                        tryAwaitRelease()
                        holding = false
                        holdJob.cancel()
                        pressed = false
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "⌫",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun KeypadButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.92f else 1f,
        animationSpec = tween(80),
        label = "keyScale"
    )

    Box(
        modifier = modifier
            .height(Dimens.KeypadButtonSize)
            .scale(scale)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { _ ->
                        pressed = true
                        onClick()
                        tryAwaitRelease()
                        pressed = false
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
