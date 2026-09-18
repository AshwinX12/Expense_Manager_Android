package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.RecurringRuleEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface RecurringRuleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rule: RecurringRuleEntity): Long

    @Update
    suspend fun update(rule: RecurringRuleEntity)

    @Delete
    suspend fun delete(rule: RecurringRuleEntity)

    @Query("SELECT * FROM recurring_rules WHERE id = :id")
    suspend fun getById(id: Long): RecurringRuleEntity?

    @Query("SELECT * FROM recurring_rules ORDER BY nextOccurrence")
    fun getAllFlow(): Flow<List<RecurringRuleEntity>>

    @Query("SELECT * FROM recurring_rules WHERE isActive = 1 AND nextOccurrence <= :date")
    suspend fun getDueRules(date: LocalDate): List<RecurringRuleEntity>

    @Query("SELECT * FROM recurring_rules WHERE isActive = 1 ORDER BY nextOccurrence")
    fun getActiveFlow(): Flow<List<RecurringRuleEntity>>

    @Query("UPDATE recurring_rules SET nextOccurrence = :nextDate WHERE id = :id")
    suspend fun updateNextOccurrence(id: Long, nextDate: LocalDate)

    @Query("UPDATE recurring_rules SET isActive = :active WHERE id = :id")
    suspend fun setActive(id: Long, active: Boolean)
}
