package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.SplitExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SplitExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(split: SplitExpenseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(splits: List<SplitExpenseEntity>)

    @Update
    suspend fun update(split: SplitExpenseEntity)

    @Delete
    suspend fun delete(split: SplitExpenseEntity)

    @Query("SELECT * FROM split_expenses WHERE transactionId = :transactionId")
    fun getByTransactionFlow(transactionId: Long): Flow<List<SplitExpenseEntity>>

    @Query("SELECT * FROM split_expenses WHERE transactionId = :transactionId")
    suspend fun getByTransaction(transactionId: Long): List<SplitExpenseEntity>

    @Query("DELETE FROM split_expenses WHERE transactionId = :transactionId")
    suspend fun deleteByTransaction(transactionId: Long)

    @Query("UPDATE split_expenses SET isSettled = :settled WHERE id = :id")
    suspend fun setSettled(id: Long, settled: Boolean)
}
