package com.expensemanager.app.data.repository

import com.expensemanager.app.data.db.dao.CategoryDao
import com.expensemanager.app.data.db.dao.SubcategoryDao
import com.expensemanager.app.data.db.entity.CategoryEntity
import com.expensemanager.app.data.db.entity.SubcategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val subcategoryDao: SubcategoryDao
) {
    // Categories
    suspend fun insertCategory(category: CategoryEntity) = categoryDao.insert(category)
    suspend fun updateCategory(category: CategoryEntity) = categoryDao.update(category)
    suspend fun deleteCategory(category: CategoryEntity) = categoryDao.delete(category)
    suspend fun getCategoryById(id: Long) = categoryDao.getById(id)
    fun getAllCategoriesFlow() = categoryDao.getAllFlow()
    suspend fun getAllCategories() = categoryDao.getAll()
    suspend fun getCategoryTransactionCount(id: Long) = categoryDao.getTransactionCount(id)

    // Subcategories
    suspend fun insertSubcategory(sub: SubcategoryEntity) = subcategoryDao.insert(sub)
    suspend fun updateSubcategory(sub: SubcategoryEntity) = subcategoryDao.update(sub)
    suspend fun deleteSubcategory(sub: SubcategoryEntity) = subcategoryDao.delete(sub)
    fun getSubcategoriesFlow(categoryId: Long) = subcategoryDao.getByCategoryFlow(categoryId)
    suspend fun getSubcategories(categoryId: Long) = subcategoryDao.getByCategory(categoryId)
    suspend fun getSubcategoryById(id: Long) = subcategoryDao.getById(id)
    suspend fun getAllSubcategories() = subcategoryDao.getAll()
}
