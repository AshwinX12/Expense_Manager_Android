package com.expensemanager.app.data.repository;

import com.expensemanager.app.data.db.dao.AccountDao;
import com.expensemanager.app.data.db.dao.RecurringRuleDao;
import com.expensemanager.app.data.db.dao.TransactionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class RecurringTransactionRepository_Factory implements Factory<RecurringTransactionRepository> {
  private final Provider<RecurringRuleDao> recurringRuleDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  private final Provider<AccountDao> accountDaoProvider;

  private RecurringTransactionRepository_Factory(
      Provider<RecurringRuleDao> recurringRuleDaoProvider,
      Provider<TransactionDao> transactionDaoProvider, Provider<AccountDao> accountDaoProvider) {
    this.recurringRuleDaoProvider = recurringRuleDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
    this.accountDaoProvider = accountDaoProvider;
  }

  @Override
  public RecurringTransactionRepository get() {
    return newInstance(recurringRuleDaoProvider.get(), transactionDaoProvider.get(), accountDaoProvider.get());
  }

  public static RecurringTransactionRepository_Factory create(
      Provider<RecurringRuleDao> recurringRuleDaoProvider,
      Provider<TransactionDao> transactionDaoProvider, Provider<AccountDao> accountDaoProvider) {
    return new RecurringTransactionRepository_Factory(recurringRuleDaoProvider, transactionDaoProvider, accountDaoProvider);
  }

  public static RecurringTransactionRepository newInstance(RecurringRuleDao recurringRuleDao,
      TransactionDao transactionDao, AccountDao accountDao) {
    return new RecurringTransactionRepository(recurringRuleDao, transactionDao, accountDao);
  }
}
