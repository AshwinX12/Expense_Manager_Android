package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.SettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setting: SettingsEntity)

    @Query("SELECT value FROM settings WHERE `key` = :key")
    suspend fun getValue(key: String): String?

    @Query("SELECT value FROM settings WHERE `key` = :key")
    fun getValueFlow(key: String): Flow<String?>

    @Query("SELECT * FROM settings")
    suspend fun getAll(): List<SettingsEntity>

    @Query("DELETE FROM settings WHERE `key` = :key")
    suspend fun delete(key: String)
}
