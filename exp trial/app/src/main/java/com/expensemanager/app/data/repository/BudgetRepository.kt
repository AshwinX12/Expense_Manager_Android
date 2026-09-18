package com.expensemanager.app.data.repository

import com.expensemanager.app.data.db.dao.BudgetDao
import com.expensemanager.app.data.db.dao.TransactionDao
import com.expensemanager.app.data.db.entity.BudgetEntity
import com.expensemanager.app.data.db.entity.BudgetPeriod
import com.expensemanager.app.domain.model.BudgetStatus
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao,
    private val transactionDao: TransactionDao
) {
    suspend fun insert(budget: BudgetEntity) = budgetDao.insert(budget)
    suspend fun update(budget: BudgetEntity) = budgetDao.update(budget)
    suspend fun delete(budget: BudgetEntity) = budgetDao.delete(budget)
    suspend fun getById(id: Long) = budgetDao.getById(id)
    fun getAllFlow() = budgetDao.getAllFlow()
    suspend fun getAll() = budgetDao.getAll()
    fun getOverallBudgetFlow() = budgetDao.getOverallBudgetFlow()
    suspend fun getOverallBudget() = budgetDao.getOverallBudget()
    suspend fun getByCategoryId(categoryId: Long) = budgetDao.getByCategoryId(categoryId)

    suspend fun calculateBudgetStatus(budget: BudgetEntity): BudgetStatus {
        val (startDate, endDate) = getPeriodDates(budget.period)
        val spent = if (budget.categoryId != null) {
            transactionDao.getCategoryExpense(budget.categoryId, startDate, endDate)
        } else {
            transactionDao.getTotalExpense(startDate, endDate)
        }
        val effectiveBudget = budget.amount + (if (budget.rolloverEnabled) budget.rolloverAmount else BigDecimal.ZERO)
        val percentage = if (effectiveBudget > BigDecimal.ZERO) {
            spent.multiply(BigDecimal(100)).divide(effectiveBudget, 0, java.math.RoundingMode.HALF_UP).toInt()
        } else 0

        return BudgetStatus(
            budget = budget,
            spent = spent,
            remaining = effectiveBudget - spent,
            percentage = percentage
        )
    }

    fun getPeriodDates(period: BudgetPeriod): Pair<LocalDate, LocalDate> {
        val today = LocalDate.now()
        return when (period) {
            BudgetPeriod.WEEKLY -> {
                val start = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                val end = start.plusDays(6)
                start to end
            }
            BudgetPeriod.MONTHLY -> {
                val start = today.withDayOfMonth(1)
                val end = today.with(TemporalAdjusters.lastDayOfMonth())
                start to end
            }
        }
    }
}
