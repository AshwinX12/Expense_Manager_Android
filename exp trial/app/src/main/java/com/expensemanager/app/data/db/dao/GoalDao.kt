package com.expensemanager.app.data.db.dao

import androidx.room.*
import com.expensemanager.app.data.db.entity.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: GoalEntity): Long

    @Update
    suspend fun update(goal: GoalEntity)

    @Delete
    suspend fun delete(goal: GoalEntity)

    @Query("SELECT * FROM goals WHERE id = :id")
    suspend fun getById(id: Long): GoalEntity?

    @Query("SELECT * FROM goals ORDER BY isCompleted, name")
    fun getAllFlow(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals ORDER BY isCompleted, name")
    suspend fun getAll(): List<GoalEntity>

    @Query("SELECT * FROM goals WHERE isCompleted = 0 ORDER BY name")
    fun getActiveFlow(): Flow<List<GoalEntity>>

    @Query("UPDATE goals SET currentAmount = currentAmount + :amount WHERE id = :goalId")
    suspend fun addToGoal(goalId: Long, amount: java.math.BigDecimal)

    @Query("UPDATE goals SET isCompleted = 1 WHERE id = :goalId")
    suspend fun markCompleted(goalId: Long)
}
