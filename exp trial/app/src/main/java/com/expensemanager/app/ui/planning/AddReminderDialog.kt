package com.expensemanager.app.ui.planning

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.expensemanager.app.data.db.entity.ReminderType
import com.expensemanager.app.ui.theme.Dimens
import com.expensemanager.app.util.formatDisplay
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderDialog(
    onDismiss: () -> Unit,
    onSave: (title: String, description: String, dueDate: LocalDate, leadTimeDays: Int, type: ReminderType) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf(LocalDate.now().plusDays(1)) }
    var showDatePicker by remember { mutableStateOf(false) }
    var leadTimeText by remember { mutableStateOf("3") }
    var type by remember { mutableStateOf(ReminderType.BILL) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = dueDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        dueDate = java.time.Instant.ofEpochMilli(millis).atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancel") } }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Reminder") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                OutlinedTextField(
                    title, { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    description, { description = it },
                    label = { Text("Description (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = dueDate.formatDisplay(),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Due date") },
                    trailingIcon = {
                        Icon(Icons.Default.CalendarToday, null, modifier = Modifier.clickable { showDatePicker = true })
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    leadTimeText,
                    { leadTimeText = it.filter { c -> c.isDigit() } },
                    label = { Text("Remind me this many days before") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Text("Type", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingXs)) {
                    ReminderType.entries.forEach { t ->
                        FilterChip(
                            selected = type == t,
                            onClick = { type = t },
                            label = {
                                Text(
                                    t.name.lowercase().replace('_', ' ').replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                enabled = title.isNotBlank(),
                onClick = {
                    onSave(title.trim(), description, dueDate, leadTimeText.toIntOrNull() ?: 3, type)
                }
            ) { Text("Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
