package com.expensemanager.app.data.repository

import com.expensemanager.app.data.db.dao.AccountDao
import com.expensemanager.app.data.db.dao.RecurringRuleDao
import com.expensemanager.app.data.db.dao.TransactionDao
import com.expensemanager.app.data.db.entity.RecurringFrequency
import com.expensemanager.app.data.db.entity.RecurringRuleEntity
import com.expensemanager.app.data.db.entity.TransactionEntity
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class RecurringTransactionRepository @Inject constructor(
    private val recurringRuleDao: RecurringRuleDao,
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao
) {
    suspend fun insertRule(rule: RecurringRuleEntity) = recurringRuleDao.insert(rule)
    suspend fun updateRule(rule: RecurringRuleEntity) {
        recurringRuleDao.update(rule)
        rule.accountId?.let { accId ->
            val updated = transactionDao.updateAccountIdForRecurringRule(rule.id, accId)
            android.util.Log.d("RecurringRepo", "Updated $updated transactions for rule ${rule.id} to account $accId")
        }
    }
    suspend fun deleteRule(rule: RecurringRuleEntity) = recurringRuleDao.delete(rule)
    suspend fun getRuleById(id: Long) = recurringRuleDao.getById(id)
    fun getAllRulesFlow() = recurringRuleDao.getAllFlow()
    fun getActiveRulesFlow() = recurringRuleDao.getActiveFlow()

    /**
     * Fix any transactions or rules that have null accountId by assigning the default account.
     */
    suspend fun fixOrphanedTransactions() {
        val defaultAccountId = accountDao.getDefault()?.id ?: accountDao.getAll().firstOrNull()?.id ?: return
        val fixedCount = transactionDao.fixNullAccountTransactions(defaultAccountId)
        if (fixedCount > 0) {
            android.util.Log.d("RecurringRepo", "Fixed $fixedCount transactions with null accountId -> $defaultAccountId")
        }
    }

    suspend fun generateDueTransactions() {
        fixOrphanedTransactions()
        val today = LocalDate.now()
        val dueRules = recurringRuleDao.getDueRules(today)

        // Resolve default account once for rules that don't have one
        val defaultAccountId = accountDao.getDefault()?.id ?: accountDao.getAll().firstOrNull()?.id

        android.util.Log.d("RecurringRepo", "generateDueTransactions called. today=$today, dueRules count=${dueRules.size}, defaultAccountId=$defaultAccountId")

        for (rule in dueRules) {
            android.util.Log.d("RecurringRepo", "Processing rule id=${rule.id}, nextOccurrence=${rule.nextOccurrence}, time=${rule.time}, categoryId=${rule.categoryId}, accountId=${rule.accountId}")

            // Check if end date has passed
            if (rule.endDate != null && today.isAfter(rule.endDate)) {
                android.util.Log.d("RecurringRepo", "Rule ${rule.id} skipped: endDate ${rule.endDate} passed")
                recurringRuleDao.setActive(rule.id, false)
                continue
            }

            // Check if time has arrived (if scheduled for today with a specific time)
            if (rule.nextOccurrence == today && rule.time != null && rule.time.isAfter(LocalTime.now())) {
                android.util.Log.d("RecurringRepo", "Rule ${rule.id} skipped: time ${rule.time} hasn't arrived yet (now=${LocalTime.now()})")
                continue
            }

            // Resolve accountId: use rule's account, or fall back to default
            val resolvedAccountId = rule.accountId ?: defaultAccountId

            // Generate transaction
            val transaction = TransactionEntity(
                amount = rule.amount,
                type = rule.type,
                date = rule.nextOccurrence,
                time = rule.time,
                categoryId = rule.categoryId,
                accountId = resolvedAccountId,
                paymentMethod = rule.paymentMethod,
                note = rule.note,
                merchantName = rule.merchantName,
                isRecurring = true,
                recurringRuleId = rule.id
            )
            val insertedId = transactionDao.insert(transaction)
            android.util.Log.d("RecurringRepo", "Rule ${rule.id} -> Generated transaction id=$insertedId, accountId=$resolvedAccountId")

            // Calculate next occurrence
            val nextDate = calculateNextOccurrence(rule.nextOccurrence, rule.frequency, rule.interval)
            recurringRuleDao.updateNextOccurrence(rule.id, nextDate)
            android.util.Log.d("RecurringRepo", "Rule ${rule.id} -> nextOccurrence updated to $nextDate")
        }
    }

    private fun calculateNextOccurrence(
        current: LocalDate,
        frequency: RecurringFrequency,
        interval: Int
    ): LocalDate = when (frequency) {
        RecurringFrequency.DAILY -> current.plusDays(interval.toLong())
        RecurringFrequency.WEEKLY -> current.plusWeeks(interval.toLong())
        RecurringFrequency.MONTHLY -> current.plusMonths(interval.toLong())
        RecurringFrequency.YEARLY -> current.plusYears(interval.toLong())
        RecurringFrequency.CUSTOM -> current.plusDays(interval.toLong())
    }
}
