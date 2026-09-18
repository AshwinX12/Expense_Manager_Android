package com.expensemanager.app.di

import android.content.Context
import com.expensemanager.app.data.attachment.AttachmentStorage
import com.expensemanager.app.data.db.dao.*
import com.expensemanager.app.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao,
        accountDao: AccountDao
    ): TransactionRepository = TransactionRepository(transactionDao, accountDao)

    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryDao: CategoryDao,
        subcategoryDao: SubcategoryDao
    ): CategoryRepository = CategoryRepository(categoryDao, subcategoryDao)

    @Provides
    @Singleton
    fun provideAccountRepository(accountDao: AccountDao): AccountRepository =
        AccountRepository(accountDao)

    @Provides
    @Singleton
    fun provideBudgetRepository(
        budgetDao: BudgetDao,
        transactionDao: TransactionDao
    ): BudgetRepository = BudgetRepository(budgetDao, transactionDao)

    @Provides
    @Singleton
    fun provideGoalRepository(goalDao: GoalDao): GoalRepository = GoalRepository(goalDao)

    @Provides
    @Singleton
    fun provideRecurringTransactionRepository(
        recurringRuleDao: RecurringRuleDao,
        transactionDao: TransactionDao,
        accountDao: AccountDao
    ): RecurringTransactionRepository =
        RecurringTransactionRepository(recurringRuleDao, transactionDao, accountDao)

    @Provides
    @Singleton
    fun provideReminderRepository(reminderDao: ReminderDao): ReminderRepository =
        ReminderRepository(reminderDao)

    @Provides
    @Singleton
    fun provideLendingRepository(
        lendingDao: LendingDao,
        transactionDao: TransactionDao,
        accountDao: AccountDao
    ): LendingRepository =
        LendingRepository(lendingDao, transactionDao, accountDao)

    @Provides
    @Singleton
    fun provideAttachmentRepository(
        attachmentDao: AttachmentDao,
        attachmentStorage: AttachmentStorage
    ): AttachmentRepository = AttachmentRepository(attachmentDao, attachmentStorage)

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsDao: SettingsDao): SettingsRepository =
        SettingsRepository(settingsDao)

    @Provides
    @Singleton
    fun provideAttachmentStorage(@ApplicationContext context: Context): AttachmentStorage =
        AttachmentStorage(context)
}
