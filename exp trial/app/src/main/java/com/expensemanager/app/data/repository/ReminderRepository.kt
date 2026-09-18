package com.expensemanager.app.data.repository

import com.expensemanager.app.data.db.dao.ReminderDao
import com.expensemanager.app.data.db.entity.ReminderEntity
import java.time.LocalDate
import javax.inject.Inject

class ReminderRepository @Inject constructor(
    private val reminderDao: ReminderDao
) {
    suspend fun insert(reminder: ReminderEntity) = reminderDao.insert(reminder)
    suspend fun update(reminder: ReminderEntity) = reminderDao.update(reminder)
    suspend fun delete(reminder: ReminderEntity) = reminderDao.delete(reminder)
    suspend fun getById(id: Long) = reminderDao.getById(id)
    fun getActiveFlow() = reminderDao.getActiveFlow()
    fun getAllFlow() = reminderDao.getAllFlow()

    suspend fun getDueReminders(): List<ReminderEntity> {
        val checkDate = LocalDate.now().plusDays(7) // Check 7 days ahead
        return reminderDao.getDueReminders(checkDate)
    }

    suspend fun markNotified(id: Long) = reminderDao.markNotified(id)
}
