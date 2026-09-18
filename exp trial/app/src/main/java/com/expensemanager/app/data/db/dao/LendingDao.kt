package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.LendingEntity
import com.expensemanager.app.data.db.entity.LendingType
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface LendingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lending: LendingEntity): Long

    @Update
    suspend fun update(lending: LendingEntity)

    @Delete
    suspend fun delete(lending: LendingEntity)

    @Query("SELECT * FROM lending WHERE id = :id")
    suspend fun getById(id: Long): LendingEntity?

    @Query("SELECT * FROM lending ORDER BY status, date DESC")
    fun getAllFlow(): Flow<List<LendingEntity>>

    @Query("SELECT * FROM lending WHERE type = :type ORDER BY status, date DESC")
    fun getByTypeFlow(type: LendingType): Flow<List<LendingEntity>>

    @Query("SELECT * FROM lending WHERE personName = :name ORDER BY date DESC")
    fun getByPersonFlow(name: String): Flow<List<LendingEntity>>

    @Query("""
        SELECT COALESCE(SUM(amount - repaidAmount), 0) FROM lending 
        WHERE type = 'LENT' AND status != 'SETTLED'
    """)
    fun getTotalLentFlow(): Flow<BigDecimal>

    @Query("""
        SELECT COALESCE(SUM(amount - repaidAmount), 0) FROM lending 
        WHERE type = 'BORROWED' AND status != 'SETTLED'
    """)
    fun getTotalBorrowedFlow(): Flow<BigDecimal>

    @Query("SELECT DISTINCT personName FROM lending ORDER BY personName")
    suspend fun getAllPersonNames(): List<String>
}
