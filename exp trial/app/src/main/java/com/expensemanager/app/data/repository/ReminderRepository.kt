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

    // Each reminder fires on its own schedule — leadTimeDays before its dueDate — rather than
    // every reminder sharing one flat "7 days ahead" window regardless of what it was set to.
    suspend fun getDueReminders(): List<ReminderEntity> {
        val today = LocalDate.now()
        return reminderDao.getPendingReminders().filter { reminder ->
            !reminder.dueDate.minusDays(reminder.leadTimeDays.toLong()).isAfter(today)
        }
    }

    suspend fun markNotified(id: Long) = reminderDao.markNotified(id)
    suspend fun resetNotified(id: Long) = reminderDao.resetNotified(id)
}
