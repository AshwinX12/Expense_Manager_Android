package com.expensemanager.app.ui.reports

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.expensemanager.app.ui.components.*
import com.expensemanager.app.ui.theme.*
import com.expensemanager.app.util.formatCurrency
import com.expensemanager.app.util.formatDisplay
import com.expensemanager.app.util.formatMonthYear
import java.math.BigDecimal
import androidx.compose.foundation.background
import com.expensemanager.app.ui.navigation.LocalNavReselectEvent
import com.expensemanager.app.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    viewModel: ReportsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val reselectEvent = LocalNavReselectEvent.current

    LaunchedEffect(reselectEvent) {
        reselectEvent.collect { route ->
            if (route == Screen.Reports.route) {
                listState.animateScrollToItem(0)
            }
        }
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
        // Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.ScreenPadding, vertical = Dimens.SpacingMd),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Reports", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text(
                    "${state.startDate.formatMonthYear()}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Summary cards
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.ScreenPadding),
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSm)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = CardShape,
                    colors = CardDefaults.cardColors(containerColor = ExpenseRed.copy(alpha = 0.1f))
                ) {
                    Column(Modifier.padding(Dimens.CardPaddingSmall)) {
                        Text("Total Spent", style = MaterialTheme.typography.labelSmall, color = ExpenseRed)
                        Text(
                            state.totalSpent.formatCurrency(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold, color = ExpenseRed
                        )
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    shape = CardShape,
                    colors = CardDefaults.cardColors(containerColor = IncomeGreen.copy(alpha = 0.1f))
                ) {
                    Column(Modifier.padding(Dimens.CardPaddingSmall)) {
                        Text("Total Income", style = MaterialTheme.typography.labelSmall, color = IncomeGreen)
                        Text(
                            state.totalIncome.formatCurrency(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold, color = IncomeGreen
                        )
                    }
                }
            }
        }

        // Weekly summary — same window as the weekly-summary notification, so this is
        // where that data lives once you've read (or missed) the notification itself
        item {
            ChartCard(
                title = "This Week",
                subtitle = "Monday through today",
                modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
            ) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Column {
                        Text("Spent", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            state.weeklyTotalSpent.formatCurrency(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold, color = ExpenseRed
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Transactions", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            "${state.weeklyTransactionCount}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                state.weeklyHighestSpendDay?.let { highest ->
                    Spacer(Modifier.height(Dimens.SpacingMd))
                    HorizontalDivider()
                    Spacer(Modifier.height(Dimens.SpacingSm))
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                        Text(
                            "Highest spend: ${highest.date.formatDisplay()}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            highest.total.formatCurrency(),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = ExpenseRed
                        )
                    }
                }
            }
        }

        // Category breakdown donut chart
        if (state.categoryBreakdown.isNotEmpty()) {
            item {
                ChartCard(
                    title = "Category Breakdown",
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                ) {
                    PieChart(
                        data = state.categoryBreakdown,
                        colors = state.categoryColors.map { it.toComposeColor() },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(Dimens.SpacingMd))
                    // Legend
                    state.categoryBreakdown.forEachIndexed { index, (name, amount) ->
                        Row(
                            Modifier.fillMaxWidth().padding(vertical = 2.dp),
                            Arrangement.SpaceBetween,
                            Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    Modifier
                                        .size(10.dp)
                                        .padding(end = 4.dp)
                                        .then(
                                            Modifier.background(
                                                state.categoryColors.getOrNull(index)?.toComposeColor()
                                                    ?: MaterialTheme.colorScheme.primary,
                                                MaterialTheme.shapes.extraSmall
                                            )
                                        )
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(name, style = MaterialTheme.typography.bodySmall)
                            }
                            Text(amount.formatCurrency(), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        // Spending trend line chart
        if (state.dailyTrend.isNotEmpty()) {
            item {
                ChartCard(
                    title = "Spending Trend",
                    subtitle = "Daily expenses this period",
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                ) {
                    LineChart(
                        data = state.dailyTrend.map { it.total.toFloat() },
                        lineColor = ExpenseRed,
                        modifier = Modifier.height(Dimens.ChartHeight)
                    )
                }
            }
        }

        // Budget vs Actual
        if (state.budgetStatuses.isNotEmpty()) {
            item {
                ChartCard(
                    title = "Budget vs Actual",
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                ) {
                    state.budgetStatuses.forEach { bs ->
                        Text(
                            if (bs.budget.categoryId != null) "Category Budget" else "Overall",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        BudgetProgressBar(
                            spent = bs.spent,
                            budget = bs.budget.amount,
                            percentage = bs.percentage,
                            height = 12
                        )
                        Spacer(Modifier.height(Dimens.SpacingSm))
                    }
                }
            }
        }

        // Top categories bar chart
        if (state.categoryBreakdown.isNotEmpty()) {
            item {
                ChartCard(
                    title = "Top Spending Categories",
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                ) {
                    val topCategories = state.categoryBreakdown.take(6)
                    BarChart(
                        data = topCategories.map { (name, amount) -> name to amount.toFloat() },
                        colors = state.categoryColors.take(6).map { it.toComposeColor() },
                        modifier = Modifier.height(Dimens.ChartHeight)
                    )
                    Spacer(Modifier.height(Dimens.SpacingSm))
                    topCategories.forEachIndexed { i, (name, amount) ->
                        val categoryColor = state.categoryColors.getOrNull(i)?.toComposeColor()
                            ?: MaterialTheme.colorScheme.onSurface
                        Row(
                            Modifier.fillMaxWidth(),
                            Arrangement.SpaceBetween,
                            Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    Modifier
                                        .size(10.dp)
                                        .background(categoryColor, MaterialTheme.shapes.extraSmall)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "${i + 1}. $name",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = categoryColor,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Text(amount.formatCurrency(), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
    }
}
