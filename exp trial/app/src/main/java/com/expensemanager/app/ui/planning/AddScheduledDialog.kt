package com.expensemanager.app.ui.planning

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.expensemanager.app.data.db.entity.AccountEntity
import com.expensemanager.app.data.db.entity.CategoryEntity
import com.expensemanager.app.data.db.entity.RecurringFrequency
import com.expensemanager.app.data.db.entity.TransactionType
import com.expensemanager.app.ui.theme.Dimens
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduledDialog(
    initialAmount: String = "",
    initialAccountId: Long? = null,
    categories: List<CategoryEntity>,
    accounts: List<AccountEntity>,
    onDismiss: () -> Unit,
    onSave: (BigDecimal, TransactionType, RecurringFrequency, LocalDate, LocalTime?, Long?, Long?) -> Unit
) {
    var amount by remember { mutableStateOf(initialAmount) }
    var type by remember { mutableStateOf(TransactionType.EXPENSE) }
    var frequency by remember { mutableStateOf(RecurringFrequency.MONTHLY) }
    var frequencyExpanded by remember { mutableStateOf(false) }

    var selectedCategoryId by remember { mutableStateOf<Long?>(null) }
    var categoryExpanded by remember { mutableStateOf(false) }

    var selectedAccountId by remember { mutableStateOf(initialAccountId ?: accounts.firstOrNull { it.isDefault }?.id ?: accounts.firstOrNull()?.id) }
    var accountExpanded by remember { mutableStateOf(false) }

    // If accounts load after dialog opens (timing), auto-select default
    androidx.compose.runtime.LaunchedEffect(accounts) {
        if (selectedAccountId == null && accounts.isNotEmpty()) {
            selectedAccountId = initialAccountId ?: accounts.firstOrNull { it.isDefault }?.id ?: accounts.firstOrNull()?.id
        }
    }

    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    var startTime by remember { mutableStateOf<LocalTime?>(null) }
    var showTimePicker by remember { mutableStateOf(false) }

    val filteredCategories = categories

    // DatePicker
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = startDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        startDate = java.time.Instant.ofEpochMilli(millis).atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // TimePicker
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = startTime?.hour ?: LocalTime.now().hour,
            initialMinute = startTime?.minute ?: LocalTime.now().minute
        )
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            title = { Text("Select Time") },
            text = { TimePicker(state = timePickerState) },
            confirmButton = {
                TextButton(onClick = {
                    startTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                    showTimePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancel") }
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Schedule Transaction") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
                ) {
                    FilterChip(
                        selected = type == TransactionType.EXPENSE,
                        onClick = { type = TransactionType.EXPENSE; selectedCategoryId = null },
                        label = { Text("Expense") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = type == TransactionType.INCOME,
                        onClick = { type = TransactionType.INCOME; selectedCategoryId = null },
                        label = { Text("Income") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = !categoryExpanded }
                ) {
                    val selectedCat = filteredCategories.find { it.id == selectedCategoryId }
                    OutlinedTextField(
                        value = selectedCat?.name ?: "Select Category",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        filteredCategories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat.name) },
                                onClick = {
                                    selectedCategoryId = cat.id
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = frequencyExpanded,
                    onExpandedChange = { frequencyExpanded = !frequencyExpanded }
                ) {
                    OutlinedTextField(
                        value = frequency.name.lowercase().replaceFirstChar { it.uppercase() },
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Frequency") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = frequencyExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = frequencyExpanded,
                        onDismissRequest = { frequencyExpanded = false }
                    ) {
                        RecurringFrequency.entries.filter { it != RecurringFrequency.CUSTOM }.forEach { freq ->
                            DropdownMenuItem(
                                text = { Text(freq.name.lowercase().replaceFirstChar { it.uppercase() }) },
                                onClick = {
                                    frequency = freq
                                    frequencyExpanded = false
                                }
                            )
                        }
                    }
                }

                // Account picker
                ExposedDropdownMenuBox(
                    expanded = accountExpanded,
                    onExpandedChange = { accountExpanded = !accountExpanded }
                ) {
                    val selectedAcc = accounts.find { it.id == selectedAccountId }
                    OutlinedTextField(
                        value = selectedAcc?.name ?: "Select Account",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Account") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = accountExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = accountExpanded,
                        onDismissRequest = { accountExpanded = false }
                    ) {
                        accounts.forEach { acc ->
                            DropdownMenuItem(
                                text = { Text(acc.name) },
                                onClick = {
                                    selectedAccountId = acc.id
                                    accountExpanded = false
                                }
                            )
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                    OutlinedTextField(
                        value = startDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Begin Date") },
                        trailingIcon = {
                            Icon(Icons.Default.CalendarToday, null, modifier = Modifier.clickable { showDatePicker = true })
                        },
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = startTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "None",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Time") },
                        trailingIcon = {
                            Icon(Icons.Default.Schedule, null, modifier = Modifier.clickable { showTimePicker = true })
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    amount.toBigDecimalOrNull()?.let {
                        onSave(it, type, frequency, startDate, startTime, selectedCategoryId, selectedAccountId)
                    }
                },
                enabled = amount.isNotBlank() && amount.toBigDecimalOrNull() != null
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
