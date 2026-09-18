package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.QuickAddShortcutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuickAddShortcutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shortcut: QuickAddShortcutEntity): Long

    @Update
    suspend fun update(shortcut: QuickAddShortcutEntity)

    @Delete
    suspend fun delete(shortcut: QuickAddShortcutEntity)

    @Query("SELECT * FROM quick_add_shortcuts ORDER BY sortOrder")
    fun getAllFlow(): Flow<List<QuickAddShortcutEntity>>

    @Query("SELECT * FROM quick_add_shortcuts ORDER BY sortOrder")
    suspend fun getAll(): List<QuickAddShortcutEntity>

    @Query("SELECT * FROM quick_add_shortcuts WHERE id = :id")
    suspend fun getById(id: Long): QuickAddShortcutEntity?
}
