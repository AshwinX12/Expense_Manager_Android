package com.expensemanager.app.ui.planning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.entity.RecurringFrequency
import com.expensemanager.app.data.db.entity.RecurringRuleEntity
import com.expensemanager.app.data.db.entity.TransactionType
import com.expensemanager.app.data.repository.AccountRepository
import com.expensemanager.app.data.repository.RecurringTransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import com.expensemanager.app.data.db.entity.AccountEntity
import com.expensemanager.app.data.db.entity.CategoryEntity
import com.expensemanager.app.data.repository.CategoryRepository

data class ScheduledUiState(
    val rules: List<RecurringRuleEntity> = emptyList(),
    val categories: List<CategoryEntity> = emptyList(),
    val accounts: List<AccountEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class ScheduledViewModel @Inject constructor(
    private val recurringTransactionRepository: RecurringTransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduledUiState())
    val uiState: StateFlow<ScheduledUiState> = _uiState.asStateFlow()

    init {
        // Fix any orphaned transactions with null accountId, then generate due transactions
        viewModelScope.launch {
            recurringTransactionRepository.fixOrphanedTransactions()
            recurringTransactionRepository.generateDueTransactions()
        }
        viewModelScope.launch {
            combine(
                recurringTransactionRepository.getAllRulesFlow(),
                categoryRepository.getAllCategoriesFlow(),
                accountRepository.getAllFlow()
            ) { rules, categories, accounts ->
                _uiState.update { it.copy(rules = rules, categories = categories, accounts = accounts, isLoading = false) }
            }.collect()
        }
    }

    fun addRule(amount: BigDecimal, type: TransactionType, frequency: RecurringFrequency, nextOccurrence: LocalDate, time: LocalTime?, categoryId: Long?, accountId: Long?) {
        viewModelScope.launch {
            // Fall back to the default account if none selected
            val resolvedAccountId = accountId ?: accountRepository.getDefault()?.id ?: _uiState.value.accounts.firstOrNull()?.id
            android.util.Log.d("ScheduledVM", "addRule called: amount=$amount, type=$type, freq=$frequency, date=$nextOccurrence, time=$time, categoryId=$categoryId, accountId=$resolvedAccountId")
            val rule = RecurringRuleEntity(
                    amount = amount,
                    type = type,
                    frequency = frequency,
                    nextOccurrence = nextOccurrence,
                    time = time,
                    categoryId = categoryId,
                    accountId = resolvedAccountId
                )
            val insertedId = recurringTransactionRepository.insertRule(rule)
            android.util.Log.d("ScheduledVM", "Rule inserted with id=$insertedId, now calling generateDueTransactions")
            recurringTransactionRepository.generateDueTransactions()
            android.util.Log.d("ScheduledVM", "generateDueTransactions completed")
        }
    }

    fun updateRuleAccount(rule: RecurringRuleEntity, newAccountId: Long) {
        viewModelScope.launch {
            recurringTransactionRepository.updateRule(rule.copy(accountId = newAccountId))
        }
    }

    fun deleteRule(rule: RecurringRuleEntity) {
        viewModelScope.launch { recurringTransactionRepository.deleteRule(rule) }
    }
}
