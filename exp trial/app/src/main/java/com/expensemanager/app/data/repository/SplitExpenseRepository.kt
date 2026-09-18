package com.expensemanager.app.data.repository

import com.expensemanager.app.data.db.dao.SplitExpenseDao
import com.expensemanager.app.data.db.entity.SplitExpenseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplitExpenseRepository @Inject constructor(
    private val dao: SplitExpenseDao
) {
    fun getByTransactionFlow(transactionId: Long): Flow<List<SplitExpenseEntity>> =
        dao.getByTransactionFlow(transactionId)

    suspend fun getByTransaction(transactionId: Long): List<SplitExpenseEntity> =
        dao.getByTransaction(transactionId)

    suspend fun insertAll(splits: List<SplitExpenseEntity>) =
        dao.insertAll(splits)

    suspend fun insert(split: SplitExpenseEntity): Long =
        dao.insert(split)

    suspend fun update(split: SplitExpenseEntity) =
        dao.update(split)

    suspend fun delete(split: SplitExpenseEntity) =
        dao.delete(split)

    suspend fun replaceForTransaction(transactionId: Long, splits: List<SplitExpenseEntity>) {
        // Delete existing splits for this transaction, then insert new ones
        dao.getByTransaction(transactionId).forEach { dao.delete(it) }
        dao.insertAll(splits.map { it.copy(transactionId = transactionId) })
    }
}
