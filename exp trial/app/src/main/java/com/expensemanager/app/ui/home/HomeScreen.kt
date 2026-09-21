package com.expensemanager.app.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.expensemanager.app.ui.components.*
import com.expensemanager.app.ui.planning.AddScheduledDialog
import com.expensemanager.app.ui.planning.ScheduledViewModel
import com.expensemanager.app.ui.navigation.LocalNavReselectEvent
import com.expensemanager.app.ui.navigation.Screen
import com.expensemanager.app.ui.theme.*
import com.expensemanager.app.util.formatCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToTransactions: () -> Unit,
    onNavigateToAdd: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToReports: () -> Unit,
    onTransactionClick: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    scheduledViewModel: ScheduledViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scheduledState by scheduledViewModel.uiState.collectAsStateWithLifecycle()
    var selectedTransactionForMenu by remember { mutableStateOf<com.expensemanager.app.domain.model.TransactionWithDetails?>(null) }
    var showAddScheduledDialogFor by remember { mutableStateOf<com.expensemanager.app.domain.model.TransactionWithDetails?>(null) }
    val listState = rememberLazyListState()
    val reselectEvent = LocalNavReselectEvent.current

    LaunchedEffect(reselectEvent) {
        reselectEvent.collect { route ->
            if (route == Screen.Home.route) {
                listState.animateScrollToItem(0)
            }
        }
    }

    val groupedTransactions = remember(state.recentTransactions) {
        state.recentTransactions.groupBy { it.transaction.date }.toSortedMap(compareByDescending { it })
    }

    val themeStyle = LocalThemeStyle.current
    Box(modifier = Modifier.fillMaxSize()) {
    if (themeStyle == com.expensemanager.app.data.db.entity.ThemeStyle.MEMPHIS) {
        MemphisBackdrop(isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f)
    }
    if (themeStyle == com.expensemanager.app.data.db.entity.ThemeStyle.AURORA) {
        AuroraBackdrop(isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f)
    }
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = Dimens.SpacingHuge + Dimens.BottomBarHeight),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingLg)
    ) {
        // Top bar
        item(key = "header", contentType = "header") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.ScreenPadding, vertical = Dimens.SpacingMd),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Expense Manager",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onNavigateToSettings) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        }

        // Balance card
        item(key = "balance", contentType = "balance_card") {
            // In dark mode the accent-filled card is a huge block of color that makes the
            // whole screen read as tinted, so the paper/print themes use a dark surface
            // there and let the accent show up only on the balance figure itself.
            val flatDarkCard = LocalIsDarkTheme.current && (
                themeStyle == com.expensemanager.app.data.db.entity.ThemeStyle.WARM_PAPER ||
                    themeStyle == com.expensemanager.app.data.db.entity.ThemeStyle.EDITORIAL
                )
            // Aurora's whole identity is a frosted-glass card over the gradient backdrop
            // drawn behind this LazyColumn — a translucent surface here lets the blobs
            // show through, in both light and dark.
            val isAurora = themeStyle == com.expensemanager.app.data.db.entity.ThemeStyle.AURORA
            val balanceContainer = when {
                isAurora -> MaterialTheme.colorScheme.surface.copy(alpha = if (LocalIsDarkTheme.current) 0.35f else 0.55f)
                flatDarkCard -> MaterialTheme.colorScheme.surfaceContainerHigh
                else -> MaterialTheme.colorScheme.primary
            }
            val balanceContent = if (isAurora || flatDarkCard) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.onPrimary
            val balanceFigure = if (isAurora || flatDarkCard) MaterialTheme.colorScheme.primary else balanceContent

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.ScreenPadding),
                shape = CardShape,
                colors = CardDefaults.cardColors(
                    containerColor = balanceContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(Dimens.SpacingXl)
                ) {
                    var showAccountPicker by remember { mutableStateOf(false) }
                    val selectedAccountName = state.accounts.firstOrNull { it.id == state.selectedAccountId }?.name

                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { showAccountPicker = true }
                        ) {
                            Text(
                                text = selectedAccountName?.let { "$it Balance" } ?: "Total Balance",
                                style = MaterialTheme.typography.bodyMedium,
                                color = balanceContent.copy(alpha = 0.8f)
                            )
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Choose account",
                                tint = balanceContent.copy(alpha = 0.8f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        DropdownMenu(
                            expanded = showAccountPicker,
                            onDismissRequest = { showAccountPicker = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("All Accounts") },
                                onClick = { viewModel.setHomeAccount(null); showAccountPicker = false },
                                leadingIcon = {
                                    if (state.selectedAccountId == null) Icon(Icons.Default.Check, null)
                                }
                            )
                            state.accounts.forEach { account ->
                                DropdownMenuItem(
                                    text = { Text(account.name) },
                                    onClick = { viewModel.setHomeAccount(account.id); showAccountPicker = false },
                                    leadingIcon = {
                                        if (state.selectedAccountId == account.id) Icon(Icons.Default.Check, null)
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(Dimens.SpacingXs))
                    Text(
                        text = state.totalBalance.formatCurrency(),
                        style = MaterialTheme.typography.displayMedium,
                        color = balanceFigure,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpacingSm))
                    Text(
                        text = "You can spend ${state.perDaySpendable.formatCurrency()} per day this month",
                        style = MaterialTheme.typography.labelMedium,
                        color = balanceContent.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpacingLg))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Income",
                                style = MaterialTheme.typography.labelSmall,
                                color = balanceContent.copy(alpha = 0.7f)
                            )
                            Text(
                                text = "+${state.monthIncome.formatCurrency()}",
                                style = MaterialTheme.typography.titleSmall,
                                color = balanceContent,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Expense",
                                style = MaterialTheme.typography.labelSmall,
                                color = balanceContent.copy(alpha = 0.7f)
                            )
                            Text(
                                text = "-${state.monthSpend.formatCurrency()}",
                                style = MaterialTheme.typography.titleSmall,
                                color = balanceContent,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // Budget progress
        if (state.overallBudgetStatus != null) {
            item(key = "budget", contentType = "budget_card") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.ScreenPadding),
                    shape = CardShape,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation)
                ) {
                    Column(modifier = Modifier.padding(Dimens.CardPadding)) {
                        Text(
                            text = "Monthly Budget",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(Dimens.SpacingSm))
                        BudgetProgressBar(
                            spent = state.overallBudgetStatus!!.spent,
                            budget = state.overallBudgetStatus!!.budget.amount,
                            percentage = state.overallBudgetStatus!!.percentage
                        )
                    }
                }
            }
        }

        // Quick-add shortcuts
        if (state.quickAddShortcuts.isNotEmpty()) {
            item(key = "quick_add", contentType = "quick_add") {
                Column(modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)) {
                    Text(
                        text = "Quick Add",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpacingSm))
                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm),
                    contentPadding = PaddingValues(horizontal = Dimens.ScreenPadding)
                ) {
                    items(state.quickAddShortcuts, key = { it.id }) { shortcut ->
                        AssistChip(
                            onClick = { viewModel.executeQuickAdd(shortcut) },
                            label = {
                                Text("${shortcut.label} · ${shortcut.amount.formatCurrency()}")
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Bolt,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            shape = ChipShape
                        )
                    }
                }
            }
        }

        // Today's spend highlight
        item(key = "today_spend", contentType = "today_card") {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.ScreenPadding)
                    .clickable { onNavigateToReports() },
                shape = CardShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.CardPadding),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Today's Spend",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = state.todaySpend.formatCurrency(),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = ExpenseRed
                        )
                    }
                    Icon(
                        Icons.Default.TrendingUp,
                        contentDescription = "View trends",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Recent transactions
        item(key = "recent_header", contentType = "section_header") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.ScreenPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                TextButton(onClick = onNavigateToTransactions) {
                    Text("See All")
                }
            }
        }

        if (state.recentTransactions.isEmpty() && !state.isLoading) {
            item(key = "empty_state", contentType = "empty_state") {
                EmptyStateView(
                    icon = {
                        Icon(
                            Icons.Default.ReceiptLong,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    title = "No transactions yet",
                    message = "Tap + to add your first transaction",
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                )
            }
        }



        groupedTransactions.forEach { (date, dailyTransactions) ->
            item(key = "group_${date.toEpochDay()}", contentType = "transaction_group") {
                com.expensemanager.app.ui.components.TransactionGroupCard(
                    date = date,
                    transactions = dailyTransactions,
                    onTransactionClick = { onTransactionClick(it) },
                    onTransactionLongClick = { selectedTransactionForMenu = it },
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                )
            }
        }
    }
    }

    selectedTransactionForMenu?.let { txDetail ->
        TransactionContextMenuSheet(
            transaction = txDetail,
            onDismiss = { selectedTransactionForMenu = null },
            onDuplicate = {
                viewModel.duplicateTransaction(txDetail)
                selectedTransactionForMenu = null
            },
            onDelete = {
                viewModel.deleteTransaction(txDetail.transaction.id)
            },
            onSchedule = {
                showAddScheduledDialogFor = txDetail
                selectedTransactionForMenu = null
            }
        )
    }

    showAddScheduledDialogFor?.let { txDetail ->
        AddScheduledDialog(
            initialAmount = txDetail.transaction.amount.toPlainString(),
            initialAccountId = txDetail.transaction.accountId,
            categories = scheduledState.categories,
            accounts = scheduledState.accounts,
            onDismiss = { showAddScheduledDialogFor = null },
            onSave = { amount, type, freq, nextDate, time, categoryId, accountId ->
                scheduledViewModel.addRule(amount, type, freq, nextDate, time, categoryId, accountId)
                showAddScheduledDialogFor = null
            }
        )
    }
}
