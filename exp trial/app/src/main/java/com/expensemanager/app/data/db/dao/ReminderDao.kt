package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: ReminderEntity): Long

    @Update
    suspend fun update(reminder: ReminderEntity)

    @Delete
    suspend fun delete(reminder: ReminderEntity)

    @Query("SELECT * FROM reminders WHERE id = :id")
    suspend fun getById(id: Long): ReminderEntity?

    @Query("SELECT * FROM reminders WHERE isActive = 1 ORDER BY dueDate")
    fun getActiveFlow(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders ORDER BY dueDate")
    fun getAllFlow(): Flow<List<ReminderEntity>>

    // Filtered in Kotlin (repository), not SQL: each reminder has its own leadTimeDays,
    // so "is this due yet" can't be expressed as a single WHERE clause against one checkDate.
    @Query("SELECT * FROM reminders WHERE isActive = 1 AND isNotified = 0")
    suspend fun getPendingReminders(): List<ReminderEntity>

    @Query("UPDATE reminders SET isNotified = 1 WHERE id = :id")
    suspend fun markNotified(id: Long)

    @Query("UPDATE reminders SET isNotified = 0 WHERE id = :id")
    suspend fun resetNotified(id: Long)
}
