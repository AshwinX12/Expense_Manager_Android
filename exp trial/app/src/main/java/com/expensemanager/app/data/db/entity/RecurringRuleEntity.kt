package com.expensemanager.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "recurring_rules")
data class RecurringRuleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val templateTransactionId: Long? = null,
    val frequency: RecurringFrequency,
    val interval: Int = 1, // e.g. every 2 weeks
    val nextOccurrence: LocalDate,
    val time: LocalTime? = null,
    val endDate: LocalDate? = null,
    val isActive: Boolean = true,
    // Template fields (used to generate new transactions)
    val amount: java.math.BigDecimal,
    val type: TransactionType,
    val categoryId: Long? = null,
    val accountId: Long? = null,
    val paymentMethod: PaymentMethod? = null,
    val note: String? = null,
    val merchantName: String? = null
)
