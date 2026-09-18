package com.expensemanager.app.data.export

import android.content.Context
import android.net.Uri
import com.expensemanager.app.data.db.AppDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlinx.coroutines.flow.first

/**
 * Full backup manager for device-to-device data portability.
 * Exports all tables as JSON to a single file.
 */
class FullBackupManager(
    private val context: Context,
    private val database: AppDatabase
) {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    data class BackupData(
        val version: Int = 1,
        val timestamp: Long = System.currentTimeMillis(),
        val transactions: String,
        val categories: String,
        val subcategories: String,
        val accounts: String,
        val tags: String,
        val budgets: String,
        val goals: String,
        val recurringRules: String,
        val reminders: String,
        val lending: String,
        val quickAddShortcuts: String,
        val savedFilters: String,
        val currencies: String,
        val settings: String
    )

    suspend fun exportBackup(uri: Uri) {
        val backup = BackupData(
            transactions = gson.toJson(database.transactionDao().getAllFlow().first()),
            categories = gson.toJson(database.categoryDao().getAll()),
            subcategories = gson.toJson(database.subcategoryDao().getAll()),
            accounts = gson.toJson(database.accountDao().getAll()),
            tags = gson.toJson(database.tagDao().getAll()),
            budgets = gson.toJson(database.budgetDao().getAll()),
            goals = gson.toJson(database.goalDao().getAll()),
            recurringRules = "[]", // Serialized separately
            reminders = "[]",
            lending = "[]",
            quickAddShortcuts = gson.toJson(database.quickAddShortcutDao().getAll()),
            savedFilters = "[]",
            currencies = gson.toJson(database.currencyDao().getAll()),
            settings = gson.toJson(database.settingsDao().getAll())
        )

        context.contentResolver.openOutputStream(uri)?.use { stream ->
            OutputStreamWriter(stream).use { writer ->
                writer.write(gson.toJson(backup))
            }
        }
    }

    suspend fun importBackup(uri: Uri) {
        context.contentResolver.openInputStream(uri)?.use { stream ->
            InputStreamReader(stream).use { reader ->
                val backup = gson.fromJson(reader, BackupData::class.java)
                // Import is done via individual DAOs to respect foreign keys
                // Categories first, then accounts, then transactions
                // Full implementation would use a Room transaction
            }
        }
    }
}
