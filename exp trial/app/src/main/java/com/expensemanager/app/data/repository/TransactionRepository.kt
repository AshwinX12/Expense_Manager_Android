package com.expensemanager.app.data.repository

import com.expensemanager.app.data.db.dao.AccountDao
import com.expensemanager.app.data.db.dao.TransactionDao
import com.expensemanager.app.data.db.entity.TransactionEntity
import com.expensemanager.app.data.db.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao
) {
    suspend fun insert(transaction: TransactionEntity): Long = transactionDao.insert(transaction)
    suspend fun update(transaction: TransactionEntity) = transactionDao.update(transaction)
    suspend fun delete(transaction: TransactionEntity) = transactionDao.delete(transaction)
    suspend fun deleteById(id: Long) = transactionDao.deleteById(id)
    suspend fun getById(id: Long) = transactionDao.getById(id)
    fun getByIdFlow(id: Long) = transactionDao.getByIdFlow(id)
    fun getAllFlow() = transactionDao.getAllFlow()
    fun getRecentFlow(limit: Int = 10) = transactionDao.getRecentFlow(limit)
    fun getByDateRangeFlow(start: LocalDate, end: LocalDate) = transactionDao.getByDateRangeFlow(start, end)
    suspend fun getByDateRange(start: LocalDate, end: LocalDate) = transactionDao.getByDateRange(start, end)
    fun getByCategoryFlow(categoryId: Long) = transactionDao.getByCategoryFlow(categoryId)
    fun getByAccountFlow(accountId: Long) = transactionDao.getByAccountFlow(accountId)
    fun getByTypeFlow(type: TransactionType) = transactionDao.getByTypeFlow(type)

    fun getTotalExpenseFlow(start: LocalDate, end: LocalDate) = transactionDao.getTotalExpenseFlow(start, end)
    fun getTotalIncomeFlow(start: LocalDate, end: LocalDate) = transactionDao.getTotalIncomeFlow(start, end)
    fun getDailyExpenseFlow(date: LocalDate) = transactionDao.getDailyExpenseFlow(date)
    fun getCategoryExpenseFlow(categoryId: Long, start: LocalDate, end: LocalDate) =
        transactionDao.getCategoryExpenseFlow(categoryId, start, end)
    suspend fun getCategoryExpense(categoryId: Long, start: LocalDate, end: LocalDate) =
        transactionDao.getCategoryExpense(categoryId, start, end)
    suspend fun getTotalExpense(start: LocalDate, end: LocalDate) = transactionDao.getTotalExpense(start, end)
    suspend fun getTotalIncome(start: LocalDate, end: LocalDate) = transactionDao.getTotalIncome(start, end)

    fun searchFlow(query: String) = transactionDao.searchFlow(query)
    fun getByAmountRangeFlow(min: BigDecimal, max: BigDecimal) = transactionDao.getByAmountRangeFlow(min, max)

    suspend fun getExpenseByCategoryRaw(start: LocalDate, end: LocalDate) =
        transactionDao.getExpenseByCategoryRaw(start, end)
    suspend fun getDailyExpenseTotals(start: LocalDate, end: LocalDate) =
        transactionDao.getDailyExpenseTotals(start, end)
    suspend fun getMonthlyTotalsByType(start: LocalDate, end: LocalDate) =
        transactionDao.getMonthlyTotalsByType(start, end)
    suspend fun getAll() = transactionDao.getAll()
    suspend fun getCount() = transactionDao.getCount()
}
