package com.expensemanager.app.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

object Constants {
    const val NOTIFICATION_CHANNEL_BUDGET = "budget_alerts"
    const val NOTIFICATION_CHANNEL_REMINDERS = "reminders"
    const val NOTIFICATION_CHANNEL_SUMMARIES = "weekly_summaries"
    const val NOTIFICATION_CHANNEL_UNUSUAL = "unusual_spending"

    const val WORK_RECURRING = "recurring_transactions"
    const val WORK_BUDGET_CHECK = "budget_check"
    const val WORK_REMINDER_CHECK = "reminder_check"
    const val WORK_WEEKLY_SUMMARY = "weekly_summary"

    const val UNUSUAL_SPENDING_MULTIPLIER = 2.0
    const val TRAILING_MONTHS_FOR_AVERAGE = 3

    val DEFAULT_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val SHORT_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM")
    val MONTH_YEAR_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
}

fun BigDecimal.formatCurrency(currencySymbol: String = AppCurrency.symbol): String {
    // Lakh/crore digit grouping only makes sense for rupees
    val locale = if (AppCurrency.code == "INR") Locale("en", "IN") else Locale.US
    val formatter = NumberFormat.getNumberInstance(locale)
    formatter.minimumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    return "$currencySymbol${formatter.format(this)}"
}

fun BigDecimal.formatCompact(currencySymbol: String = AppCurrency.symbol): String {
    val useIndianUnits = AppCurrency.code == "INR"
    return when {
        useIndianUnits && this >= BigDecimal(10000000) -> "$currencySymbol${this.divide(BigDecimal(10000000)).setScale(1, java.math.RoundingMode.HALF_UP)}Cr"
        useIndianUnits && this >= BigDecimal(100000) -> "$currencySymbol${this.divide(BigDecimal(100000)).setScale(1, java.math.RoundingMode.HALF_UP)}L"
        !useIndianUnits && this >= BigDecimal(1000000000) -> "$currencySymbol${this.divide(BigDecimal(1000000000)).setScale(1, java.math.RoundingMode.HALF_UP)}B"
        !useIndianUnits && this >= BigDecimal(1000000) -> "$currencySymbol${this.divide(BigDecimal(1000000)).setScale(1, java.math.RoundingMode.HALF_UP)}M"
        this >= BigDecimal(1000) -> "$currencySymbol${this.divide(BigDecimal(1000)).setScale(1, java.math.RoundingMode.HALF_UP)}K"
        else -> formatCurrency(currencySymbol)
    }
}

/** Capitalizes just the first letter, leaving the rest as typed (so "iPhone bill" stays "iPhone bill"). */
fun String.capitalizeFirst(): String =
    trim().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

fun LocalDate.formatDisplay(): String = format(Constants.DEFAULT_DATE_FORMATTER)
fun LocalDate.formatShort(): String = format(Constants.SHORT_DATE_FORMATTER)
fun LocalDate.formatMonthYear(): String = format(Constants.MONTH_YEAR_FORMATTER)

fun LocalDate.isToday(): Boolean = this == LocalDate.now()
fun LocalDate.isYesterday(): Boolean = this == LocalDate.now().minusDays(1)

fun LocalDate.displayLabel(): String = when {
    isToday() -> "Today"
    isYesterday() -> "Yesterday"
    else -> formatDisplay()
}
