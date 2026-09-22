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
        try {
            val dueReminders = reminderRepository.getDueReminders()
            for (reminder in dueReminders) {
                NotificationHelper.showReminderNotification(
                    applicationContext,
                    "📅 ${reminder.title}",
                    reminder.description ?: "Due on ${reminder.dueDate.formatDisplay()}",
                    REMINDER_NOTIFICATION_ID_BASE + reminder.id.toInt()
                )
                reminderRepository.markNotified(reminder.id)
            }
            // Chain tomorrow's 9am check — only on success/final-failure, not before a retry;
            // see BudgetCheckWorker for why scheduling ahead of a retry would cancel it instead
            // of letting WorkManager's own backoff run.
            WorkScheduler.scheduleReminderCheck(applicationContext)
            return Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) return Result.retry()
            WorkScheduler.scheduleReminderCheck(applicationContext)
            return Result.failure()
        }
    }

    companion object {
        // Offset from BudgetCheckWorker's own ID base so the two notification types can never
        // collide even though both key off a small integer entity id.
        private const val REMINDER_NOTIFICATION_ID_BASE = 20_000
    }
}
