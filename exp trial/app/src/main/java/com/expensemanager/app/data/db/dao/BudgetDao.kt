package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: BudgetEntity): Long

    @Update
    suspend fun update(budget: BudgetEntity)

    @Delete
    suspend fun delete(budget: BudgetEntity)

    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getById(id: Long): BudgetEntity?

    @Query("SELECT * FROM budgets ORDER BY categoryId")
    fun getAllFlow(): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets ORDER BY categoryId")
    suspend fun getAll(): List<BudgetEntity>

    @Query("SELECT * FROM budgets WHERE categoryId IS NULL LIMIT 1")
    fun getOverallBudgetFlow(): Flow<BudgetEntity?>

    @Query("SELECT * FROM budgets WHERE categoryId IS NULL LIMIT 1")
    suspend fun getOverallBudget(): BudgetEntity?

    @Query("SELECT * FROM budgets WHERE categoryId = :categoryId LIMIT 1")
    suspend fun getByCategoryId(categoryId: Long): BudgetEntity?

    @Query("SELECT * FROM budgets WHERE categoryId = :categoryId LIMIT 1")
    fun getByCategoryIdFlow(categoryId: Long): Flow<BudgetEntity?>
}
