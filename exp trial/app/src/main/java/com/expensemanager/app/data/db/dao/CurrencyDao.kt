package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: CurrencyEntity)

    @Update
    suspend fun update(currency: CurrencyEntity)

    @Delete
    suspend fun delete(currency: CurrencyEntity)

    @Query("SELECT * FROM currencies ORDER BY code")
    fun getAllFlow(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies ORDER BY code")
    suspend fun getAll(): List<CurrencyEntity>

    @Query("SELECT * FROM currencies WHERE code = :code")
    suspend fun getByCode(code: String): CurrencyEntity?
}
