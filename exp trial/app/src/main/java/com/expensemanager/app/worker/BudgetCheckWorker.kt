package com.expensemanager.app.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.expensemanager.app.data.repository.BudgetRepository
import com.expensemanager.app.notification.NotificationHelper
import com.expensemanager.app.util.formatCurrency
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BudgetCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val budgetRepository: BudgetRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        try {
            val budgets = budgetRepository.getAll()
            for (budget in budgets) {
                val status = budgetRepository.calculateBudgetStatus(budget)
                // A stable ID per budget means today's alert REPLACES yesterday's in the
                // tray instead of stacking a new one next to it — the previous behavior
                // (a fresh random ID every time) is what made the notification look like it
                // "wasn't updating": an old, stale one was still sitting there untouched
                // while a new, separate one piled up next to it.
                val notificationId = BUDGET_NOTIFICATION_ID_BASE + budget.id.toInt()
                when {
                    status.isOverBudget -> {
                        NotificationHelper.showBudgetAlert(
                            applicationContext,
                            "Budget Exceeded! ⚠️",
                            "You've spent ${status.spent.formatCurrency()} — that's ${status.percentage}% of your ${budget.amount.formatCurrency()} budget.",
                            notificationId
                        )
                    }
                    status.isApproachingLimit -> {
                        NotificationHelper.showBudgetAlert(
                            applicationContext,
                            "Budget Alert 📊",
                            "You've used ${status.percentage}% of your budget. ${status.remaining.formatCurrency()} remaining.",
                            notificationId
                        )
                    }
                }
            }
            // Chain tomorrow's 9am check — see WorkScheduler.scheduleBudgetCheck for why this
            // is a self-rescheduling one-shot rather than a fixed periodic interval. Only done
            // on success/final-failure, not before a retry: both this call and WorkManager's
            // own retry re-enqueue under the same unique work name, so scheduling "tomorrow"
            // ahead of a retry would cancel that retry instead of just backing it off.
            WorkScheduler.scheduleBudgetCheck(applicationContext)
            return Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) return Result.retry()
            WorkScheduler.scheduleBudgetCheck(applicationContext)
            return Result.failure()
        }
    }

    companion object {
        private const val BUDGET_NOTIFICATION_ID_BASE = 10_000
    }
}
