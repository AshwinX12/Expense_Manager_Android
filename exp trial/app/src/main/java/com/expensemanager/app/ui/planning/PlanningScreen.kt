package com.expensemanager.app.ui.planning

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.expensemanager.app.ui.planning.AddBudgetDialog
import com.expensemanager.app.ui.planning.BudgetViewModel
import com.expensemanager.app.ui.planning.GoalViewModel
import com.expensemanager.app.ui.planning.ScheduledViewModel
import com.expensemanager.app.ui.components.*
import com.expensemanager.app.ui.navigation.LocalNavReselectEvent
import com.expensemanager.app.ui.navigation.Screen
import com.expensemanager.app.ui.theme.*
import com.expensemanager.app.util.formatCurrency
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanningScreen(
    budgetViewModel: BudgetViewModel = hiltViewModel(),
    goalViewModel: GoalViewModel = hiltViewModel(),
    scheduledViewModel: ScheduledViewModel = hiltViewModel()
) {
    val budgetState by budgetViewModel.uiState.collectAsStateWithLifecycle()
    val goalState by goalViewModel.uiState.collectAsStateWithLifecycle()
    val scheduledState by scheduledViewModel.uiState.collectAsStateWithLifecycle()

    var showAddBudgetDialog by remember { mutableStateOf(false) }
    var showCreateCategorySheet by remember { mutableStateOf(false) }
    var showAddGoalDialog by remember { mutableStateOf(false) }
    var showTransferDialog by remember { mutableStateOf<com.expensemanager.app.data.db.entity.GoalEntity?>(null) }
    var showAddScheduledDialog by remember { mutableStateOf(false) }

    var budgetsExpanded by remember { mutableStateOf(true) }
    var goalsExpanded by remember { mutableStateOf(false) }
    var scheduledExpanded by remember { mutableStateOf(false) }
    
    val listState = rememberLazyListState()
    val reselectEvent = LocalNavReselectEvent.current

    LaunchedEffect(reselectEvent) {
        reselectEvent.collect { route ->
            if (route == Screen.Planning.route) {
                listState.animateScrollToItem(0)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planning", fontWeight = FontWeight.SemiBold) },
                windowInsets = WindowInsets(0.dp),
                colors = flatTopAppBarColors()
            )
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(bottom = Dimens.SpacingHuge + Dimens.BottomBarHeight),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMd)
        ) {
            // Budgets Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.ScreenPadding),
                    shape = CardShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { budgetsExpanded = !budgetsExpanded }
                                .padding(Dimens.SpacingMd),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.PieChart, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(Dimens.SpacingSm))
                                Text("Budgets", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { showAddBudgetDialog = true }) {
                                    Icon(Icons.Default.Add, "Add Budget")
                                }
                                Icon(
                                    imageVector = if (budgetsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = "Expand"
                                )
                            }
                        }

                        AnimatedVisibility(visible = budgetsExpanded) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                if (budgetState.budgets.isEmpty() && !budgetState.isLoading) {
                                    Text(
                                        "No budgets set.",
                                        modifier = Modifier.padding(Dimens.SpacingMd),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                } else {
                                    budgetState.budgets.forEach { budgetStatus ->
                                        val categoryName = if (budgetStatus.budget.categoryId != null) {
                                            budgetState.categories.find { it.id == budgetStatus.budget.categoryId }?.name ?: "Unknown"
                                        } else "Overall"

                                        Column(modifier = Modifier.padding(horizontal = Dimens.SpacingMd, vertical = Dimens.SpacingSm)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column {
                                                    Text(categoryName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                                                    Text(
                                                        budgetStatus.budget.period.name.lowercase().replaceFirstChar { it.uppercase() },
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                                IconButton(onClick = { budgetViewModel.deleteBudget(budgetStatus.budget.id) }) {
                                                    Icon(Icons.Default.Delete, "Delete", modifier = Modifier.size(18.dp))
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(Dimens.SpacingSm))
                                            BudgetProgressBar(
                                                spent = budgetStatus.spent,
                                                budget = budgetStatus.budget.amount,
                                                percentage = budgetStatus.percentage
                                            )
                                            Spacer(modifier = Modifier.height(Dimens.SpacingXs))
                                            Text(
                                                text = if (budgetStatus.isOverBudget)
                                                    "${budgetStatus.remaining.abs().formatCurrency()} over budget"
                                                else "${budgetStatus.remaining.formatCurrency()} remaining",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = if (budgetStatus.isOverBudget) BudgetDanger
                                                else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        HorizontalDivider()
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Goals Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.ScreenPadding),
                    shape = CardShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { goalsExpanded = !goalsExpanded }
                                .padding(Dimens.SpacingMd),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Savings, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(Dimens.SpacingSm))
                                Text("Savings Goals", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { showAddGoalDialog = true }) {
                                    Icon(Icons.Default.Add, "Add Goal")
                                }
                                Icon(
                                    imageVector = if (goalsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = "Expand"
                                )
                            }
                        }

                        AnimatedVisibility(visible = goalsExpanded) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                if (goalState.goals.isEmpty() && !goalState.isLoading) {
                                    Text(
                                        "No savings goals.",
                                        modifier = Modifier.padding(Dimens.SpacingMd),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                } else {
                                    goalState.goals.forEach { goal ->
                                        val progress = if (goal.targetAmount > BigDecimal.ZERO)
                                            goal.currentAmount.multiply(BigDecimal(100)).divide(goal.targetAmount, 0, java.math.RoundingMode.HALF_UP).toInt()
                                        else 0

                                        Column(modifier = Modifier.padding(horizontal = Dimens.SpacingMd, vertical = Dimens.SpacingSm)) {
                                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                                Column {
                                                    Text(goal.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                                                    if (goal.isCompleted) {
                                                        Text("🎉 Goal Completed!", style = MaterialTheme.typography.labelSmall, color = BudgetGood)
                                                    }
                                                }
                                                Row {
                                                    IconButton(onClick = { showTransferDialog = goal }) {
                                                        Icon(Icons.Default.Add, "Transfer", Modifier.size(18.dp))
                                                    }
                                                    IconButton(onClick = { goalViewModel.deleteGoal(goal) }) {
                                                        Icon(Icons.Default.Delete, "Delete", Modifier.size(18.dp))
                                                    }
                                                }
                                            }
                                            Spacer(Modifier.height(Dimens.SpacingSm))
                                            BudgetProgressBar(spent = goal.currentAmount, budget = goal.targetAmount, percentage = progress)
                                            Spacer(Modifier.height(Dimens.SpacingXs))
                                            Text(
                                                "${goal.currentAmount.formatCurrency()} of ${goal.targetAmount.formatCurrency()}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        HorizontalDivider()
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Scheduled Transactions Section
            item {
                var editingRule by remember { mutableStateOf<com.expensemanager.app.data.db.entity.RecurringRuleEntity?>(null) }

                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.ScreenPadding),
                    shape = CardShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { scheduledExpanded = !scheduledExpanded }
                                .padding(Dimens.SpacingMd),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Schedule, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(Dimens.SpacingSm))
                                Text("Scheduled Transactions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { showAddScheduledDialog = true }) {
                                    Icon(Icons.Default.Add, "Add Scheduled")
                                }
                                Icon(
                                    imageVector = if (scheduledExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = "Expand"
                                )
                            }
                        }

                        AnimatedVisibility(visible = scheduledExpanded) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                if (scheduledState.rules.isEmpty() && !scheduledState.isLoading) {
                                    Text(
                                        "No scheduled transactions.",
                                        modifier = Modifier.padding(Dimens.SpacingMd),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                } else {
                                    scheduledState.rules.forEach { rule ->
                                        Column(
                                            modifier = Modifier
                                                .clickable { editingRule = rule }
                                                .padding(horizontal = Dimens.SpacingMd, vertical = Dimens.SpacingSm)
                                        ) {
                                            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                                                Column {
                                                    val catName = scheduledState.categories.find { it.id == rule.categoryId }?.name
                                                    Text(catName ?: "Scheduled", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)

                                                    val accountName = scheduledState.accounts.find { it.id == rule.accountId }?.name
                                                    Text(
                                                        "Account: ${accountName ?: "Not set ⚠️"}",
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = if (accountName != null) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.error
                                                    )

                                                    val timeStr = rule.time?.let { " at ${it.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))}" } ?: ""
                                                    Text("Next: ${rule.nextOccurrence}$timeStr", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                                }
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(
                                                        rule.amount.formatCurrency(),
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (rule.type == com.expensemanager.app.data.db.entity.TransactionType.INCOME) BudgetGood else BudgetDanger
                                                    )
                                                    Spacer(modifier = Modifier.width(Dimens.SpacingSm))
                                                    IconButton(onClick = { scheduledViewModel.deleteRule(rule) }) {
                                                        Icon(Icons.Default.Delete, "Delete", Modifier.size(18.dp))
                                                    }
                                                }
                                            }
                                        }
                                        HorizontalDivider()
                                    }
                                }
                            }
                        }
                    }
                }

                // Edit account dialog for scheduled rule
                editingRule?.let { rule ->
                    var selectedAccountId by remember(rule.id) { mutableStateOf(rule.accountId ?: scheduledState.accounts.firstOrNull { it.isDefault }?.id ?: scheduledState.accounts.firstOrNull()?.id) }
                    var accountDropdownExpanded by remember { mutableStateOf(false) }

                    AlertDialog(
                        onDismissRequest = { editingRule = null },
                        title = { Text("Edit Scheduled Transaction") },
                        text = {
                            Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                                val catName = scheduledState.categories.find { it.id == rule.categoryId }?.name ?: "Scheduled"
                                Text("$catName · ${rule.amount.formatCurrency()}", style = MaterialTheme.typography.bodyMedium)

                                ExposedDropdownMenuBox(
                                    expanded = accountDropdownExpanded,
                                    onExpandedChange = { accountDropdownExpanded = !accountDropdownExpanded }
                                ) {
                                    val accountName = scheduledState.accounts.find { it.id == selectedAccountId }?.name ?: "Select Account"
                                    OutlinedTextField(
                                        value = accountName,
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text("Account") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = accountDropdownExpanded) },
                                        modifier = Modifier.menuAnchor().fillMaxWidth()
                                    )
                                    ExposedDropdownMenu(
                                        expanded = accountDropdownExpanded,
                                        onDismissRequest = { accountDropdownExpanded = false }
                                    ) {
                                        scheduledState.accounts.forEach { account ->
                                            DropdownMenuItem(
                                                text = { Text(account.name) },
                                                onClick = {
                                                    selectedAccountId = account.id
                                                    accountDropdownExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                selectedAccountId?.let { accId ->
                                    scheduledViewModel.updateRuleAccount(rule, accId)
                                }
                                editingRule = null
                            }) { Text("Save") }
                        },
                        dismissButton = {
                            TextButton(onClick = { editingRule = null }) { Text("Cancel") }
                        }
                    )
                }
            }
        }
    }

    if (showAddBudgetDialog) {
        AddBudgetDialog(
            categories = budgetState.categories,
            onDismiss = { showAddBudgetDialog = false },
            onSave = { categoryId, amount, period, rollover ->
                budgetViewModel.addBudget(categoryId, amount, period, rollover)
                showAddBudgetDialog = false
            },
            onCreateCategory = { showCreateCategorySheet = true }
        )
    }

    if (showCreateCategorySheet) {
        CreateCategorySheet(
            onDismiss = { showCreateCategorySheet = false },
            onSave = { name, color ->
                budgetViewModel.addCategory(name, color)
                showCreateCategorySheet = false
            }
        )
    }

    if (showAddGoalDialog) {
        var name by remember { mutableStateOf("") }
        var target by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddGoalDialog = false },
            title = { Text("Add Goal") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                    OutlinedTextField(name, { name = it }, label = { Text("Goal Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(target, { target = it }, label = { Text("Target Amount") }, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(onClick = {
                    target.toBigDecimalOrNull()?.let { goalViewModel.addGoal(name, it); showAddGoalDialog = false }
                }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { showAddGoalDialog = false }) { Text("Cancel") } }
        )
    }

    showTransferDialog?.let { goal ->
        var amount by remember { mutableStateOf("") }
        var selectedAccountId by remember { mutableStateOf<Long?>(goalState.accounts.firstOrNull()?.id) }
        var accountDropdownExpanded by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showTransferDialog = null },
            title = { Text("Transfer to ${goal.name}") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)) {
                    OutlinedTextField(amount, { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth())

                    ExposedDropdownMenuBox(
                        expanded = accountDropdownExpanded,
                        onExpandedChange = { accountDropdownExpanded = !accountDropdownExpanded }
                    ) {
                        val accountName = goalState.accounts.find { it.id == selectedAccountId }?.name ?: "Select Account"
                        OutlinedTextField(
                            value = accountName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Source Account") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = accountDropdownExpanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = accountDropdownExpanded,
                            onDismissRequest = { accountDropdownExpanded = false }
                        ) {
                            goalState.accounts.forEach { account ->
                                DropdownMenuItem(
                                    text = { Text(account.name) },
                                    onClick = {
                                        selectedAccountId = account.id
                                        accountDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    amount.toBigDecimalOrNull()?.let { amt ->
                        selectedAccountId?.let { accId ->
                            goalViewModel.transferToGoal(goal.id, amt, accId)
                            showTransferDialog = null
                        }
                    }
                }, enabled = selectedAccountId != null) { Text("Transfer") }
            },
            dismissButton = { TextButton(onClick = { showTransferDialog = null }) { Text("Cancel") } }
        )
    }

    if (showAddScheduledDialog) {
        AddScheduledDialog(
            categories = scheduledState.categories,
            accounts = scheduledState.accounts,
            onDismiss = { showAddScheduledDialog = false },
            onSave = { amount, type, freq, nextDate, time, categoryId, accountId ->
                scheduledViewModel.addRule(amount, type, freq, nextDate, time, categoryId, accountId)
                showAddScheduledDialog = false
            }
        )
    }
}
