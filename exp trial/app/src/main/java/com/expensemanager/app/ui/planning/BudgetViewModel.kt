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

/** One budget to create. Empty [categoryIds] = overall; more than one = clubbed budget. */
data class BudgetDraft(
    val categoryIds: List<Long>,
    val name: String?,
    val amount: BigDecimal
)

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

    /** Creates every draft in one go; a draft with several categories becomes one clubbed budget. */
    fun addBudgets(drafts: List<BudgetDraft>, period: BudgetPeriod, rollover: Boolean) {
        viewModelScope.launch {
            drafts.forEach { draft ->
                budgetRepository.insert(
                    BudgetEntity(
                        categoryId = draft.categoryIds.firstOrNull(),
                        categoryIds = if (draft.categoryIds.size > 1) draft.categoryIds else emptyList(),
                        name = draft.name?.takeIf { it.isNotBlank() },
                        amount = draft.amount,
                        period = period,
                        rolloverEnabled = rollover,
                        startDate = LocalDate.now()
                    )
                )
            }
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
