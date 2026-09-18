package com.expensemanager.app.ui.planning

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.expensemanager.app.data.db.entity.BudgetPeriod
import com.expensemanager.app.data.db.entity.CategoryEntity
import com.expensemanager.app.ui.theme.Dimens
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetDialog(
    categories: List<CategoryEntity>,
    onDismiss: () -> Unit,
    onSave: (Long?, BigDecimal, BudgetPeriod, Boolean) -> Unit,
    onCreateCategory: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf<Long?>(null) }
    var period by remember { mutableStateOf(BudgetPeriod.MONTHLY) }
    var rollover by remember { mutableStateOf(false) }

    var categoryDropdownExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Budget") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = categoryDropdownExpanded,
                    onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded }
                ) {
                    val categoryName = if (selectedCategoryId == null) "Overall (All Categories)"
                    else categories.find { it.id == selectedCategoryId }?.name ?: "Overall"

                    OutlinedTextField(
                        value = categoryName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = categoryDropdownExpanded,
                        onDismissRequest = { categoryDropdownExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Overall (All Categories)") },
                            onClick = {
                                selectedCategoryId = null
                                categoryDropdownExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("+ Create New", color = MaterialTheme.colorScheme.primary) },
                            onClick = {
                                categoryDropdownExpanded = false
                                onCreateCategory()
                            }
                        )
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    selectedCategoryId = category.id
                                    categoryDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Text("Period", style = MaterialTheme.typography.labelMedium)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    BudgetPeriod.values().forEach { p ->
                        FilterChip(
                            selected = period == p,
                            onClick = { period = p },
                            label = { Text(p.name.lowercase().replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }

                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Checkbox(checked = rollover, onCheckedChange = { rollover = it })
                    Text("Rollover unused amount")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                amount.toBigDecimalOrNull()?.let { amt ->
                    onSave(selectedCategoryId, amt, period, rollover)
                }
            }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
