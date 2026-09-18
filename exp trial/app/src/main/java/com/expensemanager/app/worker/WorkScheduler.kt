package com.expensemanager.app.worker

import android.content.Context
import androidx.work.*
import com.expensemanager.app.util.Constants
import java.util.concurrent.TimeUnit

object WorkScheduler {

    fun scheduleAll(context: Context) {
        scheduleRecurringTransactions(context)
        scheduleBudgetCheck(context)
        scheduleReminderCheck(context)
        scheduleWeeklySummary(context)
    }

    private fun scheduleRecurringTransactions(context: Context) {
        val request = PeriodicWorkRequestBuilder<RecurringTransactionWorker>(
            1, TimeUnit.DAYS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            Constants.WORK_RECURRING,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    private fun scheduleBudgetCheck(context: Context) {
        val request = PeriodicWorkRequestBuilder<BudgetCheckWorker>(
            6, TimeUnit.HOURS
        )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            Constants.WORK_BUDGET_CHECK,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    private fun scheduleReminderCheck(context: Context) {
        val request = PeriodicWorkRequestBuilder<ReminderCheckWorker>(
            12, TimeUnit.HOURS
        )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            Constants.WORK_REMINDER_CHECK,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    private fun scheduleWeeklySummary(context: Context) {
        val request = PeriodicWorkRequestBuilder<WeeklySummaryWorker>(
            7, TimeUnit.DAYS
        )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            Constants.WORK_WEEKLY_SUMMARY,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
