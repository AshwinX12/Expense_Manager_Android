package com.expensemanager.app.data.repository

import com.expensemanager.app.data.db.dao.SettingsDao
import com.expensemanager.app.data.db.entity.SettingsEntity
import com.expensemanager.app.data.db.entity.ThemeMode
import com.expensemanager.app.data.db.entity.ThemeStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val settingsDao: SettingsDao
) {
    suspend fun setValue(key: String, value: String) {
        settingsDao.insert(SettingsEntity(key, value))
    }

    suspend fun getValue(key: String): String? = settingsDao.getValue(key)

    fun getValueFlow(key: String): Flow<String?> = settingsDao.getValueFlow(key)

    suspend fun getAll() = settingsDao.getAll()

    // Typed accessors
    fun getThemeModeFlow(): Flow<ThemeMode> = settingsDao.getValueFlow("theme").map { value ->
        try { ThemeMode.valueOf(value ?: "SYSTEM") } catch (e: Exception) { ThemeMode.SYSTEM }
    }

    suspend fun setThemeMode(mode: ThemeMode) = setValue("theme", mode.name)

    fun getThemeStyleFlow(): Flow<ThemeStyle> = settingsDao.getValueFlow("theme_style").map { value ->
        try { ThemeStyle.valueOf(value ?: "CLASSIC") } catch (e: Exception) { ThemeStyle.CLASSIC }
    }

    suspend fun setThemeStyle(style: ThemeStyle) = setValue("theme_style", style.name)

    suspend fun getBaseCurrency(): String = getValue("base_currency") ?: "INR"
    suspend fun setBaseCurrency(code: String) = setValue("base_currency", code)
    fun getBaseCurrencyFlow(): Flow<String> = getValueFlow("base_currency").map { it ?: "INR" }

    fun isAppLockEnabledFlow(): Flow<Boolean> = getValueFlow("app_lock_enabled").map { it == "true" }
    suspend fun isAppLockEnabled(): Boolean = getValue("app_lock_enabled") == "true"
    suspend fun setAppLockEnabled(enabled: Boolean) = setValue("app_lock_enabled", enabled.toString())

    fun isBiometricEnabledFlow(): Flow<Boolean> = getValueFlow("biometric_enabled").map { it == "true" }
    suspend fun isBiometricEnabled(): Boolean = getValue("biometric_enabled") == "true"
    suspend fun setBiometricEnabled(enabled: Boolean) = setValue("biometric_enabled", enabled.toString())

    fun getPinFlow(): Flow<String?> = getValueFlow("app_pin")
    suspend fun getPin(): String? = getValue("app_pin")
    suspend fun setPin(pin: String) = setValue("app_pin", pin)

    fun isSwipeNavigationEnabledFlow(): Flow<Boolean> = getValueFlow("swipe_navigation").map { it != "false" } // default true
    suspend fun setSwipeNavigationEnabled(enabled: Boolean) = setValue("swipe_navigation", enabled.toString())

    // Which account's balance the Home screen shows — null means "all accounts combined"
    fun getHomeAccountIdFlow(): Flow<Long?> = getValueFlow("home_account_id").map { it?.toLongOrNull() }
    suspend fun setHomeAccountId(id: Long?) = setValue("home_account_id", id?.toString() ?: "")

    // Per-day spendable amount is deliberately locked in once per calendar day (see HomeViewModel) —
    // otherwise it recalculates on every transaction and reads as a moving target.
    suspend fun getPerDaySpendCache(): Pair<LocalDate, BigDecimal>? {
        val raw = getValue("per_day_spend_cache") ?: return null
        val parts = raw.split("|")
        if (parts.size != 2) return null
        return try {
            LocalDate.parse(parts[0]) to BigDecimal(parts[1])
        } catch (e: Exception) {
            null
        }
    }

    suspend fun setPerDaySpendCache(date: LocalDate, amount: BigDecimal) =
        setValue("per_day_spend_cache", "$date|$amount")
}
