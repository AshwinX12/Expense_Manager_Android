package com.expensemanager.app.data.db.converter

import androidx.room.TypeConverter
import com.expensemanager.app.data.db.entity.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class Converters {

    private val gson = Gson()

    // LocalDate
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    // LocalDateTime
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? = dateTime?.toString()

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it) }

    // LocalTime
    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? = time?.toString()

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? = value?.let { LocalTime.parse(it) }

    // BigDecimal
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal?): String? = value?.toPlainString()

    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? = value?.let { BigDecimal(it) }

    // TransactionType
    @TypeConverter
    fun fromTransactionType(type: TransactionType?): String? = type?.name

    @TypeConverter
    fun toTransactionType(value: String?): TransactionType? = value?.let { TransactionType.valueOf(it) }

    // AccountType
    @TypeConverter
    fun fromAccountType(type: AccountType?): String? = type?.name

    @TypeConverter
    fun toAccountType(value: String?): AccountType? = value?.let { AccountType.valueOf(it) }

    // PaymentMethod
    @TypeConverter
    fun fromPaymentMethod(method: PaymentMethod?): String? = method?.name

    @TypeConverter
    fun toPaymentMethod(value: String?): PaymentMethod? = value?.let { PaymentMethod.valueOf(it) }

    // BudgetPeriod
    @TypeConverter
    fun fromBudgetPeriod(period: BudgetPeriod?): String? = period?.name

    @TypeConverter
    fun toBudgetPeriod(value: String?): BudgetPeriod? = value?.let { BudgetPeriod.valueOf(it) }

    // RecurringFrequency
    @TypeConverter
    fun fromRecurringFrequency(freq: RecurringFrequency?): String? = freq?.name

    @TypeConverter
    fun toRecurringFrequency(value: String?): RecurringFrequency? = value?.let { RecurringFrequency.valueOf(it) }

    // LendingType
    @TypeConverter
    fun fromLendingType(type: LendingType?): String? = type?.name

    @TypeConverter
    fun toLendingType(value: String?): LendingType? = value?.let { LendingType.valueOf(it) }

    // LendingStatus
    @TypeConverter
    fun fromLendingStatus(status: LendingStatus?): String? = status?.name

    @TypeConverter
    fun toLendingStatus(value: String?): LendingStatus? = value?.let { LendingStatus.valueOf(it) }

    // ReminderType
    @TypeConverter
    fun fromReminderType(type: ReminderType?): String? = type?.name

    @TypeConverter
    fun toReminderType(value: String?): ReminderType? = value?.let { ReminderType.valueOf(it) }

    // List<String> (for tags)
    @TypeConverter
    fun fromStringList(list: List<String>?): String? = list?.let { gson.toJson(it) }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value == null) return null
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}
