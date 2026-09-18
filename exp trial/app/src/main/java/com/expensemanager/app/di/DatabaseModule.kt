package com.expensemanager.app.di

import android.content.Context
import com.expensemanager.app.data.db.AppDatabase
import com.expensemanager.app.data.db.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.create(context)
    }

    @Provides fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao()
    @Provides fun provideCategoryDao(db: AppDatabase): CategoryDao = db.categoryDao()
    @Provides fun provideSubcategoryDao(db: AppDatabase): SubcategoryDao = db.subcategoryDao()
    @Provides fun provideAccountDao(db: AppDatabase): AccountDao = db.accountDao()
    @Provides fun provideTagDao(db: AppDatabase): TagDao = db.tagDao()
    @Provides fun provideBudgetDao(db: AppDatabase): BudgetDao = db.budgetDao()
    @Provides fun provideGoalDao(db: AppDatabase): GoalDao = db.goalDao()
    @Provides fun provideRecurringRuleDao(db: AppDatabase): RecurringRuleDao = db.recurringRuleDao()
    @Provides fun provideReminderDao(db: AppDatabase): ReminderDao = db.reminderDao()
    @Provides fun provideLendingDao(db: AppDatabase): LendingDao = db.lendingDao()
    @Provides fun provideSplitExpenseDao(db: AppDatabase): SplitExpenseDao = db.splitExpenseDao()
    @Provides fun provideQuickAddShortcutDao(db: AppDatabase): QuickAddShortcutDao = db.quickAddShortcutDao()
    @Provides fun provideSavedFilterDao(db: AppDatabase): SavedFilterDao = db.savedFilterDao()
    @Provides fun provideCurrencyDao(db: AppDatabase): CurrencyDao = db.currencyDao()
    @Provides fun provideAttachmentDao(db: AppDatabase): AttachmentDao = db.attachmentDao()
    @Provides fun provideSettingsDao(db: AppDatabase): SettingsDao = db.settingsDao()
}
