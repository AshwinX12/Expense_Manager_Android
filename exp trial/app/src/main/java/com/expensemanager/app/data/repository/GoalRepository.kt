package com.expensemanager.app.data.repository

import com.expensemanager.app.data.db.dao.GoalDao
import com.expensemanager.app.data.db.entity.GoalEntity
import java.math.BigDecimal
import javax.inject.Inject

class GoalRepository @Inject constructor(
    private val goalDao: GoalDao
) {
    suspend fun insert(goal: GoalEntity) = goalDao.insert(goal)
    suspend fun update(goal: GoalEntity) = goalDao.update(goal)
    suspend fun delete(goal: GoalEntity) = goalDao.delete(goal)
    suspend fun getById(id: Long) = goalDao.getById(id)
    fun getAllFlow() = goalDao.getAllFlow()
    suspend fun getAll() = goalDao.getAll()
    fun getActiveFlow() = goalDao.getActiveFlow()

    suspend fun transferToGoal(goalId: Long, amount: BigDecimal) {
        goalDao.addToGoal(goalId, amount)
        val goal = goalDao.getById(goalId)
        if (goal != null && goal.currentAmount + amount >= goal.targetAmount) {
            goalDao.markCompleted(goalId)
        }
    }
}
