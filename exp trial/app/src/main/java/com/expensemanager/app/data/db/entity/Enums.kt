package com.expensemanager.app.data.db.entity

/**
 * All enum types used across the data layer.
 */

enum class TransactionType {
    EXPENSE,
    INCOME,
    TRANSFER_TO_GOAL,
    DEBT_TRANSFER_IN,
    DEBT_TRANSFER_OUT
}

enum class AccountType {
    BANK,
    CASH,
    CREDIT_CARD,
    DIGITAL_WALLET,
    UPI,
    OTHER
}

enum class PaymentMethod {
    CASH,
    DEBIT_CARD,
    CREDIT_CARD,
    UPI,
    NET_BANKING,
    DIGITAL_WALLET,
    CHEQUE,
    OTHER
}

enum class BudgetPeriod {
    WEEKLY,
    MONTHLY
}

enum class RecurringFrequency {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY,
    CUSTOM
}

enum class LendingType {
    LENT,
    BORROWED
}

enum class LendingStatus {
    PENDING,
    PARTIALLY_REPAID,
    SETTLED
}

enum class ReminderType {
    BILL,
    CREDIT_CARD,
    CUSTOM
}

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}

enum class ThemeStyle {
    CLASSIC,
    WARM_PAPER,
    EDITORIAL,
    MEMPHIS,
    TERMINAL,
    AURORA
}
