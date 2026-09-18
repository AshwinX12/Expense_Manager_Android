package com.expensemanager.app.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.expensemanager.app.data.repository.ReminderRepository
import com.expensemanager.app.notification.NotificationHelper
import com.expensemanager.app.util.formatDisplay
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReminderCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val reminderRepository: ReminderRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val dueReminders = reminderRepository.getDueReminders()
            for (reminder in dueReminders) {
                NotificationHelper.showReminderNotification(
                    applicationContext,
                    "📅 ${reminder.title}",
                    reminder.description ?: "Due on ${reminder.dueDate.formatDisplay()}"
                )
                reminderRepository.markNotified(reminder.id)
            }
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }
}
