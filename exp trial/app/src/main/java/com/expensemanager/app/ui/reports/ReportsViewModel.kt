package com.expensemanager.app.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.dao.CategoryTotal
import com.expensemanager.app.data.db.dao.DateTotal
import com.expensemanager.app.data.db.entity.CategoryEntity
import com.expensemanager.app.data.repository.*
import com.expensemanager.app.domain.model.BudgetStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

data class ReportsUiState(
    val categoryBreakdown: List<Pair<String, BigDecimal>> = emptyList(),
    val categoryColors: List<String> = emptyList(),
    val dailyTrend: List<DateTotal> = emptyList(),
    val monthlyIncome: BigDecimal = BigDecimal.ZERO,
    val monthlyExpense: BigDecimal = BigDecimal.ZERO,
    val budgetStatuses: List<BudgetStatus> = emptyList(),
    val totalSpent: BigDecimal = BigDecimal.ZERO,
    val totalIncome: BigDecimal = BigDecimal.ZERO,
    val startDate: LocalDate = LocalDate.now().withDayOfMonth(1),
    val endDate: LocalDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()),
    // Always "this calendar week" (Monday through today), independent of the date range
    // picked above — matches what the weekly-summary notification reports.
    val weeklyTotalSpent: BigDecimal = BigDecimal.ZERO,
    val weeklyTransactionCount: Int = 0,
    val weeklyHighestSpendDay: DateTotal? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val budgetRepository: BudgetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    private var categoriesMap: Map<Long, CategoryEntity> = emptyMap()

    init {
        loadReports()
        // Reports previously only reloaded on init or when the date range changed, so an
        // edited/added transaction or a changed budget elsewhere in the app (Reports stays
        // alive in the swipe pager, so it wasn't even recreated on tab switch) wouldn't show
        // up until something happened to force a reload — read as a "delayed update" bug.
        viewModelScope.launch {
            combine(
                transactionRepository.getAllFlow(),
                budgetRepository.getAllFlow()
            ) { _, _ -> Unit }.drop(1).collect { loadReports() }
        }
    }

    fun loadReports() {
        val state = _uiState.value
        viewModelScope.launch {
            // Category breakdown
            // Fetch categories here rather than relying on a separate init-time load: that load
            // ran concurrently, so the first report often built before the map was populated and
            // every row fell back to "Uncategorized". It also never saw categories added later.
            categoriesMap = categoryRepository.getAllCategories().associateBy { it.id }
            val categoryTotals = transactionRepository.getExpenseByCategoryRaw(state.startDate, state.endDate)
                .sortedByDescending { it.total }
            // Names and colors come from the same sorted list so each color matches its row.
            val breakdown = categoryTotals.map { ct ->
                val name = ct.categoryId?.let { categoriesMap[it]?.name } ?: "Uncategorized"
                name to ct.total
            }
            val colors = categoryTotals.map { ct ->
                ct.categoryId?.let { categoriesMap[it]?.colorHex } ?: "#AEB6BF"
            }

            // Daily trend
            val dailyTrend = transactionRepository.getDailyExpenseTotals(state.startDate, state.endDate)

            // Totals
            val totalSpent = transactionRepository.getTotalExpense(state.startDate, state.endDate)
            val totalIncome = transactionRepository.getTotalIncome(state.startDate, state.endDate)

            // Budget statuses
            val budgets = budgetRepository.getAll()
            val statuses = budgets.map { budgetRepository.calculateBudgetStatus(it) }

            // This week, always — same window as the weekly-summary notification
            // (previousOrSame, not previous: today itself counts when today is a Monday)
            val today = LocalDate.now()
            val weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            val weeklyTotalSpent = transactionRepository.getTotalExpense(weekStart, today)
            val weeklyTransactionCount = transactionRepository.getByDateRange(weekStart, today).size
            val weeklyHighestSpendDay = transactionRepository.getDailyExpenseTotals(weekStart, today)
                .maxByOrNull { it.total }

            _uiState.update {
                it.copy(
                    categoryBreakdown = breakdown,
                    categoryColors = colors,
                    dailyTrend = dailyTrend,
                    totalSpent = totalSpent,
                    totalIncome = totalIncome,
                    budgetStatuses = statuses,
                    weeklyTotalSpent = weeklyTotalSpent,
                    weeklyTransactionCount = weeklyTransactionCount,
                    weeklyHighestSpendDay = weeklyHighestSpendDay,
                    isLoading = false
                )
            }
        }
    }

    fun setDateRange(start: LocalDate, end: LocalDate) {
        _uiState.update { it.copy(startDate = start, endDate = end) }
        loadReports()
    }
}
