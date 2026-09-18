package com.expensemanager.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate

@Entity(tableName = "lending")
data class LendingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val personName: String,
    val amount: BigDecimal,
    val type: LendingType,
    val date: LocalDate,
    val note: String? = null,
    val status: LendingStatus = LendingStatus.PENDING,
    val repaidAmount: BigDecimal = BigDecimal.ZERO
)
