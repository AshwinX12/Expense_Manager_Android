package com.expensemanager.app.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = SubcategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["subcategoryId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("categoryId"),
        Index("subcategoryId"),
        Index("accountId"),
        Index("date"),
        Index("type")
    ]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: BigDecimal,
    val currency: String = "INR",
    val conversionRate: BigDecimal = BigDecimal.ONE,
    val type: TransactionType,
    val date: LocalDate,
    val time: LocalTime? = null,
    val categoryId: Long? = null,
    val subcategoryId: Long? = null,
    val accountId: Long? = null,
    val paymentMethod: PaymentMethod? = null,
    val note: String? = null,
    val merchantName: String? = null,
    val isRecurring: Boolean = false,
    val recurringRuleId: Long? = null,
    val tags: List<String>? = null,
    val hasAttachments: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
