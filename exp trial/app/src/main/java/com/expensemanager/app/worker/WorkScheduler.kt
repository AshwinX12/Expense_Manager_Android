package com.expensemanager.app.worker

import android.content.Context
import androidx.work.*
import com.expensemanager.app.util.Constants
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

object WorkScheduler {

    private const val DAILY_CHECK_HOUR = 9

    fun scheduleAll(context: Context) {
        scheduleRecurringTransactions(context)
        scheduleBudgetCheck(context)
        scheduleReminderCheck(context)
        scheduleWeeklySummary(context)
    }

    /** Milliseconds from now until the next occurrence of [DAILY_CHECK_HOUR]:00 today or tomorrow. */
    private fun delayUntilNextDailyCheck(): Long {
        val now = LocalDateTime.now()
        var nextRun = now.withHour(DAILY_CHECK_HOUR).withMinute(0).withSecond(0).withNano(0)
        if (!nextRun.isAfter(now)) nextRun = nextRun.plusDays(1)
        return Duration.between(now, nextRun).toMillis()
    }

    /**
     * Budget checks fire once a day at 9am, not on a fixed "every N hours" timer — that
     * used to re-fire every 6 hours for as long as a budget stayed over/near its limit,
     * which read as spam. This is a self-rescheduling one-time work chain (BudgetCheckWorker
     * calls this again after it runs) rather than PeriodicWorkRequest, because a periodic
     * request's time-of-day drifts over weeks; recomputing "next 9am" from the current time
     * on every run keeps it anchored instead of accumulating drift.
     */
    fun scheduleBudgetCheck(context: Context) {
        val request = OneTimeWorkRequestBuilder<BudgetCheckWorker>()
            .setInitialDelay(delayUntilNextDailyCheck(), TimeUnit.MILLISECONDS)
            .build()

        // REPLACE, not KEEP: existing installs already have the old "every 6 hours"
        // PeriodicWorkRequest registered under this same unique name, and that needs to be
        // torn down and replaced with this schedule, not left running alongside/instead of it.
        WorkManager.getInstance(context).enqueueUniqueWork(
            Constants.WORK_BUDGET_CHECK,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    /** Same 9am-anchored self-rescheduling pattern as [scheduleBudgetCheck], see there for why. */
    fun scheduleReminderCheck(context: Context) {
        val request = OneTimeWorkRequestBuilder<ReminderCheckWorker>()
            .setInitialDelay(delayUntilNextDailyCheck(), TimeUnit.MILLISECONDS)
            .build()

        // REPLACE: existing installs have the old "every 12 hours" PeriodicWorkRequest
        // registered under this name and need it torn down, same reasoning as budget check.
        WorkManager.getInstance(context).enqueueUniqueWork(
            Constants.WORK_REMINDER_CHECK,
            ExistingWorkPolicy.REPLACE,
            request
        )
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
