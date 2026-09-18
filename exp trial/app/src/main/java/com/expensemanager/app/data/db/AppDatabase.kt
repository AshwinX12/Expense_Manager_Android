package com.expensemanager.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.expensemanager.app.data.db.converter.Converters
import com.expensemanager.app.data.db.dao.*
import com.expensemanager.app.data.db.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal

@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        SubcategoryEntity::class,
        AccountEntity::class,
        TagEntity::class,
        TransactionTagCrossRef::class,
        BudgetEntity::class,
        GoalEntity::class,
        RecurringRuleEntity::class,
        ReminderEntity::class,
        LendingEntity::class,
        SplitExpenseEntity::class,
        QuickAddShortcutEntity::class,
        SavedFilterEntity::class,
        CurrencyEntity::class,
        AttachmentEntity::class,
        SettingsEntity::class
    ],
    version = 5,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun subcategoryDao(): SubcategoryDao
    abstract fun accountDao(): AccountDao
    abstract fun tagDao(): TagDao
    abstract fun budgetDao(): BudgetDao
    abstract fun goalDao(): GoalDao
    abstract fun recurringRuleDao(): RecurringRuleDao
    abstract fun reminderDao(): ReminderDao
    abstract fun lendingDao(): LendingDao
    abstract fun splitExpenseDao(): SplitExpenseDao
    abstract fun quickAddShortcutDao(): QuickAddShortcutDao
    abstract fun savedFilterDao(): SavedFilterDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun attachmentDao(): AttachmentDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        const val DATABASE_NAME = "expense_manager.db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE transactions ADD COLUMN time TEXT DEFAULT NULL")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE recurring_rules ADD COLUMN time TEXT DEFAULT NULL")
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Fix existing transactions with null accountId: assign to default account
                db.execSQL("""
                    UPDATE transactions SET accountId = (
                        SELECT id FROM accounts WHERE isDefault = 1 LIMIT 1
                    ) WHERE accountId IS NULL
                """)
                // Fix existing recurring rules with null accountId
                db.execSQL("""
                    UPDATE recurring_rules SET accountId = (
                        SELECT id FROM accounts WHERE isDefault = 1 LIMIT 1
                    ) WHERE accountId IS NULL
                """)
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE accounts ADD COLUMN customTypeName TEXT DEFAULT NULL")
            }
        }

        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .addCallback(SeedCallback())
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .build()
        }
    }

    private class SeedCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Seed default categories
            val defaultCategories = listOf(
                Triple("Food & Dining", "restaurant", "#FF6B6B"),
                Triple("Transport", "directions_car", "#4ECDC4"),
                Triple("Shopping", "shopping_bag", "#45B7D1"),
                Triple("Bills & Utilities", "receipt_long", "#96CEB4"),
                Triple("Entertainment", "movie", "#FFEAA7"),
                Triple("Health", "local_hospital", "#DDA0DD"),
                Triple("Education", "school", "#98D8C8"),
                Triple("Groceries", "local_grocery_store", "#F7DC6F"),
                Triple("Rent", "home", "#BB8FCE"),
                Triple("Salary", "payments", "#82E0AA"),
                Triple("Freelance", "work", "#85C1E9"),
                Triple("Other", "more_horiz", "#AEB6BF")
            )
            defaultCategories.forEachIndexed { index, (name, icon, color) ->
                db.execSQL(
                    "INSERT INTO categories (name, iconName, colorHex, isDefault, sortOrder) VALUES (?, ?, ?, 1, ?)",
                    arrayOf(name, icon, color, index)
                )
            }

            // Seed default currency
            db.execSQL(
                "INSERT INTO currencies (code, name, symbol, conversionRateToBase) VALUES ('INR', 'Indian Rupee', '₹', '1')"
            )
            db.execSQL(
                "INSERT INTO currencies (code, name, symbol, conversionRateToBase) VALUES ('USD', 'US Dollar', '\$', '83.5')"
            )
            db.execSQL(
                "INSERT INTO currencies (code, name, symbol, conversionRateToBase) VALUES ('EUR', 'Euro', '€', '91.0')"
            )

            // Seed default account
            db.execSQL(
                "INSERT INTO accounts (name, type, currency, initialBalance, iconName, colorHex, isDefault, sortOrder) VALUES ('Cash', 'CASH', 'INR', '0', 'account_balance_wallet', '#4CAF50', 1, 0)"
            )

            // Seed default settings
            db.execSQL("INSERT INTO settings (`key`, value) VALUES ('theme', 'SYSTEM')")
            db.execSQL("INSERT INTO settings (`key`, value) VALUES ('base_currency', 'INR')")
            db.execSQL("INSERT INTO settings (`key`, value) VALUES ('app_lock_enabled', 'false')")
            db.execSQL("INSERT INTO settings (`key`, value) VALUES ('biometric_enabled', 'false')")
        }
    }
}
