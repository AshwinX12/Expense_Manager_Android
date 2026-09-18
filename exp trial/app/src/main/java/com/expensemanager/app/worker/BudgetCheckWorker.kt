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
        return try {
            val budgets = budgetRepository.getAll()
            for (budget in budgets) {
                val status = budgetRepository.calculateBudgetStatus(budget)
                when {
                    status.isOverBudget -> {
                        NotificationHelper.showBudgetAlert(
                            applicationContext,
                            "Budget Exceeded! ⚠️",
                            "You've spent ${status.spent.formatCurrency()} — that's ${status.percentage}% of your ${budget.amount.formatCurrency()} budget."
                        )
                    }
                    status.isApproachingLimit -> {
                        NotificationHelper.showBudgetAlert(
                            applicationContext,
                            "Budget Alert 📊",
                            "You've used ${status.percentage}% of your budget. ${status.remaining.formatCurrency()} remaining."
                        )
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }
}
