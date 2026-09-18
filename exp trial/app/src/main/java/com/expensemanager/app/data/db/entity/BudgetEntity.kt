package com.expensemanager.app.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate

@Entity(
    tableName = "budgets",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoryId")]
)
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryId: Long? = null, // null = overall budget
    val amount: BigDecimal,
    val period: BudgetPeriod,
    val rolloverEnabled: Boolean = false,
    val rolloverAmount: BigDecimal = BigDecimal.ZERO,
    val startDate: LocalDate,
    val alertThreshold: Int = 80 // percentage
)
