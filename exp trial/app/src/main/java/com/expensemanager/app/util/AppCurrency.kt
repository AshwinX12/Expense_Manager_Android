package com.expensemanager.app.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class CurrencyOption(val code: String, val symbol: String, val label: String)

val SUPPORTED_CURRENCIES = listOf(
    CurrencyOption("INR", "₹", "Indian Rupee"),
    CurrencyOption("USD", "$", "US Dollar"),
    CurrencyOption("EUR", "€", "Euro"),
    CurrencyOption("GBP", "£", "British Pound"),
    CurrencyOption("JPY", "¥", "Japanese Yen"),
    CurrencyOption("AED", "د.إ", "UAE Dirham"),
    CurrencyOption("AUD", "A$", "Australian Dollar"),
    CurrencyOption("CAD", "C$", "Canadian Dollar"),
    CurrencyOption("SGD", "S$", "Singapore Dollar"),
    CurrencyOption("CHF", "Fr", "Swiss Franc"),
    CurrencyOption("CNY", "¥", "Chinese Yuan"),
    CurrencyOption("ZAR", "R", "South African Rand"),
)

/**
 * The currency the app formats amounts in. Held as Compose state so that changing it in
 * settings re-renders every amount on screen without threading a parameter through the
 * whole UI — `formatCurrency()` reads it as its default argument.
 */
object AppCurrency {
    var code by mutableStateOf("INR")
        private set
    var symbol by mutableStateOf("₹")
        private set

    fun set(currencyCode: String) {
        val option = SUPPORTED_CURRENCIES.firstOrNull { it.code == currencyCode }
            ?: SUPPORTED_CURRENCIES.first()
        code = option.code
        symbol = option.symbol
    }
}
