package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.TransactionEntity
import com.expensemanager.app.data.db.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.time.LocalDate

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity): Long

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getById(id: Long): TransactionEntity?

    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getByIdFlow(id: Long): Flow<TransactionEntity?>

    @Query("SELECT * FROM transactions ORDER BY date DESC, createdAt DESC")
    fun getAllFlow(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions ORDER BY date DESC, createdAt DESC")
    suspend fun getAll(): List<TransactionEntity>

    @Query("SELECT * FROM transactions ORDER BY date DESC, createdAt DESC LIMIT :limit")
    fun getRecentFlow(limit: Int): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getByDateRangeFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getByDateRange(startDate: LocalDate, endDate: LocalDate): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE categoryId = :categoryId ORDER BY date DESC")
    fun getByCategoryFlow(categoryId: Long): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE accountId = :accountId ORDER BY date DESC")
    fun getByAccountFlow(accountId: Long): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY date DESC")
    fun getByTypeFlow(type: TransactionType): Flow<List<TransactionEntity>>

    // Aggregations
    @Query("""
        SELECT COALESCE(SUM(CASE WHEN type = 'INCOME' THEN amount ELSE 0 END), 0) -
               COALESCE(SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END), 0) -
               COALESCE(SUM(CASE WHEN type = 'TRANSFER_TO_GOAL' THEN amount ELSE 0 END), 0) +
               COALESCE(SUM(CASE WHEN type = 'DEBT_TRANSFER_IN' THEN amount ELSE 0 END), 0) -
               COALESCE(SUM(CASE WHEN type = 'DEBT_TRANSFER_OUT' THEN amount ELSE 0 END), 0)
        FROM transactions WHERE accountId = :accountId
    """)
    fun getAccountBalanceFlow(accountId: Long): Flow<BigDecimal>

    @Query("""
        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        WHERE type IN ('EXPENSE', 'TRANSFER_TO_GOAL', 'DEBT_TRANSFER_OUT') AND date BETWEEN :startDate AND :endDate
    """)
    fun getTotalExpenseFlow(startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal>

    @Query("""
        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        WHERE type = 'INCOME' AND date BETWEEN :startDate AND :endDate
    """)
    fun getTotalIncomeFlow(startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal>

    @Query("""
        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        WHERE type IN ('EXPENSE', 'TRANSFER_TO_GOAL', 'DEBT_TRANSFER_OUT') AND date = :date
    """)
    fun getDailyExpenseFlow(date: LocalDate): Flow<BigDecimal>

    @Query("""
        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        WHERE type = 'EXPENSE' AND categoryId = :categoryId 
        AND date BETWEEN :startDate AND :endDate
    """)
    fun getCategoryExpenseFlow(categoryId: Long, startDate: LocalDate, endDate: LocalDate): Flow<BigDecimal>

    @Query("""
        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        WHERE type = 'EXPENSE' AND categoryId = :categoryId 
        AND date BETWEEN :startDate AND :endDate
    """)
    suspend fun getCategoryExpense(categoryId: Long, startDate: LocalDate, endDate: LocalDate): BigDecimal

    @Query("""
        SELECT COALESCE(SUM(amount), 0) FROM transactions
        WHERE type IN ('EXPENSE', 'TRANSFER_TO_GOAL', 'DEBT_TRANSFER_OUT') AND date BETWEEN :startDate AND :endDate
    """)
    suspend fun getTotalExpense(startDate: LocalDate, endDate: LocalDate): BigDecimal

    @Query("""
        SELECT COALESCE(SUM(amount), 0) FROM transactions
        WHERE type = 'INCOME' AND date BETWEEN :startDate AND :endDate
    """)
    suspend fun getTotalIncome(startDate: LocalDate, endDate: LocalDate): BigDecimal

    // Search
    @Query("""
        SELECT * FROM transactions 
        WHERE (note LIKE '%' || :query || '%' OR merchantName LIKE '%' || :query || '%')
        ORDER BY date DESC
    """)
    fun searchFlow(query: String): Flow<List<TransactionEntity>>

    @Query("""
        SELECT * FROM transactions 
        WHERE amount BETWEEN :minAmount AND :maxAmount 
        ORDER BY date DESC
    """)
    fun getByAmountRangeFlow(minAmount: BigDecimal, maxAmount: BigDecimal): Flow<List<TransactionEntity>>

    // Category-wise aggregation for charts
    @Query("""
        SELECT categoryId, COALESCE(SUM(amount), 0) as total 
        FROM transactions 
        WHERE type IN ('EXPENSE', 'TRANSFER_TO_GOAL', 'DEBT_TRANSFER_OUT') AND date BETWEEN :startDate AND :endDate 
        GROUP BY categoryId
    """)
    suspend fun getExpenseByCategoryRaw(startDate: LocalDate, endDate: LocalDate): List<CategoryTotal>

    // Daily totals for trend charts
    @Query("""
        SELECT date, COALESCE(SUM(amount), 0) as total 
        FROM transactions 
        WHERE type IN ('EXPENSE', 'TRANSFER_TO_GOAL', 'DEBT_TRANSFER_OUT') AND date BETWEEN :startDate AND :endDate 
        GROUP BY date ORDER BY date
    """)
    suspend fun getDailyExpenseTotals(startDate: LocalDate, endDate: LocalDate): List<DateTotal>

    // Monthly totals
    @Query("""
        SELECT date, type, COALESCE(SUM(amount), 0) as total 
        FROM transactions 
        WHERE date BETWEEN :startDate AND :endDate 
        GROUP BY strftime('%Y-%m', date), type ORDER BY date
    """)
    suspend fun getMonthlyTotalsByType(startDate: LocalDate, endDate: LocalDate): List<DateTypeTotal>

    @Query("SELECT COUNT(*) FROM transactions")
    suspend fun getCount(): Int

    @Query("UPDATE transactions SET accountId = :defaultAccountId WHERE accountId IS NULL")
    suspend fun fixNullAccountTransactions(defaultAccountId: Long): Int

    @Query("UPDATE transactions SET accountId = :accountId WHERE recurringRuleId = :ruleId")
    suspend fun updateAccountIdForRecurringRule(ruleId: Long, accountId: Long): Int
}

data class CategoryTotal(
    val categoryId: Long?,
    val total: BigDecimal
)

data class DateTotal(
    val date: LocalDate,
    val total: BigDecimal
)

data class DateTypeTotal(
    val date: LocalDate,
    val type: TransactionType,
    val total: BigDecimal
)
