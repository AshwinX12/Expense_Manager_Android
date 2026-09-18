package com.expensemanager.app.di;

import com.expensemanager.app.data.db.dao.AccountDao;
import com.expensemanager.app.data.db.dao.TransactionDao;
import com.expensemanager.app.data.repository.TransactionRepository;
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
public final class RepositoryModule_ProvideTransactionRepositoryFactory implements Factory<TransactionRepository> {
  private final Provider<TransactionDao> transactionDaoProvider;

  private final Provider<AccountDao> accountDaoProvider;

  private RepositoryModule_ProvideTransactionRepositoryFactory(
      Provider<TransactionDao> transactionDaoProvider, Provider<AccountDao> accountDaoProvider) {
    this.transactionDaoProvider = transactionDaoProvider;
    this.accountDaoProvider = accountDaoProvider;
  }

  @Override
  public TransactionRepository get() {
    return provideTransactionRepository(transactionDaoProvider.get(), accountDaoProvider.get());
  }

  public static RepositoryModule_ProvideTransactionRepositoryFactory create(
      Provider<TransactionDao> transactionDaoProvider, Provider<AccountDao> accountDaoProvider) {
    return new RepositoryModule_ProvideTransactionRepositoryFactory(transactionDaoProvider, accountDaoProvider);
  }

  public static TransactionRepository provideTransactionRepository(TransactionDao transactionDao,
      AccountDao accountDao) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideTransactionRepository(transactionDao, accountDao));
  }
}
