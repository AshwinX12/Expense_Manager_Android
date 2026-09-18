package com.expensemanager.app.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.expensemanager.app.data.repository.TransactionRepository
import com.expensemanager.app.notification.NotificationHelper
import com.expensemanager.app.util.formatCurrency
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@HiltWorker
class WeeklySummaryWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val transactionRepository: TransactionRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val today = LocalDate.now()
            val weekStart = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
            val weekEnd = today

            val totalSpent = transactionRepository.getTotalExpense(weekStart, weekEnd)
            val count = transactionRepository.getByDateRange(weekStart, weekEnd).size

            NotificationHelper.showWeeklySummary(
                applicationContext,
                "Weekly Summary 📋",
                "You spent ${totalSpent.formatCurrency()} across $count transactions this week."
            )
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }
}
