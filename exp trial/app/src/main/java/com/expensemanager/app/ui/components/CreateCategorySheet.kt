package com.expensemanager.app.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.expensemanager.app.ui.theme.ButtonShape
import com.expensemanager.app.ui.theme.CategoryColorsClassic
import com.expensemanager.app.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCategorySheet(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
    palette: List<Color> = CategoryColorsClassic,
    usedColors: List<String> = emptyList()
) {
    var name by remember { mutableStateOf("") }
    val hexPalette = remember(palette) { palette.map { String.format("#%06X", 0xFFFFFF and it.toArgb()) } }
    var selectedColor by remember(palette) { mutableStateOf(hexPalette.first()) }
    var showColorWheel by remember { mutableStateOf(false) }
    val isCustomColor = selectedColor !in hexPalette

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)
        ) {
            Text(
                "New Category",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Category name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(android.graphics.Color.parseColor(selectedColor)))
                    )
                }
            )

            Text(
                "Color",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Color swatches, plus a custom swatch that opens a full color wheel
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                hexPalette.forEach { colorHex ->
                    val isSelected = colorHex == selectedColor
                    val scale by animateFloatAsState(
                        targetValue = if (isSelected) 1.2f else 1f,
                        animationSpec = spring(stiffness = Spring.StiffnessLow),
                        label = "colorScale"
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .scale(scale)
                            .clip(CircleShape)
                            .background(Color(android.graphics.Color.parseColor(colorHex)))
                            .then(
                                if (isSelected) Modifier.border(2.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                                else Modifier
                            )
                            .clickable { selectedColor = colorHex }
                    )
                }

                // Custom color — opens a full color wheel
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .scale(if (isCustomColor) 1.2f else 1f)
                        .clip(CircleShape)
                        .then(
                            if (isCustomColor) Modifier.background(Color(android.graphics.Color.parseColor(selectedColor)))
                            else Modifier.background(
                                Brush.sweepGradient(
                                    listOf(Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta, Color.Red)
                                )
                            )
                        )
                        .then(
                            if (isCustomColor) Modifier.border(2.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                            else Modifier
                        )
                        .clickable { showColorWheel = true },
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    if (!isCustomColor) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Custom color",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Button(
                onClick = { if (name.isNotBlank()) onSave(name, selectedColor) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = name.isNotBlank(),
                shape = ButtonShape
            ) {
                Text("Create Category", fontWeight = FontWeight.SemiBold)
            }
        }
    }

    if (showColorWheel) {
        ColorWheelDialog(
            initialColor = Color(android.graphics.Color.parseColor(selectedColor)),
            onDismiss = { showColorWheel = false },
            onConfirm = { hex ->
                selectedColor = hex
                showColorWheel = false
            }
        )
    }
}
