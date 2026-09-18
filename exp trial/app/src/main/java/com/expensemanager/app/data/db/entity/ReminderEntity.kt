package com.expensemanager.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val dueDate: LocalDate,
    val leadTimeDays: Int = 3,
    val type: ReminderType,
    val recurringRuleId: Long? = null,
    val isActive: Boolean = true,
    val isNotified: Boolean = false
)
