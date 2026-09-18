package com.expensemanager.app.data.repository;

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

  private RecurringTransactionRepository_Factory(
      Provider<RecurringRuleDao> recurringRuleDaoProvider,
      Provider<TransactionDao> transactionDaoProvider) {
    this.recurringRuleDaoProvider = recurringRuleDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
  }

  @Override
  public RecurringTransactionRepository get() {
    return newInstance(recurringRuleDaoProvider.get(), transactionDaoProvider.get());
  }

  public static RecurringTransactionRepository_Factory create(
      Provider<RecurringRuleDao> recurringRuleDaoProvider,
      Provider<TransactionDao> transactionDaoProvider) {
    return new RecurringTransactionRepository_Factory(recurringRuleDaoProvider, transactionDaoProvider);
  }

  public static RecurringTransactionRepository newInstance(RecurringRuleDao recurringRuleDao,
      TransactionDao transactionDao) {
    return new RecurringTransactionRepository(recurringRuleDao, transactionDao);
  }
}
