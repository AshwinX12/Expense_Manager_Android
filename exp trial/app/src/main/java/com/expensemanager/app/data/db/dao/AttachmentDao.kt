package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.AttachmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttachmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attachment: AttachmentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(attachments: List<AttachmentEntity>)

    @Delete
    suspend fun delete(attachment: AttachmentEntity)

    @Query("DELETE FROM attachments WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM attachments WHERE transactionId = :transactionId ORDER BY createdAt")
    fun getByTransactionFlow(transactionId: Long): Flow<List<AttachmentEntity>>

    @Query("SELECT * FROM attachments WHERE transactionId = :transactionId ORDER BY createdAt")
    suspend fun getByTransaction(transactionId: Long): List<AttachmentEntity>

    @Query("SELECT * FROM attachments WHERE id = :id")
    suspend fun getById(id: Long): AttachmentEntity?

    @Query("DELETE FROM attachments WHERE transactionId = :transactionId")
    suspend fun deleteByTransaction(transactionId: Long)

    @Query("SELECT * FROM attachments")
    suspend fun getAll(): List<AttachmentEntity>
}
