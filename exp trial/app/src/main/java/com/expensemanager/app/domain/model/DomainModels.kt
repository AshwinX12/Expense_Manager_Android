package com.expensemanager.app.domain.model

import com.expensemanager.app.data.db.entity.BudgetEntity
import java.math.BigDecimal
import androidx.compose.runtime.Immutable

@Immutable
data class BudgetStatus(
    val budget: BudgetEntity,
    val spent: BigDecimal,
    val remaining: BigDecimal,
    val percentage: Int // 0-100+
) {
    val isOverBudget: Boolean get() = percentage > 100
    val isApproachingLimit: Boolean get() = percentage in 80..100
    val isUnderBudget: Boolean get() = percentage < 80
}

@Immutable
data class DashboardData(
    val totalBalance: BigDecimal = BigDecimal.ZERO,
    val todaySpend: BigDecimal = BigDecimal.ZERO,
    val monthSpend: BigDecimal = BigDecimal.ZERO,
    val monthIncome: BigDecimal = BigDecimal.ZERO,
    val overallBudgetStatus: BudgetStatus? = null,
    val recentTransactions: List<com.expensemanager.app.data.db.entity.TransactionEntity> = emptyList(),
    val quickAddShortcuts: List<com.expensemanager.app.data.db.entity.QuickAddShortcutEntity> = emptyList()
)

@Immutable
data class FilterCriteria(
    val startDate: java.time.LocalDate? = null,
    val endDate: java.time.LocalDate? = null,
    val categoryIds: List<Long> = emptyList(),
    val accountIds: List<Long> = emptyList(),
    val tagIds: List<Long> = emptyList(),
    val transactionTypes: List<com.expensemanager.app.data.db.entity.TransactionType> = emptyList(),
    val paymentMethods: List<com.expensemanager.app.data.db.entity.PaymentMethod> = emptyList(),
    val minAmount: BigDecimal? = null,
    val maxAmount: BigDecimal? = null,
    val searchQuery: String? = null
) {
    val isActive: Boolean get() = startDate != null || endDate != null ||
        categoryIds.isNotEmpty() || accountIds.isNotEmpty() || tagIds.isNotEmpty() ||
        transactionTypes.isNotEmpty() || paymentMethods.isNotEmpty() ||
        minAmount != null || maxAmount != null || !searchQuery.isNullOrBlank()
}

@Immutable
data class TransactionWithDetails(
    val transaction: com.expensemanager.app.data.db.entity.TransactionEntity,
    val categoryName: String? = null,
    val categoryIcon: String? = null,
    val categoryColor: String? = null,
    val subcategoryName: String? = null,
    val accountName: String? = null,
    val tags: List<com.expensemanager.app.data.db.entity.TagEntity> = emptyList(),
    val attachmentCount: Int = 0
)
