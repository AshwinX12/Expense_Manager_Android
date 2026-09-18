package com.expensemanager.app.di;

import com.expensemanager.app.data.db.dao.RecurringRuleDao;
import com.expensemanager.app.data.db.dao.TransactionDao;
import com.expensemanager.app.data.repository.RecurringTransactionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RepositoryModule_ProvideRecurringTransactionRepositoryFactory implements Factory<RecurringTransactionRepository> {
  private final Provider<RecurringRuleDao> recurringRuleDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  private RepositoryModule_ProvideRecurringTransactionRepositoryFactory(
      Provider<RecurringRuleDao> recurringRuleDaoProvider,
      Provider<TransactionDao> transactionDaoProvider) {
    this.recurringRuleDaoProvider = recurringRuleDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
  }

  @Override
  public RecurringTransactionRepository get() {
    return provideRecurringTransactionRepository(recurringRuleDaoProvider.get(), transactionDaoProvider.get());
  }

  public static RepositoryModule_ProvideRecurringTransactionRepositoryFactory create(
      Provider<RecurringRuleDao> recurringRuleDaoProvider,
      Provider<TransactionDao> transactionDaoProvider) {
    return new RepositoryModule_ProvideRecurringTransactionRepositoryFactory(recurringRuleDaoProvider, transactionDaoProvider);
  }

  public static RecurringTransactionRepository provideRecurringTransactionRepository(
      RecurringRuleDao recurringRuleDao, TransactionDao transactionDao) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideRecurringTransactionRepository(recurringRuleDao, transactionDao));
  }
}
