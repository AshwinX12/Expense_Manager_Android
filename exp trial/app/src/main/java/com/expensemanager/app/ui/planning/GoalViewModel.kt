package com.expensemanager.app.ui.planning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.entity.AccountEntity
import com.expensemanager.app.data.db.entity.GoalEntity
import com.expensemanager.app.data.db.entity.TransactionEntity
import com.expensemanager.app.data.db.entity.TransactionType
import com.expensemanager.app.data.repository.AccountRepository
import com.expensemanager.app.data.repository.GoalRepository
import com.expensemanager.app.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

data class GoalsUiState(
    val goals: List<GoalEntity> = emptyList(),
    val accounts: List<AccountEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GoalsUiState())
    val uiState: StateFlow<GoalsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            goalRepository.getAllFlow().collect { goals ->
                _uiState.update { it.copy(goals = goals, isLoading = false) }
            }
        }
        viewModelScope.launch {
            accountRepository.getAllFlow().collect { accounts ->
                _uiState.update { it.copy(accounts = accounts) }
            }
        }
    }

    fun addGoal(name: String, target: BigDecimal) {
        viewModelScope.launch {
            goalRepository.insert(GoalEntity(name = name, targetAmount = target))
        }
    }

    fun transferToGoal(goalId: Long, amount: BigDecimal, accountId: Long) {
        viewModelScope.launch { 
            goalRepository.transferToGoal(goalId, amount) 
            
            // Log as transaction to deduct balance
            val goal = goalRepository.getById(goalId)
            val tx = TransactionEntity(
                amount = amount,
                type = TransactionType.TRANSFER_TO_GOAL,
                date = java.time.LocalDate.now(),
                accountId = accountId,
                note = "Transfer to Goal: ${goal?.name}"
            )
            transactionRepository.insert(tx)
        }
    }

    fun deleteGoal(goal: GoalEntity) {
        viewModelScope.launch { goalRepository.delete(goal) }
    }
}
