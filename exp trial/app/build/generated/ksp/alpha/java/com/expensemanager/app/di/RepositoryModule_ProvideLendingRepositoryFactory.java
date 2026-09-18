package com.expensemanager.app.di;

import com.expensemanager.app.data.db.dao.AccountDao;
import com.expensemanager.app.data.db.dao.LendingDao;
import com.expensemanager.app.data.db.dao.TransactionDao;
import com.expensemanager.app.data.repository.LendingRepository;
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
public final class RepositoryModule_ProvideLendingRepositoryFactory implements Factory<LendingRepository> {
  private final Provider<LendingDao> lendingDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  private final Provider<AccountDao> accountDaoProvider;

  private RepositoryModule_ProvideLendingRepositoryFactory(Provider<LendingDao> lendingDaoProvider,
      Provider<TransactionDao> transactionDaoProvider, Provider<AccountDao> accountDaoProvider) {
    this.lendingDaoProvider = lendingDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
    this.accountDaoProvider = accountDaoProvider;
  }

  @Override
  public LendingRepository get() {
    return provideLendingRepository(lendingDaoProvider.get(), transactionDaoProvider.get(), accountDaoProvider.get());
  }

  public static RepositoryModule_ProvideLendingRepositoryFactory create(
      Provider<LendingDao> lendingDaoProvider, Provider<TransactionDao> transactionDaoProvider,
      Provider<AccountDao> accountDaoProvider) {
    return new RepositoryModule_ProvideLendingRepositoryFactory(lendingDaoProvider, transactionDaoProvider, accountDaoProvider);
  }

  public static LendingRepository provideLendingRepository(LendingDao lendingDao,
      TransactionDao transactionDao, AccountDao accountDao) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideLendingRepository(lendingDao, transactionDao, accountDao));
  }
}
