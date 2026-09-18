package com.expensemanager.app.di;

import com.expensemanager.app.data.db.dao.AccountDao;
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

  private final Provider<AccountDao> accountDaoProvider;

  private RepositoryModule_ProvideRecurringTransactionRepositoryFactory(
      Provider<RecurringRuleDao> recurringRuleDaoProvider,
      Provider<TransactionDao> transactionDaoProvider, Provider<AccountDao> accountDaoProvider) {
    this.recurringRuleDaoProvider = recurringRuleDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
    this.accountDaoProvider = accountDaoProvider;
  }

  @Override
  public RecurringTransactionRepository get() {
    return provideRecurringTransactionRepository(recurringRuleDaoProvider.get(), transactionDaoProvider.get(), accountDaoProvider.get());
  }

  public static RepositoryModule_ProvideRecurringTransactionRepositoryFactory create(
      Provider<RecurringRuleDao> recurringRuleDaoProvider,
      Provider<TransactionDao> transactionDaoProvider, Provider<AccountDao> accountDaoProvider) {
    return new RepositoryModule_ProvideRecurringTransactionRepositoryFactory(recurringRuleDaoProvider, transactionDaoProvider, accountDaoProvider);
  }

  public static RecurringTransactionRepository provideRecurringTransactionRepository(
      RecurringRuleDao recurringRuleDao, TransactionDao transactionDao, AccountDao accountDao) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideRecurringTransactionRepository(recurringRuleDao, transactionDao, accountDao));
  }
}
