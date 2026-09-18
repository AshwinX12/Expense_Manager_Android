package com.expensemanager.app.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensemanager.app.data.db.entity.*
import com.expensemanager.app.data.repository.*
import com.expensemanager.app.domain.model.FilterCriteria
import com.expensemanager.app.domain.model.TransactionWithDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import androidx.compose.runtime.Immutable
import javax.inject.Inject

@Immutable
data class TransactionsUiState(
    val transactions: List<TransactionWithDetails> = emptyList(),
    val categories: List<CategoryEntity> = emptyList(),
    val accounts: List<AccountEntity> = emptyList(),
    val searchQuery: String = "",
    val filter: FilterCriteria = FilterCriteria(),
    val sortBy: SortBy = SortBy.DATE_DESC,
    val isLoading: Boolean = true
)

enum class SortBy { DATE_DESC, DATE_ASC, AMOUNT_DESC, AMOUNT_ASC, CATEGORY }

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionsUiState())
    val uiState: StateFlow<TransactionsUiState> = _uiState.asStateFlow()

    private var allCategories: Map<Long, CategoryEntity> = emptyMap()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            kotlinx.coroutines.flow.combine(
                transactionRepository.getAllFlow(),
                categoryRepository.getAllCategoriesFlow(),
                accountRepository.getAllFlow()
            ) { transactions, categories, accounts ->
                val categoryMap = categories.associateBy { it.id }
                val accountMap = accounts.associateBy { it.id }

                val enriched = transactions.map { tx ->
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

                Triple(enriched, categories, accounts)
            }.collect { (enriched, categories, accounts) ->
                _uiState.update {
                    it.copy(
                        transactions = enriched,
                        categories = categories,
                        accounts = accounts,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setFilter(filter: FilterCriteria) {
        _uiState.update { it.copy(filter = filter) }
    }

    fun setSortBy(sortBy: SortBy) {
        _uiState.update { it.copy(sortBy = sortBy) }
    }

    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            transactionRepository.deleteById(id)
        }
    }

    fun duplicateTransaction(transactionDetail: TransactionWithDetails) {
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

    fun getFilteredTransactions(): List<TransactionWithDetails> {
        val state = _uiState.value
        var result = state.transactions

        // Search filter
        if (state.searchQuery.isNotBlank()) {
            val query = state.searchQuery.lowercase()
            result = result.filter {
                it.transaction.note?.lowercase()?.contains(query) == true ||
                it.transaction.merchantName?.lowercase()?.contains(query) == true ||
                it.categoryName?.lowercase()?.contains(query) == true ||
                it.transaction.amount.toPlainString().contains(query)
            }
        }

        // Category filter
        if (state.filter.categoryIds.isNotEmpty()) {
            result = result.filter { it.transaction.categoryId in state.filter.categoryIds }
        }

        // Account filter
        if (state.filter.accountIds.isNotEmpty()) {
            result = result.filter { it.transaction.accountId in state.filter.accountIds }
        }

        // Type filter
        if (state.filter.transactionTypes.isNotEmpty()) {
            result = result.filter { it.transaction.type in state.filter.transactionTypes }
        }

        // Date range filter
        state.filter.startDate?.let { start ->
            result = result.filter { !it.transaction.date.isBefore(start) }
        }
        state.filter.endDate?.let { end ->
            result = result.filter { !it.transaction.date.isAfter(end) }
        }

        // Amount range filter
        state.filter.minAmount?.let { min ->
            result = result.filter { it.transaction.amount >= min }
        }
        state.filter.maxAmount?.let { max ->
            result = result.filter { it.transaction.amount <= max }
        }

        // Sort
        result = when (state.sortBy) {
            SortBy.DATE_DESC -> result.sortedByDescending { it.transaction.date }
            SortBy.DATE_ASC -> result.sortedBy { it.transaction.date }
            SortBy.AMOUNT_DESC -> result.sortedByDescending { it.transaction.amount }
            SortBy.AMOUNT_ASC -> result.sortedBy { it.transaction.amount }
            SortBy.CATEGORY -> result.sortedBy { it.categoryName ?: "" }
        }

        return result
    }
}
