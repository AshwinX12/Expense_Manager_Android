package com.expensemanager.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey
    val code: String, // e.g. "USD", "EUR", "INR"
    val name: String,
    val symbol: String,
    val conversionRateToBase: BigDecimal = BigDecimal.ONE
)
