package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.SavedFilterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedFilterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filter: SavedFilterEntity): Long

    @Update
    suspend fun update(filter: SavedFilterEntity)

    @Delete
    suspend fun delete(filter: SavedFilterEntity)

    @Query("SELECT * FROM saved_filters ORDER BY name")
    fun getAllFlow(): Flow<List<SavedFilterEntity>>

    @Query("SELECT * FROM saved_filters WHERE id = :id")
    suspend fun getById(id: Long): SavedFilterEntity?
}
