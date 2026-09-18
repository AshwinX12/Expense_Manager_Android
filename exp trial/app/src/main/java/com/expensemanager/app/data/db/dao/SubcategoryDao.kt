package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.SubcategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubcategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subcategory: SubcategoryEntity): Long

    @Update
    suspend fun update(subcategory: SubcategoryEntity)

    @Delete
    suspend fun delete(subcategory: SubcategoryEntity)

    @Query("SELECT * FROM subcategories WHERE parentCategoryId = :categoryId ORDER BY name")
    fun getByCategoryFlow(categoryId: Long): Flow<List<SubcategoryEntity>>

    @Query("SELECT * FROM subcategories WHERE parentCategoryId = :categoryId ORDER BY name")
    suspend fun getByCategory(categoryId: Long): List<SubcategoryEntity>

    @Query("SELECT * FROM subcategories WHERE id = :id")
    suspend fun getById(id: Long): SubcategoryEntity?

    @Query("SELECT * FROM subcategories ORDER BY name")
    suspend fun getAll(): List<SubcategoryEntity>
}
