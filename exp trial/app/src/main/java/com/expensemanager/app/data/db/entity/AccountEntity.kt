package com.expensemanager.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: AccountType,
    val currency: String = "INR",
    val initialBalance: BigDecimal = BigDecimal.ZERO,
    val iconName: String = "account_balance",
    val colorHex: String = "#4CAF50",
    val isDefault: Boolean = false,
    val sortOrder: Int = 0
)
