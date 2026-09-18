package com.expensemanager.app.ui.planning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.entity.*
import com.expensemanager.app.data.repository.*
import com.expensemanager.app.domain.model.BudgetStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

data class BudgetsUiState(
    val budgets: List<BudgetStatus> = emptyList(),
    val categories: List<CategoryEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BudgetsUiState())
    val uiState: StateFlow<BudgetsUiState> = _uiState.asStateFlow()

    init {
        loadBudgets()
    }

    private fun loadBudgets() {
        viewModelScope.launch {
            categoryRepository.getAllCategoriesFlow().collect { cats ->
                _uiState.update { it.copy(categories = cats) }
            }
        }
        viewModelScope.launch {
            combine(
                budgetRepository.getAllFlow(),
                transactionRepository.getAllFlow()
            ) { budgets, _ ->
                budgets.map { budget ->
                    budgetRepository.calculateBudgetStatus(budget)
                }
            }.collect { statuses ->
                _uiState.update { it.copy(budgets = statuses, isLoading = false) }
            }
        }
    }

    fun addBudget(categoryId: Long?, amount: BigDecimal, period: BudgetPeriod, rollover: Boolean) {
        viewModelScope.launch {
            budgetRepository.insert(
                BudgetEntity(
                    categoryId = categoryId,
                    amount = amount,
                    period = period,
                    rolloverEnabled = rollover,
                    startDate = LocalDate.now()
                )
            )
        }
    }

    fun deleteBudget(budgetId: Long) {
        viewModelScope.launch {
            budgetRepository.getById(budgetId)?.let { budgetRepository.delete(it) }
        }
    }

    fun addCategory(name: String, colorHex: String) {
        viewModelScope.launch {
            categoryRepository.insertCategory(CategoryEntity(name = name, colorHex = colorHex))
        }
    }
}
