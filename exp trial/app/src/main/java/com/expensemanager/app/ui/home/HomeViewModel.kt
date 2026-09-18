package com.expensemanager.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.dao.AccountWithBalance
import com.expensemanager.app.data.db.entity.QuickAddShortcutEntity
import com.expensemanager.app.data.db.entity.TransactionEntity
import com.expensemanager.app.data.db.entity.TransactionType
import com.expensemanager.app.data.repository.*
import com.expensemanager.app.domain.model.BudgetStatus
import com.expensemanager.app.domain.model.TransactionWithDetails
import com.expensemanager.app.data.db.entity.CategoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

import androidx.compose.runtime.Immutable

@Immutable
data class HomeUiState(
    val totalBalance: BigDecimal = BigDecimal.ZERO,
    val perDaySpendable: BigDecimal = BigDecimal.ZERO,
    val todaySpend: BigDecimal = BigDecimal.ZERO,
    val monthSpend: BigDecimal = BigDecimal.ZERO,
    val monthIncome: BigDecimal = BigDecimal.ZERO,
    val overallBudgetStatus: BudgetStatus? = null,
    val recentTransactions: List<TransactionWithDetails> = emptyList(),
    val quickAddShortcuts: List<QuickAddShortcutEntity> = emptyList(),
    val accounts: List<AccountWithBalance> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository,
    private val quickAddShortcutDao: com.expensemanager.app.data.db.dao.QuickAddShortcutDao,
    private val recurringTransactionRepository: com.expensemanager.app.data.repository.RecurringTransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            recurringTransactionRepository.fixOrphanedTransactions()
            recurringTransactionRepository.generateDueTransactions()
        }
        loadDashboard()
    }

    private fun loadDashboard() {
        val today = LocalDate.now()
        val monthStart = today.withDayOfMonth(1)
        val monthEnd = today.with(TemporalAdjusters.lastDayOfMonth())

        // Today's spend
        viewModelScope.launch {
            transactionRepository.getDailyExpenseFlow(today).collect { todaySpend ->
                _uiState.update { it.copy(todaySpend = todaySpend) }
            }
        }

        // Month expense
        viewModelScope.launch {
            transactionRepository.getTotalExpenseFlow(monthStart, monthEnd).collect { monthSpend ->
                _uiState.update { it.copy(monthSpend = monthSpend) }
            }
        }

        // Month income
        viewModelScope.launch {
            transactionRepository.getTotalIncomeFlow(monthStart, monthEnd).collect { monthIncome ->
                _uiState.update { it.copy(monthIncome = monthIncome) }
            }
        }

        // Recent transactions
        viewModelScope.launch {
            combine(
                transactionRepository.getRecentFlow(10),
                categoryRepository.getAllCategoriesFlow(),
                accountRepository.getAllFlow()
            ) { transactions, categories, accounts ->
                val categoryMap = categories.associateBy { it.id }
                val accountMap = accounts.associateBy { it.id }
                transactions.map { tx ->
                    val category = tx.categoryId?.let { categoryMap[it] }
                    val account = tx.accountId?.let { accountMap[it] }
                    TransactionWithDetails(
                        transaction = tx,
                        categoryName = category?.name,
                        categoryIcon = category?.iconName,
                        categoryColor = category?.colorHex,
                        accountName = account?.name
                    )
                }
            }.collect { enriched ->
                _uiState.update { it.copy(recentTransactions = enriched, isLoading = false) }
            }
        }

        // Accounts with balance
        val daysLeftInMonth = (monthEnd.dayOfMonth - today.dayOfMonth + 1).coerceAtLeast(1)
        viewModelScope.launch {
            accountRepository.getAllWithBalanceFlow().collect { accounts ->
                val totalBalance = accounts.sumOf { it.balance }
                val perDaySpendable = totalBalance.divide(
                    BigDecimal(daysLeftInMonth), 2, java.math.RoundingMode.DOWN
                )
                _uiState.update {
                    it.copy(accounts = accounts, totalBalance = totalBalance, perDaySpendable = perDaySpendable)
                }
            }
        }

        // Quick-add shortcuts
        viewModelScope.launch {
            quickAddShortcutDao.getAllFlow().collect { shortcuts ->
                _uiState.update { it.copy(quickAddShortcuts = shortcuts) }
            }
        }

        // Overall budget status
        viewModelScope.launch {
            budgetRepository.getOverallBudgetFlow().collect { budget ->
                if (budget != null) {
                    val status = budgetRepository.calculateBudgetStatus(budget)
                    _uiState.update { it.copy(overallBudgetStatus = status) }
                }
            }
        }
    }

    fun executeQuickAdd(shortcut: QuickAddShortcutEntity) {
        viewModelScope.launch {
            val transaction = TransactionEntity(
                amount = shortcut.amount,
                type = TransactionType.EXPENSE,
                date = LocalDate.now(),
                categoryId = shortcut.categoryId,
                accountId = shortcut.accountId
            )
            transactionRepository.insert(transaction)
        }
    }

    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            transactionRepository.deleteById(id)
        }
    }

    fun duplicateTransaction(transactionDetail: com.expensemanager.app.domain.model.TransactionWithDetails) {
        viewModelScope.launch {
            val newTx = transactionDetail.transaction.copy(
                id = 0, 
                date = java.time.LocalDate.now(),
                time = java.time.LocalTime.now(),
                createdAt = java.time.LocalDateTime.now(),
                updatedAt = java.time.LocalDateTime.now()
            )
            transactionRepository.insert(newTx)
        }
    }
}
