package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.TagEntity
import com.expensemanager.app.data.db.entity.TransactionTagCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tag: TagEntity): Long

    @Update
    suspend fun update(tag: TagEntity)

    @Delete
    suspend fun delete(tag: TagEntity)

    @Query("SELECT * FROM tags ORDER BY name")
    fun getAllFlow(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tags ORDER BY name")
    suspend fun getAll(): List<TagEntity>

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun getById(id: Long): TagEntity?

    // Cross-ref operations
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRef(crossRef: TransactionTagCrossRef)

    @Delete
    suspend fun deleteCrossRef(crossRef: TransactionTagCrossRef)

    @Query("DELETE FROM transaction_tag_cross_ref WHERE transactionId = :transactionId")
    suspend fun deleteAllCrossRefsForTransaction(transactionId: Long)

    @Query("""
        SELECT t.* FROM tags t 
        INNER JOIN transaction_tag_cross_ref cr ON t.id = cr.tagId 
        WHERE cr.transactionId = :transactionId
    """)
    suspend fun getTagsForTransaction(transactionId: Long): List<TagEntity>

    @Query("""
        SELECT t.* FROM tags t 
        INNER JOIN transaction_tag_cross_ref cr ON t.id = cr.tagId 
        WHERE cr.transactionId = :transactionId
    """)
    fun getTagsForTransactionFlow(transactionId: Long): Flow<List<TagEntity>>
}
