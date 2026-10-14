package com.expensemanager.app.ui.planning

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.expensemanager.app.data.db.entity.BudgetEntity
import com.expensemanager.app.data.db.entity.BudgetPeriod
import com.expensemanager.app.data.db.entity.CategoryEntity
import com.expensemanager.app.ui.theme.Dimens
import java.math.BigDecimal

/**
 * Create one or many budgets at once. Tick any number of categories (and/or Overall); each gets
 * its own amount. With two or more categories ticked, "Club into one budget" merges them into a
 * single budget with one shared limit.
 */
@Composable
fun AddBudgetDialog(
    categories: List<CategoryEntity>,
    existingBudgets: List<BudgetEntity> = emptyList(),
    onDismiss: () -> Unit,
    onSave: (List<BudgetDraft>, BudgetPeriod, Boolean) -> Unit,
    onCreateCategory: () -> Unit
) {
    var overallSelected by remember { mutableStateOf(false) }
    var selectedIds by remember { mutableStateOf(setOf<Long>()) }
    // Amounts keyed by category id; OVERALL_KEY for the overall budget.
    var amounts by remember { mutableStateOf(mapOf<Long, String>()) }
    var club by remember { mutableStateOf(false) }
    var clubAmount by remember { mutableStateOf("") }
    var clubName by remember { mutableStateOf("") }
    var period by remember { mutableStateOf(BudgetPeriod.MONTHLY) }
    var rollover by remember { mutableStateOf(false) }

    val selectedCategories = categories.filter { it.id in selectedIds }
    // Clubbing only makes sense (and only shows) once two or more categories are ticked.
    val clubbing = club && selectedCategories.size >= 2

    fun amountOf(key: Long) = amounts[key]?.toBigDecimalOrNull()

    // Warn (not block) when a ticked category is already covered by an existing budget: its
    // spending would then count toward both budgets. Overall is exempt — it overlaps by design.
    val overlapWarnings = selectedCategories.mapNotNull { category ->
        val covering = existingBudgets.filter { category.id in it.effectiveCategoryIds }
        if (covering.isEmpty()) null else {
            val names = covering.joinToString(", ") { b ->
                "\"${b.name ?: b.effectiveCategoryIds.joinToString(" + ") { id ->
                    categories.find { it.id == id }?.name ?: "Unknown"
                }}\" (${b.period.name.lowercase()})"
            }
            "${category.name} is already in $names — its spending will count toward both budgets."
        }
    }

    val drafts: List<BudgetDraft> = buildList {
        if (overallSelected) {
            amountOf(OVERALL_KEY)?.let { add(BudgetDraft(emptyList(), null, it)) }
        }
        if (clubbing) {
            clubAmount.toBigDecimalOrNull()?.let {
                add(BudgetDraft(selectedCategories.map { c -> c.id }, clubName, it))
            }
        } else {
            selectedCategories.forEach { c ->
                amountOf(c.id)?.let { add(BudgetDraft(listOf(c.id), null, it)) }
            }
        }
    }
    val selectionCount = (if (overallSelected) 1 else 0) + (if (clubbing) 1 else selectedCategories.size)
    // Every selected budget needs a valid amount before saving.
    val canSave = selectionCount > 0 && drafts.size == selectionCount && drafts.all { it.amount > BigDecimal.ZERO }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Budgets") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
            ) {
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

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = rollover, onCheckedChange = { rollover = it })
                    Text("Rollover unused amount")
                }

                HorizontalDivider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Select categories", style = MaterialTheme.typography.labelMedium)
                    TextButton(onClick = onCreateCategory) { Text("+ Create New") }
                }

                BudgetPickRow(
                    label = "Overall (All Categories)",
                    checked = overallSelected,
                    onCheckedChange = { overallSelected = it }
                )
                if (overallSelected) {
                    AmountField(amounts[OVERALL_KEY].orEmpty(), "Overall amount") {
                        amounts = amounts + (OVERALL_KEY to it)
                    }
                }

                categories.forEach { category ->
                    val checked = category.id in selectedIds
                    BudgetPickRow(
                        label = category.name,
                        checked = checked,
                        onCheckedChange = {
                            selectedIds = if (it) selectedIds + category.id else selectedIds - category.id
                        }
                    )
                    // Per-category amount, unless these are being clubbed under one shared limit.
                    if (checked && !clubbing) {
                        AmountField(amounts[category.id].orEmpty(), "${category.name} amount") {
                            amounts = amounts + (category.id to it)
                        }
                    }
                }

                if (selectedCategories.size >= 2) {
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Club into one budget", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "Share a single limit across the ${selectedCategories.size} selected categories",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(checked = club, onCheckedChange = { club = it })
                    }
                    if (clubbing) {
                        OutlinedTextField(
                            value = clubName,
                            onValueChange = { clubName = it },
                            label = { Text("Budget name (optional)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        AmountField(clubAmount, "Combined amount") { clubAmount = it }
                    }
                }

                overlapWarnings.forEach { warning ->
                    WarningNote(warning)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(drafts, period, rollover) },
                enabled = canSave
            ) {
                Text(if (drafts.size > 1) "Save ${drafts.size} budgets" else "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun WarningNote(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            Icons.Default.Warning,
            contentDescription = "Warning",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun BudgetPickRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(label, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun AmountField(value: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp)
    )
}

// Category ids are positive autoGenerate values, so a negative key can't collide with one.
private const val OVERALL_KEY = -1L
