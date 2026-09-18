package com.expensemanager.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val targetAmount: BigDecimal,
    val currentAmount: BigDecimal = BigDecimal.ZERO,
    val targetDate: LocalDate? = null,
    val iconName: String = "savings",
    val colorHex: String = "#4CAF50",
    val isCompleted: Boolean = false
)
